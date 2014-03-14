/**
 * 
 */
package com.jingta.game.sdrl01.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector3;



/**
 * @author jingta
 *
 */
public class World {
	Hero hero;
	Level level;
	
	public Hero getHero() {
		return hero;
	}
	public Level getLevel() {
		return level;
	}
	
	public List<Tile> getDrawableTiles(Vector3 bottomLeft, Vector3 topRight) {
		int x = (int) Math.floor(bottomLeft.x);
		int y = (int) Math.floor(topRight.y);
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		int x2 = (int) Math.ceil(topRight.x);
		int y2 = (int) Math.ceil(bottomLeft.y);
		if (x2 > level.getWidth()) x2 = level.getWidth() - 1;
		if (y2 > level.getHeight()) y2 = level.getHeight() - 1;
		
		List<Tile> tiles = new ArrayList<Tile>();
		Tile tile;
		
		for (int col = x; col <= x2; col++){
			for (int row = y; row <= y2; row++) {
				tile = level.getTile(col, row);
				if (tile != null) tiles.add(tile);
			}
		}
		
		return tiles;
	}
	
	
	public World() {
		createDemoWorld();
	}
	
	public void createDemoWorld(){
		this.level = new Level();
		this.hero = new Hero(level.getStartPoint());
	}
	
}
