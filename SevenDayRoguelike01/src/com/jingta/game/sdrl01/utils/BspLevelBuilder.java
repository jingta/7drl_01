/**
 * 
 */
package com.jingta.game.sdrl01.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author jingta
 *
 */
public class BspLevelBuilder {

	public class Leaf {
		public static final int MIN_LEAF_SIZE = 6;
		public static final int MAX_LEAF_SIZE = 20;
		private Rectangle rect;
		private Leaf leftChild;
		private Leaf rightChild;
		private Rectangle room;

		private List<Rectangle> halls;
		
		public Leaf(int x, int y, int width, int height) {
			this.rect = new Rectangle(x, y, width, height);
			halls = new ArrayList<Rectangle>();
		}
		public Leaf(Rectangle rect) {
			this.rect = rect;
			halls = new ArrayList<Rectangle>();
		}
		
		/**
		 * Split the Leaf into two children if possible
		 * @return true if split
		 */
		public boolean split() {
			if (hasChildren()) {
				return false; // already split
			}
			boolean hSplit = Math.random() > 0.5;
			if (this.rect.width > this.rect.height * 1.25 ) {
				hSplit = false; //split vertically instead
			} else if (this.rect.height > this.rect.width * 1.25) {
				hSplit = true;
			}
			int max = (int)(hSplit ? this.rect.height : this.rect.width) - MIN_LEAF_SIZE;
			if (max < MIN_LEAF_SIZE) {
				return false; // too small to split
			}
			int splitPoint = (int) (MIN_LEAF_SIZE + ((max - MIN_LEAF_SIZE) * Math.random()));
			if (hSplit) {
				leftChild = new Leaf((int)this.rect.x, (int)this.rect.y,
						(int)this.rect.width, splitPoint);
				rightChild = new Leaf((int)this.rect.x, (int)this.rect.y + splitPoint,
						(int)this.rect.width, (int)this.rect.height - splitPoint);
			} else {
				leftChild = new Leaf((int)this.rect.x, (int)this.rect.y,
						splitPoint, (int)this.rect.height);
				rightChild = new Leaf((int)this.rect.x + splitPoint, (int)this.rect.y,
						(int)this.rect.width - splitPoint, (int)this.rect.height);
			}
			return true;
		}
		/**
		 * Check if this leaf has any child leaves
		 * @return false unless there is a right and left child
		 */
		public boolean hasChildren() {
			return this.leftChild != null && this.rightChild != null;
		}
		
		/**
		 * Create a corridor between two rooms 
		 * @param lRect
		 * @param rRect
		 */
		public void createHall(Rectangle lRect, Rectangle rRect) {
			//TODO: fix me, still doesnt render right
			
			int x1 = (int) (lRect.x + (Math.random() * (lRect.width -2)) +1);// from xPos +1 to xPos -1
			int y1 = (int) (lRect.y + (Math.random() * (lRect.height -2)) +1); // from yPos +1 to yPos -1
			int x2 = (int) (rRect.x + (Math.random() * (rRect.width -2)) +1);
			int y2 = (int) (rRect.y + (Math.random() * (rRect.height -2)) +1);
			
			Gdx.app.log("JING", "HALL from pt1 (" + x1 +", " +y1+ 
					") to pt2 (" + x2 +", " +y2+")");
			Gdx.app.log("JING", "---rect1 (" + lRect.x +", " +lRect.y+ 
					"; " + (lRect.x + lRect.width) +", " +(lRect.y+lRect.height)+")");
			Gdx.app.log("JING", "---rect2 (" + rRect.x +", " +rRect.y+ 
					"; " + (rRect.x + rRect.width) +", " +(rRect.y+rRect.height)+")");
			
	      	int thickness = 1;
			if (x1 > x2) {
				if (y1 > y2) {
					this.halls.add(new Rectangle(x2, y2, x1-x2, thickness)); // horiz
					this.halls.add(new Rectangle(x1, y2, thickness, y1-y2)); // vert
				} else {
					this.halls.add(new Rectangle(x2, y1, x1-x2, thickness)); // horiz
					this.halls.add(new Rectangle(x2, y1, thickness, y2-y1)); // vert
				}
			} else {
				if (y1 > y2) {
					this.halls.add(new Rectangle(x1, y2, x2-x1, thickness)); // horiz
					this.halls.add(new Rectangle(x1, y2, thickness, y1-y2)); // vert
				} else {
					this.halls.add(new Rectangle(x1, y1, x2-x1, thickness)); // horiz
					this.halls.add(new Rectangle(x2, y1, thickness, y2-y1)); // vert
				}
			}
			/*
			if (hallWidth < 0) {
				if (hallHeight < 0) {
					if (Math.random() > 0.5) {
						halls.add(new Rectangle(x2, y1, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x2, y2, 1, Math.abs(hallHeight)));
					} else {
						halls.add(new Rectangle(x2, y2, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x1, y2, 1, Math.abs(hallHeight)));
					}
				} else if (hallHeight > 0) {
					if (Math.random() > 0.5) {
						halls.add(new Rectangle(x2, y1, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x2, y1, 1, Math.abs(hallHeight)));
					} else {
						halls.add(new Rectangle(x2, y2, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x1, y1, 1, Math.abs(hallHeight)));
					}
				} else { // height == 0
					halls.add(new Rectangle(x2, y2, Math.abs(hallWidth), 1));
				}
			} else if (hallWidth > 0) {
				if (hallHeight < 0) {
					if (Math.random() > 0.5) {
						halls.add(new Rectangle(x1, y2, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x1, y2, 1, Math.abs(hallHeight)));
					} else {
						halls.add(new Rectangle(x1, y1, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x2, y2, 1, Math.abs(hallHeight)));
					}
				} else if (hallHeight > 0) {
					if (Math.random() > 0.5) {
						halls.add(new Rectangle(x1, y1, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x2, y1, 1, Math.abs(hallHeight)));
					} else {
						halls.add(new Rectangle(x1, y2, Math.abs(hallWidth), 1));
						halls.add(new Rectangle(x1, y1, 1, Math.abs(hallHeight)));
					}
				} else { // height == 0
					halls.add(new Rectangle(x1, y1, Math.abs(hallWidth), 1));
				}
			} else { // width == 0
				if (hallHeight < 0) {
					halls.add(new Rectangle(x2, y2, 1, Math.abs(hallHeight)));
				} else if (hallHeight > 0) {
					halls.add(new Rectangle(x1, y1, 1, Math.abs(hallHeight)));
				}
			}
			*/
		}
		
