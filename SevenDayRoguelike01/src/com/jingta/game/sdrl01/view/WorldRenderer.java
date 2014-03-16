/**
 * 
 */
package com.jingta.game.sdrl01.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jingta.game.sdrl01.model.Hero;
import com.jingta.game.sdrl01.model.Tile;
import com.jingta.game.sdrl01.model.World;

/**
 * @author jingta
 * 
 */
public class WorldRenderer {
	private World world;

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private OrthographicCamera cam;

	private static final float RUNNING_FRAME_DURATION = 0.20f;

	private SpriteBatch spriteBatch;
	// private Texture blockTexture;
	// private Texture heroTexture;

	private TextureRegion wallTexture;
	private TextureRegion floorTexture;

	// TODO: make used?
	//private int width;
	//private int height;

	private boolean debug = false;
	ShapeRenderer debugRenderer = new ShapeRenderer(); // debugging?

	private TextureRegion heroFrame;
	private TextureRegion heroIdleLeft;
	private TextureRegion heroIdleRight;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, 0);
		this.cam.zoom = 10; // TODO: breaks pathfinding on 5
		this.cam.update();
		this.debug = debug;
		this.spriteBatch = new SpriteBatch();
		loadTextures();
	}

	public void render() {
		this.cam.position.x = world.getHero().getPosition().x;
		this.cam.position.y = world.getHero().getPosition().y;

		cam.update();
		spriteBatch.setProjectionMatrix(cam.combined); //NEW
		spriteBatch.begin();
		drawTiles();
		drawHero();
		spriteBatch.end();
		if (debug) {
			drawDebug();
		}
	}

	private void drawTiles() {
		//Gdx.app.log("JING", "=======Drawing Blocks========");
		TextureRegion tileTexture;
		
		//TODO: better way to do this?
		Vector3 bottomLeft = new Vector3(0,0, 0);
		Vector3 topRight = new Vector3(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), 0);
		this.cam.unproject(topRight);	
		this.cam.unproject(bottomLeft);
		for (Tile tile : world.getDrawableTiles(bottomLeft, topRight)) {
			if (tile.getType().equals(Tile.Type.COLLIDABLE)) {
				tileTexture = wallTexture;
				tileTexture = computeWallTexture(tile, wallTexture);
			} else {
				tileTexture = new TextureRegion(floorTexture, 0, -3*16, 16, 16);
			}
			spriteBatch.draw(tileTexture,
					tile.getPosition().x,
					tile.getPosition().y,
					Tile.SIZE, Tile.SIZE);
		}
	}

	private TextureRegion computeWallTexture(Tile tile, TextureRegion wallTexture) {
		Tile up = this.world.getLevel().getTile((int)tile.getPosition().x, (int)tile.getPosition().y+1);
		if (up == null) up = new Tile(new Vector2(tile.getPosition().x, tile.getPosition().y+1), Tile.Type.DECORATIVE);
		Tile down = this.world.getLevel().getTile((int)tile.getPosition().x, (int)tile.getPosition().y-1);
		if (down == null) down = new Tile(new Vector2(tile.getPosition().x, tile.getPosition().y-1), Tile.Type.DECORATIVE);
		Tile left = this.world.getLevel().getTile((int)tile.getPosition().x-1, (int)tile.getPosition().y);
		if (left == null) left = new Tile(new Vector2(tile.getPosition().x-1, tile.getPosition().y), Tile.Type.DECORATIVE);
		Tile right = this.world.getLevel().getTile((int)tile.getPosition().x+1, (int)tile.getPosition().y);
		if (right == null) right = new Tile(new Vector2(tile.getPosition().x+1, tile.getPosition().y), Tile.Type.DECORATIVE);
		
		if (up.getType().equals(Tile.Type.COLLIDABLE) &&
				down.getType().equals(Tile.Type.COLLIDABLE) &&
				left.getType().equals(Tile.Type.COLLIDABLE) &&
				right.getType().equals(Tile.Type.COLLIDABLE)) {
			// if top bottom left right are walls: 4,1
			return new TextureRegion(
					wallTexture, 
					3*16, 
					0*16, 16, 16);
		} else if (left.getType().equals(Tile.Type.COLLIDABLE) &&
				right.getType().equals(Tile.Type.COLLIDABLE) &&
				down.getType().equals(Tile.Type.COLLIDABLE)) {
			// if left, right, and bottom are walls 4, 0
			return new TextureRegion(
					wallTexture, 
					3*16, 
					-1*16, 16, 16);
		} else if (up.getType().equals(Tile.Type.COLLIDABLE) &&
				down.getType().equals(Tile.Type.COLLIDABLE) &&
				right.getType().equals(Tile.Type.COLLIDABLE)) {
			//top bottom right 3,1
			return new TextureRegion(
					wallTexture, 
					2*16, 
					0*16, 16, 16);
		} else if (up.getType().equals(Tile.Type.COLLIDABLE) &&
				down.getType().equals(Tile.Type.COLLIDABLE) &&
				left.getType().equals(Tile.Type.COLLIDABLE)) {
			// top bottom left 5,1
			return new TextureRegion(
					wallTexture, 
					4*16, 
					0*16, 16, 16);
		} else if (up.getType().equals(Tile.Type.COLLIDABLE) &&
				left.getType().equals(Tile.Type.COLLIDABLE) &&
				right.getType().equals(Tile.Type.COLLIDABLE)) {
			// top, left, right 4,2
			return new TextureRegion(
					wallTexture, 
					3*16, 
					1*16, 16, 16);
		} else if (right.getType().equals(Tile.Type.COLLIDABLE) &&
				down.getType().equals(Tile.Type.COLLIDABLE)) {
			// if right and bottom are walls  0, 0 -top left tile 
			return new TextureRegion(
					wallTexture, 
					-1*16, 
					-1*16, 16, 16);
		} else if (left.getType().equals(Tile.Type.COLLIDABLE) &&
				right.getType().equals(Tile.Type.COLLIDABLE)) {
			// if left and right are walls 1, 0 -top middle tile
			return new TextureRegion(
					wallTexture, 
					0*16, 
					-1*16, 16, 16);
		} else if (left.getType().equals(Tile.Type.COLLIDABLE) &&
				down.getType().equals(Tile.Type.COLLIDABLE)) {
			// if left and bottom are walls 2, 0
			return new TextureRegion(
					wallTexture, 
					1*16, 
					-1*16, 16, 16);
		} else if (up.getType().equals(Tile.Type.COLLIDABLE) &&
				down.getType().equals(Tile.Type.COLLIDABLE)) {
			// if top and bottom are walls 0, 1
			return new TextureRegion(
					wallTexture, 
					-1*16, 
					0*16, 16, 16);
		} else if (up.getType().equals(Tile.Type.COLLIDABLE) &&
				right.getType().equals(Tile.Type.COLLIDABLE)) {
			// if top and right are walls 0, 2
			return new TextureRegion(
					wallTexture, 
					-1*16, 
					1*16, 16, 16);
		} else if (up.getType().equals(Tile.Type.COLLIDABLE) &&
				left.getType().equals(Tile.Type.COLLIDABLE)) {
			// if top and left are walls 2, 2
			return new TextureRegion(
					wallTexture, 
					1*16, 
					1*16, 16, 16);
		} else if (down.getType().equals(Tile.Type.COLLIDABLE)) {
			// if down is wall 1,1 and flip y axis
			return new TextureRegion(
					wallTexture, 
					-1*16, 
					0*16, 16, 16);
		} else {
			// if no neighboring walls 1, 1
			return wallTexture;
		}
	}

	private void drawHero() {
		Hero hero = world.getHero();
		heroFrame = hero.isFacingLeft() ? heroIdleLeft : heroIdleRight;
		if (!hero.getState().equals(Hero.State.WALKING)) {
			heroFrame = hero.isFacingLeft() ? walkLeftAnimation.getKeyFrame(
					hero.getStateTime(), true) : walkRightAnimation
					.getKeyFrame(hero.getStateTime(), true);
		} else if (hero.getState().equals(Hero.State.JUMPING)) {

		}
		spriteBatch.draw(
				heroFrame, hero.getPosition().x,
				hero.getPosition().y, 
				Hero.SIZE,
				Hero.SIZE);
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		//TODO: better way to do this?
		Vector3 bottomLeft = new Vector3(0,0, 0);
		Vector3 topRight = new Vector3(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), 0);
		this.cam.unproject(topRight);	
		this.cam.unproject(bottomLeft);
		for (Tile tile : world.getDrawableTiles(bottomLeft, topRight)) {
			Rectangle r = tile.getBounds();
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(r.x, r.y, r.width, r.height);
		}
		// render hero
		Hero hero = world.getHero();
		Rectangle r = hero.getBounds();
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(r.x, r.y, r.width, r.height);
		debugRenderer.end();
	}

	public void setSize(int width, int height) {
		//this.width = width;
		//this.height = height;
	}

	public void toggleDebug() {
		this.debug = !this.debug;
	}

	private void loadTextures() {
		// dawnlike texture pack is 16x16 tiles
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("image/textures/textures.pack"));
		heroIdleLeft = new TextureRegion(
				atlas.findRegion("DawnLike/Characters/Player0"), 16, 48, 16, 16);
		heroIdleRight = new TextureRegion(heroIdleLeft);
		heroIdleRight.flip(true, false);
		wallTexture = new TextureRegion(
				atlas.findRegion("DawnLike/Objects/Wall"), 
				(1+(0*7))*16, //tileid + setId*7 
				(1+(2*3))*16, 16, 16); // tileID + setId*3
		floorTexture = new TextureRegion(
				atlas.findRegion("DawnLike/Objects/Floor"), 
				(1+(0*7))*16, 
				(1+(3*3))*16, 16, 16);
		TextureRegion[] walkLeftFrames = new TextureRegion[2];
		TextureRegion[] walkRightFrames = new TextureRegion[2];

		for (int i = 0; i < 2; i++) {
			walkLeftFrames[i] = new TextureRegion(atlas.findRegion("DawnLike/Characters/Player"
					+ (i + 1) % 2), 16, 48, 16, 16);
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkLeftFrames);
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkRightFrames);
	}

	public Vector2 toGameCoordinates(int x, int y) {
		//y = 0 -> 320 at bottom
		Vector3 v3 = new Vector3(x,  y, 0);
		this.cam.unproject(v3);
		Gdx.app.log("JING", "x: " + v3.x + " y:" + v3.y);
		return new Vector2((float)Math.floor(v3.x), (float)Math.floor(v3.y));
	}

}
