package org.copinf.cc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoordinatesTest {

	@Test
	public void testConstructorX() {
		final Coordinates coord = new Coordinates(1, 2, 0);
		assertEquals(coord.getX(), 1);
	}

	@Test
	public void testConstructorY() {
		final Coordinates coord = new Coordinates(1, 2, 0);
		assertEquals(coord.getY(), 2);
	}

	@Test
	public void testConstructorZ() {
		final Coordinates coord = new Coordinates(1, 2, 0);
		assertEquals(coord.getZ(), 0);
	}

	@Test
	public void testEqualsReflexive() {
		final Coordinates coord = new Coordinates(0, 0, 0);
		assertEquals(coord, coord);
	}

	@Test
	public void testEqualsSymmetric1() {
		final Coordinates coord1 = new Coordinates(0, 0, 0);
		final Coordinates coord2 = new Coordinates(0, 0, 0);
		if (coord1.equals(coord2)) {
			assertEquals(coord1, coord2);
		}
	}

	@Test
	public void testEqualsSymmetric2() {
		final Coordinates coord1 = new Coordinates(0, 0, 0);
		final Coordinates coord2 = new Coordinates(1, 1, 0);
		if (!coord1.equals(coord2)) {
			assertNotEquals(coord1, coord2);
		}
	}

	@Test
	public void testEqualsTransitive1() {
		final Coordinates coord1 = new Coordinates(0, 0, 0);
		final Coordinates coord2 = new Coordinates(0, 0, 0);
		final Coordinates coord3 = new Coordinates(0, 0, 0);
		if (coord1.equals(coord2) && coord2.equals(coord3)) {
			assertEquals(coord1, coord3);
		}
	}

	@Test
	public void testEqualsConsistent() {
		final Coordinates coord = new Coordinates(0, 0, 0);
		if (coord.equals(coord)) {
			assertEquals(coord, coord);
		}
	}

	@Test
	public void testEqualsNull() {
		final Coordinates coord = new Coordinates(0, 0, 0);
		assertNotEquals(coord, null);
	}

	@Test
	public void hashCodeEqualsTest() {
		final Coordinates coord1 = new Coordinates(0, 0, 0);
		final Coordinates coord2 = new Coordinates(0, 0, 0);
		if (coord1.equals(coord2)) {
			assertEquals(coord1.hashCode(), coord2.hashCode());
		}
	}

	@Test
	public void hashCodeNotEqualsTest() {
		final Coordinates coord1 = new Coordinates(0, 0, 0);
		final Coordinates coord2 = new Coordinates(1, 1, 0);
		if (!coord1.equals(coord2)) {
			assertNotEquals(coord1.hashCode(), coord2.hashCode());
		}
	}

	@Test
	public void testAdjacencyNotReflexive() {
		final Coordinates coord = new Coordinates(1, 1, 0);
		assertFalse(coord.isAdjacentTo(coord));
	}

	@Test
	public void testAdjacencySymmetric() {
		final Coordinates coord1 = new Coordinates(0, 0, 0);
		final Coordinates coord2 = new Coordinates(0, 1, 0);
		if (coord1.isAdjacentTo(coord2)) {
			assertTrue(coord2.isAdjacentTo(coord1));
		}
	}
}
