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
		int navigation_cost;
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
		public Object getPath() {
			// TODO Auto-generated method stub
			return null;
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
				//, this.level.getTile(position.x, position.y)));
		
		while (!closedNodes.contains(destination) && openNodes.size() > 0  ) {
			closedNodes.add(openNodes.remove(0));
			
			// if neighbor is closed or not traversibile, ignore
			// if not open, add to open list, with this tile as parent
			// if open, if this.traverse cost + move of current is lower than 
			//    traverse cost of neighbor, set neighbor.parent = this, update neighbor
			//    
			// path = array of destination.parent chains
		}
		
		return new Node(position, null);
	}
}

