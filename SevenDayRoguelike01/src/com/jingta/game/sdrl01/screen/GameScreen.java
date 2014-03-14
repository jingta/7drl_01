/**
 * 
 */
package com.jingta.game.sdrl01.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.jingta.game.sdrl01.controller.WorldController;
import com.jingta.game.sdrl01.model.World;
import com.jingta.game.sdrl01.view.WorldRenderer;

/**
 * @author jingta
 *
 */
public class GameScreen implements Screen, InputProcessor {
	private World world;
	private WorldRenderer renderer;
	private WorldController controller;
	
	//private int width, height;
	
	
	////////////////////////////////////////////// com.badlogic.gdx.Screen
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		//this.width = width;
		//this.height = height;
	}
	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, true);//false);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
	}
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
	
	
	//////////////////////////////////// com.badlogic.gdx.InputProcessor
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		controller.moveHeroTo(renderer.toGameCoordinates(screenX,screenY));
		return true; //input handled!
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
