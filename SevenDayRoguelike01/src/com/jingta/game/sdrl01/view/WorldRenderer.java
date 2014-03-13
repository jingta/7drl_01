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
		this.cam.update();
		this.debug = debug;
		this.spriteBatch = new SpriteBatch();
		loadTextures();
	}

	public void render() {
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
		//when cut in 1/10, everything is only drawn in 1/10th
		//when doubled, only see 5 tiles
		TextureRegion tileTexture;
		for (Tile tile : world.getDrawableTiles((int) CAMERA_WIDTH, (int) CAMERA_HEIGHT)) {
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
					tile.getPosition().x * this.ppux,
					tile.getPosition().y * this.ppuy,
					Tile.SIZE * this.ppux, 
					Tile.SIZE * this.ppuy);
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
				heroFrame, hero.getPosition().x * ppux + ppux*.10f,
				hero.getPosition().y * ppuy, 
				Hero.SIZE * ppux *0.8f,
				Hero.SIZE * ppuy );
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Tile tile : world.getDrawableTiles((int) CAMERA_WIDTH, 
				(int) CAMERA_HEIGHT)) {
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
		return new Vector2((float)Math.floor(x / ppux), (float)Math.floor(CAMERA_HEIGHT - y/ppuy));
	}

}
