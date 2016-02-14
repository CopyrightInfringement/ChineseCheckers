package org.copinf.cc.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CoordinatesTest {

	@Test
	public void testConstructorX() {
		final Coordinates coord = new Coordinates(1, 2);
		assertEquals(coord.x, 1);
	}

	@Test
	public void testConstructorY() {
		final Coordinates coord = new Coordinates(1, 2);
		assertEquals(coord.y, 2);
	}

	@Test
	public void testEqualsReflexive() {
		final Coordinates coord = new Coordinates(0, 0);
		assertEquals(coord, coord);
	}

	@Test
	public void testEqualsSymmetric1() {
		final Coordinates coord1 = new Coordinates(0, 0);
		final Coordinates coord2 = new Coordinates(0, 0);
		if (coord1.equals(coord2)) {
			assertEquals(coord1, coord2);
		}
	}

	@Test
	public void testEqualsSymmetric2() {
		final Coordinates coord1 = new Coordinates(0, 0);
		final Coordinates coord2 = new Coordinates(1, 1);
		if (!coord1.equals(coord2)) {
			assertNotEquals(coord1, coord2);
		}
	}

	@Test
	public void testEqualsTransitive1() {
		final Coordinates coord1 = new Coordinates(0, 0);
		final Coordinates coord2 = new Coordinates(0, 0);
		final Coordinates coord3 = new Coordinates(0, 0);
		if (coord1.equals(coord2) && coord2.equals(coord3)) {
			assertEquals(coord1, coord3);
		}
	}

	@Test
	public void testEqualsConsistent() {
		final Coordinates coord = new Coordinates(0, 0);
		if (coord.equals(coord)) {
			assertEquals(coord, coord);
		}
	}

	@Test
	public void testEqualsNull() {
		final Coordinates coord = new Coordinates(0, 0);
		assertNotEquals(coord, null);
	}

	@Test
	public void hashCodeEqualsTest() {
		final Coordinates coord1 = new Coordinates(0, 0);
		final Coordinates coord2 = new Coordinates(0, 0);
		if (coord1.equals(coord2)) {
			assertEquals(coord1.hashCode(), coord2.hashCode());
		}
	}

	@Test
	public void hashCodeNotEqualsTest() {
		final Coordinates coord1 = new Coordinates(0, 0);
		final Coordinates coord2 = new Coordinates(1, 1);
		if (!coord1.equals(coord2)) {
			assertNotEquals(coord1.hashCode(), coord2.hashCode());
		}
	}
}
