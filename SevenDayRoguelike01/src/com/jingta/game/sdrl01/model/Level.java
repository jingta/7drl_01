/**
 * 
 */
package com.jingta.game.sdrl01.model;

import com.badlogic.gdx.math.Vector2;
import com.jingta.game.sdrl01.model.Tile.Type;

/**
 * @author jingta
 *
 */
public class Level {
	private int width, height;
	private Tile[][] tiles;
	private Vector2 startPoint;
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public Tile[][] getTiles() {
		return tiles;
	}
	public Tile getTile(int x, int y) {
		if (x >= 0 && x < tiles.length) { 
			if (y >= 0 && y < tiles[x].length) {
				return tiles[x][y];
			}
		}
		return null;
	}
	public Level() {
		loadDemo2Level();
	}
	
	private void generateLevel() {
		//TODO:
	}
	
	private void loadDemo2Level() {
		this.width = 20;
		this.height = 14;
		
		this.startPoint = new Vector2(10,7);
		
		this.tiles = new Tile[width][height];
		Tile tile = null;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == 0 || y == 0 || x == width-1 || y == height-1) {
					// walls on edges
					tile = new Tile(new Vector2(x, y), Type.COLLIDABLE);
				} else {
					tile = new Tile(new Vector2(x, y), Type.DECORATIVE);
				}
				tiles[x][y] = tile;
			}
		}
		
		///TESTING
		
		for (int col = 0; col < 10; col++) {
			tiles[col][0] = new Tile(new Vector2(col, 0), Type.COLLIDABLE);
			tiles[col][6] = new Tile(new Vector2(col, 6), Type.COLLIDABLE);
			if (col > 2) {
				tiles[col][1] = new Tile(new Vector2(col, 1), Type.COLLIDABLE);
			}
		}
		tiles[9][2] = new Tile(new Vector2(9, 2), Type.COLLIDABLE);
		tiles[9][3] = new Tile(new Vector2(9, 3), Type.COLLIDABLE);
		//tiles[9][4] = new Tile(new Vector2(9, 4), Type.COLLIDABLE);
		tiles[9][5] = new Tile(new Vector2(9, 5), Type.COLLIDABLE);

		tiles[6][3] = new Tile(new Vector2(6, 3), Type.COLLIDABLE);
		tiles[6][4] = new Tile(new Vector2(6, 4), Type.COLLIDABLE);
		tiles[6][5] = new Tile(new Vector2(6, 5), Type.COLLIDABLE);
		
	}
	@SuppressWarnings("unused")
	private void loadDemo1Level() {
		this.width = 10;
		this.height = 7;
		this.startPoint = new Vector2(7,2);
		
		this.tiles = new Tile[width][height];
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				tiles[col][row] = null;
			}
		}
		
		for (int col = 0; col < 10; col++) {
			tiles[col][0] = new Tile(new Vector2(col, 0), Type.COLLIDABLE);
			tiles[col][6] = new Tile(new Vector2(col, 6), Type.COLLIDABLE);
			if (col > 2) {
				tiles[col][1] = new Tile(new Vector2(col, 1), Type.COLLIDABLE);
			}
		}
		tiles[9][2] = new Tile(new Vector2(9, 2), Type.COLLIDABLE);
		tiles[9][3] = new Tile(new Vector2(9, 3), Type.COLLIDABLE);
		tiles[9][4] = new Tile(new Vector2(9, 4), Type.COLLIDABLE);
		tiles[9][5] = new Tile(new Vector2(9, 5), Type.COLLIDABLE);

		tiles[6][3] = new Tile(new Vector2(6, 3), Type.COLLIDABLE);
		tiles[6][4] = new Tile(new Vector2(6, 4), Type.COLLIDABLE);
		tiles[6][5] = new Tile(new Vector2(6, 5), Type.COLLIDABLE);
	}
	public Vector2 getStartPoint() {
		return this.startPoint;
	}
}
