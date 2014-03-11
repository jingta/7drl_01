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
	
	private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private SpriteBatch spriteBatch;
	//private Texture blockTexture;
	//private Texture heroTexture;
	
	private TextureRegion blockTexture;
	
	private int width;
	private int height;
	private float ppux; // pixels per unit, x axis
	private float ppuy; // pixels per unit, y axis
	
	private boolean debug = false;
	ShapeRenderer debugRenderer = new ShapeRenderer(); //debugging?
	
	
	private TextureRegion heroFrame;
	private TextureRegion heroIdleLeft;
	private TextureRegion heroIdleRight;
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;
	
	
	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
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

	

	private void drawTiles() {
		for (Tile tile: world.getDrawableTiles((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)){
			spriteBatch.draw(blockTexture, tile.getPosition().x * ppux, tile.getPosition().y * ppuy, 
					Tile.SIZE * ppux, Tile.SIZE * ppuy);
		}
	}
	private void drawHero() {
		Hero hero = world.getHero();
		heroFrame = hero.isFacingLeft() ? heroIdleLeft : heroIdleRight;
		if (hero.getState().equals(Hero.State.WALKING)) {
			heroFrame = hero.isFacingLeft() ? 
					walkLeftAnimation.getKeyFrame(hero.getStateTime(), true) : 
					walkRightAnimation.getKeyFrame(hero.getStateTime(), true);
		} else if (hero.getState().equals(Hero.State.JUMPING)) {
			
		}
		spriteBatch.draw(heroFrame, hero.getPosition().x * ppux, hero.getPosition().y * ppuy, 
				Hero.SIZE * ppux, Hero.SIZE * ppuy);
	}
	private void drawDebug() {
		// render blocks
				debugRenderer.setProjectionMatrix(cam.combined);
				debugRenderer.begin(ShapeType.Line);
				for (Tile tile : world.getDrawableTiles((int)CAMERA_WIDTH, (int)CAMERA_HEIGHT)) {
					Rectangle r = tile.getBounds();
					//float x1 = block.getPosition().x + r.getX();
					//float y1 = block.getPosition().y + r.getY();
					debugRenderer.setColor(new Color(1, 0, 0, 1));
					//debugRenderer.rect(x1, y1, r.width, r.height);
					debugRenderer.rect(r.x, r.y, r.width, r.height);
				}
				// render hero
				Hero hero = world.getHero();
				Rectangle r = hero.getBounds();
				//float x1 = hero.getPosition().x + r.getX();
				//float y1 = hero.getPosition().y + r.getY();
				debugRenderer.setColor(new Color(0, 1, 0, 1));
				//debugRenderer.rect(x1, y1, r.width, r.height);
				debugRenderer.rect(r.x, r.y, r.width, r.height);
				debugRenderer.end();
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.ppux = (float)this.width / CAMERA_WIDTH;
		this.ppuy = (float)this.height / CAMERA_HEIGHT;
	}
	
	public void toggleDebug(){
		this.debug = !this.debug;
	}
	
	private void loadTextures() {
		// dawnlike texture pack is 16x16 tiles
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("image/textures/textures.pack"));
		heroIdleLeft = new TextureRegion(atlas.findRegion("DawnLike/Characters/Player0"), 16,48,16,16);
		heroIdleRight = new TextureRegion(heroIdleLeft);
		heroIdleRight.flip(true, false);
		blockTexture = new TextureRegion(atlas.findRegion("DawnLike/Objects/Tile"), 0, 0, 16, 16);
		TextureRegion[] walkLeftFrames = new TextureRegion[2];
		TextureRegion[] walkRightFrames = new TextureRegion[2];
		
		for (int i = 0; i < 2; i++) {
			walkLeftFrames[i] = atlas.findRegion("DawnLike/Characters/Player" + (i+1)%2);
			walkLeftFrames[i].setRegion(0, 0, 16, 16);
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
	}

	

}
