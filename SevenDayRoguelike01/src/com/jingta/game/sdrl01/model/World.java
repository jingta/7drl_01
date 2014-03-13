/**
 * 
 */
package com.jingta.game.sdrl01.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;


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
	
	public List<Tile> getDrawableTiles(int width, int height) {
		int x = (int)hero.getPosition().x - width;
		int y = (int)hero.getPosition().y - height;
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		int x2 = x + 2*width;
		int y2 = y + 2*height;
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
