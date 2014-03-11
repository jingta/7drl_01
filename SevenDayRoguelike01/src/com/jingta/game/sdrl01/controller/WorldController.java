/**
 * 
 */
package com.jingta.game.sdrl01.controller;

import com.jingta.game.sdrl01.model.Hero;
import com.jingta.game.sdrl01.model.World;


/**
 * @author jingta
 *
 */
public class WorldController {

	private World world;
	private Hero hero;
	
	public WorldController(World world) {
		this.world = world;
		this.hero = this.world.getHero();
	}
	
	public void update(float delta){
		this.hero.update(delta);
	}
}