		/**
		 * Generate room and hallways for children
		 */
		public void createRooms() {
			if (hasChildren()) {
				this.leftChild.createRooms();
				this.rightChild.createRooms();
				this.createHall(leftChild.getRoom(), rightChild.getRoom());
			} else {
				// no children! make a room!
				int roomX, roomY, roomWidth, roomHeight;
				
		        // the room can be between 3 x 3 tiles to the size of the leaf - 2.
		        roomWidth = (int) ((Math.random() * (this.rect.width - 2 - 3))) + 3;
		        roomHeight = (int) ((Math.random() * (this.rect.height - 2 - 3))) + 3;
		        // place the room within the Leaf, but don't put it right 
		        // against the side of the Leaf (that would merge rooms together)
		        roomX = (int) (this.rect.x + (Math.random() * (this.rect.width - roomWidth + 0)) + 1);
		        roomY = (int) (this.rect.y + (Math.random() * (this.rect.height - roomHeight + 0)) + 1);
		        
		        Gdx.app.log("JING", "Creating a " + roomWidth + "x" + roomHeight + " room at " +roomX + ", " + roomY + " (rect w/h: " +(this.rect.x+this.rect.width) + "x" + (this.rect.y + this.rect.height) + ")");
		      	this.room = new Rectangle(roomX, roomY, roomWidth, roomHeight);
			}
		}
		
		/**
		 * Get a random room from somewhere in the tree
		 * @return a random leaf room
		 */
		public Rectangle getRoom() {
			if (this.room != null) {
				return room;
			} else {
				Rectangle lRoom = null;
				Rectangle rRoom = null;
				if (this.hasChildren()) {
					lRoom = leftChild.getRoom();
					rRoom = rightChild.getRoom();
				}
				if (lRoom == null & rRoom == null) {
					return null;
				} else if (Math.random() > 0.5) {
					return lRoom;
				} else {
					return rRoom;
				}
			}
		}
		public List<Rectangle> getRooms() {
			List<Rectangle> allRooms = new ArrayList<Rectangle>();
			if (this.hasChildren()) {
				allRooms.addAll(this.leftChild.getRooms());
				allRooms.addAll(this.rightChild.getRooms());
			} else {
				allRooms.add(this.room);
			}
			return allRooms;
		}
		public List<Rectangle> getHalls() {
			List<Rectangle> allHalls = new ArrayList<Rectangle>();
			if (this.hasChildren()) {
				allHalls.addAll(this.leftChild.getHalls());
				allHalls.addAll(this.rightChild.getHalls());
				allHalls.addAll(this.halls);
			} else {//if(this.halls != null) {
				allHalls.addAll(this.halls);
			}
			return allHalls;
		}
		
	}
	
	
	public BspLevelBuilder() {
		
	}
	List<Leaf> leaves;
	Leaf rootLeaf;
	public void generate(int width, int height) {
		this.leaves = new ArrayList<Leaf>();
		this.rootLeaf = new Leaf(0,0,width,height);
		leaves.add(rootLeaf);
		
		List<Leaf> leavesToSplit = new ArrayList<Leaf>();
		leavesToSplit.add(rootLeaf);
		
		while(!leavesToSplit.isEmpty()) {
			Gdx.app.log("JING", "leaves left:" + leavesToSplit.size());
			Leaf leaf = leavesToSplit.remove(0);
			if (!leaf.hasChildren()) {
				// split if one axis is greater than max, or 75% chance
				if (leaf.rect.width > Leaf.MAX_LEAF_SIZE ||
					leaf.rect.height > Leaf.MAX_LEAF_SIZE ||
					Math.random() > 0.25) {
					if (leaf.split()) {
						leaves.add(leaf.leftChild);
						leaves.add(leaf.rightChild);
						leavesToSplit.add(leaf.leftChild);
						leavesToSplit.add(leaf.rightChild);
					}
				}
			}
		}
		rootLeaf.createRooms();
	}
	
	public List<Rectangle> getHalls() {
		return this.rootLeaf.getHalls();
	}
	public List<Rectangle> getRooms() {
		return this.rootLeaf.getRooms();
	}

	/**
	 * Get the middle of a random room
	 * @return
	 */
	public Vector2 getStartPoint() {
		Rectangle r = this.rootLeaf.getRoom();
		return new Vector2(r.x + r.width/2, r.y + r.height/2);
	}

	
	
	
}
