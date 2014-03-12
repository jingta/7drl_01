/**
 * 
 */
package com.jingta.game.sdrl01.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.jingta.game.sdrl01.model.Level;
import com.jingta.game.sdrl01.model.Tile;

/**
 * @author jingta
 *
 */
public class Pathfinder {

	public static class Node {
		int navigation_cost = 0;
		int estimated_distance;
		Vector2 position;
		Node parent;
		public Node(Vector2 position, Node parent) {
			this.position = position;
			this.parent = parent;
		}
		public Vector2 getPosition() {
			return this.position;
		}
		public Node getParent() {
			return this.parent;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj.getClass().equals(Vector2.class)) {
				return obj.equals(this.getPosition());
			} else if(obj.getClass().equals(Tile.class)) {
				return ((Tile) obj).getPosition().equals(this.getPosition());
			} else if (obj.getClass().equals(Node.class)) {
				return this.getPosition().equals(((Node)obj).getPosition());
			} else {
				return super.equals(obj);
			}
		}
	}
	
	Level level;
	List<Node> openNodes;
	List<Node> closedNodes;

	Pathfinder(Level level) {
		this.level = level;
	}
	
	public Node getPath(Vector2 position, Vector2 destination) {
		openNodes = new ArrayList<Node>();
		closedNodes = new ArrayList<Node>();
		
		openNodes.add(new Node(position, null));
		
		Node destinationNode = new Node(destination, null);
		
		Node current = null;
		while (!closedNodes.contains(destinationNode) && openNodes.size() > 0  ) {
			current = openNodes.remove(0);
			closedNodes.add(current);
			//TODO: BLOCK NODES IF VALUES ARE > MAX OR < 0...will be solved if you remove blanks
			Node up = new Node(
					new Vector2(current.position.x, current.position.y + 1), current);
			Node down = new Node(
					new Vector2(current.position.x, current.position.y - 1), current);
			Node left = new Node(
					new Vector2(current.position.x - 1, current.position.y), current);
			Node right = new Node(
					new Vector2(current.position.x + 1, current.position.y), current);
			Node[] neighbors = {up, down, left, right};
			for (Node n : neighbors) {
				Tile t = level.getTile((int)n.position.x, (int)n.position.y);
				if (t == null || !t.getType().equals(Tile.Type.COLLIDABLE)) { // TODO: empty space is traversible for now
					// tile is traversible
					int dist = 0;
					dist += Math.abs(destination.x - n.position.x);
					dist += Math.abs(destination.y - n.position.y);
					n.estimated_distance = dist;
					n.navigation_cost = current.navigation_cost + 1;
					
					if (closedNodes.contains(n)) { continue; } // ignore if closed
					int index = openNodes.indexOf(n);
					if (index > -1) {
						// node exists, check for better path
						if (openNodes.get(index).navigation_cost > n.navigation_cost ) {
							// better path!
							openNodes.set(index, n);
						}
					} else {
						openNodes.add(n); // doesnt exist, add it
					}
				}
			}

		}
		int index = closedNodes.indexOf(destinationNode);
		if (index > -1) {
			// return last node as path
			return closedNodes.get(index);
		} else {
			return null; // no path
		}
	}
}

