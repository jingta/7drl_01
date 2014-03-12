package com.jingta.game.sdrl01.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.jingta.game.sdrl01.model.Level;
import com.jingta.game.sdrl01.model.Tile;
import com.jingta.game.sdrl01.utils.Pathfinder.Node;

public class PathfinderTest {

	@Test
	public void testNodeEquals() {
		Vector2 pos = new Vector2(1f, 2f);
		Node node = new Pathfinder.Node(pos, null);
		assertTrue("Vector2 not equal!", node.equals(pos));
		assertTrue("Tile not equal!", node.equals(new Tile(pos, Tile.Type.COLLIDABLE)));
		assertTrue("Node not equal itself!", node.equals(node));
		assertFalse("Node is equal to a string??", node.equals("poo"));
		Node node2 = new Pathfinder.Node(pos, null);
		assertTrue("Node is not equal to similar", node.equals(node2));
	}
	
	@Test
	public void testGetPathSelf() {
		Level l = new Level();
		Pathfinder p = new Pathfinder(l);
		Vector2 pos = new Vector2(1f, 2f);
		Vector2 dest = new Vector2(1f, 2f);
		Node n = p.getPath(pos, dest);
		Node expected = new Pathfinder.Node(pos, null);
		assertEquals(expected, n);
	}
	
	@Test
	public void testGetPath() {
		Level l = new Level();
		Pathfinder p = new Pathfinder(l);
		Vector2 pos = new Vector2(1f, 1f);
		Vector2 dest = new Vector2(7f, 5f);
		// path on demo level should be 
		// 2,1;2,2;3,2;4,2;5,2;6,2;7,2;7,3;7,4;7,5
		Node n1 = new Node(new Vector2(1f, 1f), null);
		Node n2 = new Node(new Vector2(2f, 1f), n1);
		Node n3 = new Node(new Vector2(2f, 2f), n2);
		Node n4 = new Node(new Vector2(3f, 2f), n3);
		Node n5 = new Node(new Vector2(4f, 2f), n4);
		Node n6 = new Node(new Vector2(5f, 2f), n5);
		Node n7 = new Node(new Vector2(6f, 2f), n6);
		Node n8 = new Node(new Vector2(7f, 2f), n7);
		Node n9 = new Node(new Vector2(7f, 3f), n8);
		Node n10 = new Node(new Vector2(7f, 4f), n9);
		Node n11 = new Node(new Vector2(7f, 5f), n10);

		Node result = p.getPath(pos, dest);
		assertTrue(result.equals(n11));
		assertTrue(result.
				getParent().
				equals(n10));
		assertTrue(result.
				getParent().getParent().
				equals(n9));
		assertTrue(result.
				getParent().getParent().getParent().
				equals(n8));
		assertTrue(result.
				getParent().getParent().getParent().getParent().
				equals(n7));
		assertTrue(result.
				getParent().getParent().getParent().getParent().getParent().
				equals(n6));
		assertTrue(result.
				getParent().getParent().getParent().getParent().getParent().
				getParent().
				equals(n5));
		assertTrue(result.
				getParent().getParent().getParent().getParent().getParent().
				getParent().getParent().
				equals(n4));
		assertTrue(result.
				getParent().getParent().getParent().getParent().getParent().
				getParent().getParent().getParent().
				equals(n3));
		assertTrue(result.
				getParent().getParent().getParent().getParent().getParent().
				getParent().getParent().getParent().getParent().
				equals(n2));
		assertTrue(result.
				getParent().getParent().getParent().getParent().getParent().
				getParent().getParent().getParent().getParent().getParent().
				equals(n1));
		assertTrue(result.
				getParent().getParent().getParent().getParent().getParent().
				getParent().getParent().getParent().getParent().getParent().
				getParent().
				equals(null));
	}
	
	

}
