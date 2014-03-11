/**
 * 
 */
package com.jingta.game.sdrl01.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

/**
 * @author jingta
 * 
 */
public class TexturePack {
	public static void main(String[] args) throws Exception {
		String inputDir = "./../SevenDayRoguelike01-android/assets/image";
		String outputDir = "./../SevenDayRoguelike01-android/assets/image/textures";
		String packFileName = "textures.pack";
		TexturePacker2.process(inputDir, outputDir, packFileName);
	}
}
