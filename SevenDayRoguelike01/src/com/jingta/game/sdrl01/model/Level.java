/**
 * 
 */
package com.jingta.game.sdrl01.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jingta.game.sdrl01.model.Tile.Type;
import com.jingta.game.sdrl01.utils.BspLevelBuilder;

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
		return this.tiles;
	}
	public Tile getTile(int x, int y) {
		if (x >= 0 && x < this.tiles.length) { 
			if (y >= 0 && y < this.tiles[x].length) {
				return this.tiles[x][y];
			}
		}
		return null;
	}
	public Level() {
		generateLevel();
	}
	
	private void generateLevel() {
		//TODO: make it work
		this.width = 50;
		this.height = 50;
		
		BspLevelBuilder bspLb = new BspLevelBuilder();
		Gdx.app.log("JING", "=======Beginning generate========" + System.currentTimeMillis());
		bspLb.generate(this.width, this.height);
		Gdx.app.log("JING", "=======Ending generate========" + System.currentTimeMillis());
		this.tiles = new Tile[this.width][this.height];
		
		Tile tile = null;
		// turn rooms into ground tiles
		for (Rectangle rect : bspLb.getRooms()) {
			Gdx.app.log("JING", "setting room: " + rect.x + ", " + rect.y + " to " + (rect.x + rect.width) + ", " + (rect.y + rect.height));
			for (int x = (int) rect.x; x < rect.x + rect.width; x++) {
				for (int y = (int) rect.y; y < rect.y + rect.height; y++){
					tile = new Tile(new Vector2(x, y), Type.DECORATIVE);
					this.tiles[x][y] = tile;
				}
			}
		}
		
		// turn halls into ground tiles
		for (Rectangle rect : bspLb.getHalls()) {
			Gdx.app.log("JING", "setting HALL: " + rect.x + ", " + rect.y + " to " + (rect.x + rect.width) + ", " + (rect.y + rect.height));
			
			for (int x = (int) rect.x; x < rect.x + rect.width; x++) {
				for (int y = (int) rect.y; y < rect.y + rect.height; y++){
					tile = new Tile(new Vector2(x, y), Type.DECORATIVE);
					this.tiles[x][y] = tile;
				}
			}
		}
		
		// find a valid start point
		this.startPoint = bspLb.getStartPoint();
		
		//add some walls
		/*
		Tile up, down, left, right, upLeft, upRight, downLeft, downRight;
		for (int x = 0; x < this.width; x++){
			for (int y = 0; y < this.height; y++) {
				tile = this.tiles[x][y];
				if (tile != null && tile.getType().equals(Tile.Type.DECORATIVE)) {
					// set empty neighbors to walls
					up = getTile(x, y+1);
					if (up == null && y+1 < this.height) this.tiles[x][y+1] = new Tile(new Vector2(x, y+1), Tile.Type.COLLIDABLE);
					down = getTile(x, y-1);
					if (down == null && y-1 >= 0) this.tiles[x][y-1] = new Tile(new Vector2(x, y-1), Tile.Type.COLLIDABLE);
					left = getTile(x-1, y);
					if (left == null && x-1 >= 0) this.tiles[x-1][y] = new Tile(new Vector2(x-1, y), Tile.Type.COLLIDABLE);
					right = getTile(x+1, y);
					if (right == null && x+1 < this.width) this.tiles[x+1][y] = new Tile(new Vector2(x+1, y), Tile.Type.COLLIDABLE);
					//diagonals
					upLeft = getTile(x-1, y+1);
					if (upLeft == null && x-1 >= 0 && y+1 < this.height) this.tiles[x-1][y+1] = new Tile(new Vector2(x-1, y+1), Tile.Type.COLLIDABLE);
					upRight = getTile(x+1, y+1);
					if (upRight == null && x+1 < this.width && y+1 < this.height) this.tiles[x+1][y+1] = new Tile(new Vector2(x+1, y+1), Tile.Type.COLLIDABLE);
					downLeft = getTile(x-1, y-1);
					if (downLeft == null && x-1 >= 0 && y-1 >= 0) this.tiles[x-1][y-1] = new Tile(new Vector2(x-1, y-1), Tile.Type.COLLIDABLE);
					downRight = getTile(x+1, y-1);
					if (downRight == null && x+1 < this.width && y-1 >= 0) this.tiles[x+1][y-1] = new Tile(new Vector2(x+1, y-1), Tile.Type.COLLIDABLE);
				}
			}
		}
		*/
	}
	@SuppressWarnings("unused")
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
