/**
 * 
 */
package com.jingta.game.sdrl01.model;

import com.badlogic.gdx.math.Vector2;

/**
 * @author jingta
 *
 */
public class Level {
	private int width, height;
	private Tile[][] tiles;
	
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
		return tiles[x][y];
	}
	public Level() {
		loadDemoLevel();
	}
	
	private void loadDemoLevel() {
		width = 10;
		height = 7;
		tiles = new Tile[width][height];
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				tiles[col][row] = null;
			}
		}
		
		for (int col = 0; col < 10; col++) {
			tiles[col][0] = new Tile(new Vector2(col, 0));
			tiles[col][6] = new Tile(new Vector2(col, 6));
			if (col > 2) {
				tiles[col][1] = new Tile(new Vector2(col, 1));
			}
		}
		tiles[9][2] = new Tile(new Vector2(9, 2));
		tiles[9][3] = new Tile(new Vector2(9, 3));
		tiles[9][4] = new Tile(new Vector2(9, 4));
		tiles[9][5] = new Tile(new Vector2(9, 5));

		tiles[6][3] = new Tile(new Vector2(6, 3));
		tiles[6][4] = new Tile(new Vector2(6, 4));
		tiles[6][5] = new Tile(new Vector2(6, 5));
	}
}
