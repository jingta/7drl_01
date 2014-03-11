/**
 * 
 */
package com.jingta.game.sdrl01.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author jingta
 *
 */
public class Tile {
public static final float SIZE = 1.0f;
	public enum Type {COLLIDABLE, INTERACTABLE, ATTACKABLE, COLLECTIBLE, DECORATIVE}
	
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	Type type;
	
	public Type getType() {
		return this.type;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Vector2 getPosition(){
		return position;
	}
	
	public Tile(Vector2 position, Type type) {
		this.type = type;
		this.position = position;
		this.bounds.setX(position.x);
        this.bounds.setY(position.y);
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
}
