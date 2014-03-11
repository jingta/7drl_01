package com.jingta.game.sdrl01;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "SevenDayRoguelike01";
		//cfg.useGL30 = false;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new SevenDayRoguelike01(), cfg);
	}
}
