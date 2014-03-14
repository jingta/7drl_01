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

	private int width;
	private int height;
	private float ppux; // pixels per unit, x axis
	private float ppuy; // pixels per unit, y axis

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
		this.cam.zoom = 3;
		this.cam.update();
		this.debug = debug;
		this.spriteBatch = new SpriteBatch();
		loadTextures();
	}

	public void render() {
		//this.cam.position.x = (world.getHero().getPosition().x *16);
		//this.cam.position.y = (world.getHero().getPosition().y *16);
		//Vector3 heroPos = new Vector3(this.world.getHero().getPosition(), 0);
		//this.cam.unproject(heroPos);
		this.cam.position.x = world.getHero().getPosition().x;
		this.cam.position.y = world.getHero().getPosition().y;

		//Gdx.app.log("JING", "cam: " + world.getHero().getPosition().x + ", " + world.getHero().getPosition().y);
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

	private boolean resized = false;
	private void drawTiles() {
		if (resized) {
		Gdx.app.log("JING", "=======Drawing Blocks========");
		}
		TextureRegion tileTexture;
		
		//TODO: better way to do this?
		Vector3 bottomLeft = new Vector3(0,0, 0);
		Vector3 topRight = new Vector3(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight(), 0);
		this.cam.unproject(topRight);	
		this.cam.unproject(bottomLeft);
		for (Tile tile : world.getDrawableTiles(bottomLeft, topRight)) {
			if (tile.getType().equals(Tile.Type.COLLIDABLE)) {
				tileTexture = wallTexture;
				if (tile.getPosition().x == 0 && tile.getPosition().y == 0) {
					tileTexture = new TextureRegion(
							wallTexture, 
							-1*16, 
							1*16, 16, 16);	
				} else if (tile.getPosition().x == 0) {
					tileTexture = new TextureRegion(
							wallTexture, 
							-1*16, 
							0, 16, 16);
				} else if (tile.getPosition().y == 13 || tile.getPosition().y == 0){
					tileTexture = new TextureRegion(
							wallTexture, 
							0, 
							-1*16, 16, 16);
				}
			} else {
				tileTexture = //floorTexture;
						new TextureRegion(
						floorTexture, 
						0, 
						-3*16, 16, 16);
			}
			spriteBatch.draw(tileTexture,
					tile.getPosition().x,
					tile.getPosition().y,
					Tile.SIZE, 
					Tile.SIZE);
			if (resized) {
			Gdx.app.log("JING", "draw " + tile.getPosition().x * ppux + ", " +
					+ tile.getPosition().y * ppuy + ", " + 
					Tile.SIZE * ppux + ", " +
					Tile.SIZE * ppuy);
			}
		}
		resized = false;
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
		this.width = width;
		this.height = height;
		this.ppux = ((float) this.width) / CAMERA_WIDTH;
		this.ppuy = ((float) this.height) / CAMERA_HEIGHT;
		Gdx.app.log("JING", "=======Setting Size========");
		Gdx.app.log("JING", "width: " + width);
		Gdx.app.log("JING", "height: " + height);
		Gdx.app.log("JING", "ppux: " + ppux);
		Gdx.app.log("JING", "ppuy: " + ppuy);
		resized = true;
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
		Gdx.app.log("JING", "x: " + x/ppuy + " y:" + (CAMERA_HEIGHT - y/ppuy));
		//return new Vector2((float)Math.floor(x / ppux), (float)Math.floor(CAMERA_HEIGHT - y/ppuy));
		Vector3 v3 = new Vector3(x,  y, 0);
		this.cam.unproject(v3);
		Gdx.app.log("JING", "x: " + v3.x + " y:" + v3.y);
		return new Vector2((float)Math.floor(v3.x), (float)Math.floor(v3.y));
	}

}
