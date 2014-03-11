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
		this.hero.update(delta);
	}
	
	// input events
	public void moveHeroTo(Vector2 position){
		Tile t = world.getLevel().getTile((int)position.x, (int)position.y);
		if(t == null || !t.getType().equals(Tile.Type.COLLIDABLE)) {
			hero.setPosition(position);
		}
	}
}
