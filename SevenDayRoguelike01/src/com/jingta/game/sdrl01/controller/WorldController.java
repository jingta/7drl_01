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
import com.jingta.game.sdrl01.utils.Pathfinder;
import com.jingta.game.sdrl01.utils.Pathfinder.Node;


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
	
	float lastPathfound = 0f;
	public void update(float delta){
		lastPathfound += delta;
		if (this.hero.getDestination() != null && lastPathfound > 0.08f) {
			lastPathfound = 0;
			// HERO A* PATHFIND
			this.hero.setPosition(pathfindMove(this.hero));
			if (this.hero.getDestination().equals(this.hero.getPosition())) {
				this.hero.setDestination(null);
			} 
		}
		this.hero.update(delta);
	}
	
	// Pathfind! With A*
	private Vector2 pathfindMove(Hero unit) {
		Vector2 d = unit.getDestination();
		Vector2 p = unit.getPosition();
		Pathfinder pathfinder = new Pathfinder(world.getLevel());
		Node result = pathfinder.getPath(p, d);
		while (result.getParent() != null && 
				result.getParent().getParent() != null) {
			result = result.getParent();
		}
		return result.getPosition();
	}

	// input events
	public void moveHeroTo(Vector2 destination){
		Tile t = world.getLevel().getTile((int)destination.x, (int)destination.y);
		if(t == null || !t.getType().equals(Tile.Type.COLLIDABLE)) {
			hero.setDestination(destination);
		}
	}
}
