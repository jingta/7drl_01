/**
 * 
 */
package com.jingta.game.sdrl01.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.jingta.game.sdrl01.model.Hero;
import com.jingta.game.sdrl01.model.Tile;
import com.jingta.game.sdrl01.model.World;


/**
 * @author jingta
 *
 */
public class WorldController {

	private World world;
	private Hero hero;
	
	public enum Actions { MOVETO, FIGHT}
	static Map<Actions, Boolean> keys = new HashMap<Actions, Boolean>();
	
	public WorldController(World world) {
		this.world = world;
		this.hero = this.world.getHero();
	}
	
	public void update(float delta){
		if (this.hero.getDestination() != null) {
			// HERO A* PATHFIND
			this.hero.setPosition(pathfindMove(this.hero));
			if (this.hero.getDestination().equals(this.hero.getPosition())) {
				this.hero.setDestination(null);
			} 
		}
		this.hero.update(delta);
	}
	
	private Vector2 pathfindMove(Hero unit) {
		Vector2 d = unit.getDestination();
		Vector2 p = unit.getPosition();
		//*
		if (d.x > p.x) {
			p.x += 1;
		} else if (d.x < p.x) {
			p.x -= 1;
		}
		if (d.y > p.y) {
			p.y += 1;
		} else if (d.y < p.y) {
			p.y -= 1;
		}//*/
		/*
		int up = 0, down = 0, left = 0, right = 0;
		class Node {
			public Node(int x, int y, int p) {
				this.x = x;
				this.y = y;
				this.pathCost = p;
			}
			int pathCost;
			int x;
			int y;
			int heuristic(Vector2 destination) {
				int dist = 0;
				dist += Math.abs(destination.x - this.x);
				dist += Math.abs(destination.y - this.y);
				return dist;
			}
		}
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closedList = new ArrayList<Node>();
		
		openList.add(new Node((int) p.x, (int) p.y, 0));
		while(!openList.isEmpty() && !closedList.contains(d)) {
			// find most likely in the open list
			// remove from open and add it to closed
			// add each neighbor of closed item to open list with path if not present
			// if neighbor has lower cost, remove current node?
		}
		
		
		if (up > down && up > left && up > right) {
			p.y -= 1;
		} else if (down > up && down > left && down > right) {
			p.y += 1;
		} else if (left > down && left > up && up > right) {
			p.x -= 1;
		} else if (right > down && right > up && right > left) {
			p.x += 1;
		}
		*/
		return p;
	}

	// input events
	public void moveHeroTo(Vector2 destination){
		Tile t = world.getLevel().getTile((int)destination.x, (int)destination.y);
		if(t == null || !t.getType().equals(Tile.Type.COLLIDABLE)) {
			hero.setDestination(destination);
		}
	}
}
