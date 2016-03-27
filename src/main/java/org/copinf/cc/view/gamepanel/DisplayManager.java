package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.Coordinates;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class DisplayManager {

	/* Radius of a hexagon. */
	private final double radius;
	/* Width and height of a hexagon. */
	private Point2D.Double size;
	/* Horizontal and vertical scaling of the board along its axis (before rotation). */
	private Point2D.Double scale;
	/* Angle of the board rotation. */
	private double angle;
	/* Where the center of the board will be located on the screen. */
	private Point2D.Double origin;
	private final AbstractBoard board;

	public DisplayManager(
			final double radius,
			final double horizontalScaling, final double verticalScaling,
			final double angle,
			final double centerX, final double centerY,
			final AbstractBoard board) {
		this.radius = radius;
		this.size = new Point2D.Double(Math.sqrt(3) * radius, 2 * radius);
		this.scale = new Point2D.Double(horizontalScaling, verticalScaling);
		this.angle = angle;
		this.origin = new Point2D.Double(centerX, centerY);
		this.board = board;
	}


	public void setSize(final Point2D.Double size) {
		this.size = size;
	}

	public Point2D.Double getSize() {
		return this.size;
	}

	public void setScale(final Point2D.Double scale) {
		this.scale = scale;
	}

	public Point2D.Double getScale() {
		return this.scale;
	}

	public void setAngle(final double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return this.angle;
	}

	public void setOrigin(final Point2D.Double origin) {
		this.origin = origin;
	}

	public Point2D.Double getOrigin() {
		return this.origin;
	}


	/**
	 * Gives the screen coordinates given the board coordinates.
	 * NOTE: The origin is (0,0)
	 * @param x
	 * @param y
	 * @param z
	 * @return The screen coordinates
	 */
	private Point2D.Double _boardToScreen(final double x, final double y, final double z) {
		return new Point2D.Double((x - y) * size.x / 2.0, 3.0 * (x + y) * size.y / 4.0);
	}

	/**
	 * Gives the screen coordinates given the board coordinates and the tilt angle of the board.
	 * NOTE: The origin is (0,0)
	 * @param x
	 * @param y
	 * @param z
	 * @param theta
	 * @return The screen coordinates
	 */
	private Point2D.Double _boardToScreen(final double x, final double y, final double z,
			final double theta) {
		final double cos = Math.cos(theta);
		final double sin = Math.sin(theta);
		final Point2D.Double point = _boardToScreen(x, y, z);
		return new Point2D.Double(point.x * cos - sin * point.y, point.x * sin + cos * point.y);
	}

	/**
	 * Gives the screen coordinates given the board coordinates,
	 * the tilt angle of the board and the scaling factors.
	 * NOTE: The origin is (0,0)
	 * @param x
	 * @param y
	 * @param z
	 * @param theta
	 * @param horizontalScale
	 * @param verticalScale
	 * @return The screen coordinates
	 */
	private Point2D.Double _boardToScreen(final double x, final double y, final double z,
			final double theta, final double horizontalScale, final double verticalScale) {
		final Point2D.Double point = _boardToScreen(x, y, z, theta);
		return new Point2D.Double(point.x * horizontalScale, point.y * verticalScale);
	}

	/**
	 * Gives the screen coordinates given the board coordinates.
	 * @param x
	 * @param y
	 * @param z
	 * @return The screen coordinates
	 */
	public Point2D.Double boardToScreen(final double x, final double y, final double z) {
		final Point2D.Double point = _boardToScreen(x, y, z, angle, scale.x, scale.y);
		return new Point2D.Double(point.x + origin.x, point.y + origin.y);
	}

	/**
	 * Gives the screen coordinates given the square coordinates.
	 * @param coordinates
	 * @return The screen coordinates
	 */
	public Point2D.Double squareToScreen(final Coordinates coordinates) {
		return boardToScreen(coordinates.x, coordinates.y, coordinates.z);
	}


	/**
	 * Gives the board coordinates given the screen coordinates.
	 * NOTE: The origin is (0,0)
	 * @param u
	 * @param v
	 * @return The board coordinates
	 */
	private double[] _screenToBoard(final double u, final double v) {
		return new double[]{
			2.0 * v / (3.0 * size.y) + u / size.x,
			2.0 * v / (3.0 * size.y) - u / size.x,
			-4 * v / (3.0 * size.y)};
	}

	/**
	 * Gives the board coordinates given the screen coordinates given the rotation angle.
	 * NOTE: The origin is (0,0)
	 * @param u
	 * @param v
	 * @param theta The rotation angle applied to the board
	 * @return The screen coordinates
	 */
	private double[] _screenToBoard(final double u, final double v, final double theta) {
		final double cos = Math.cos(-theta);
		final double sin = Math.sin(-theta);
		return _screenToBoard(cos * u - sin * v, sin * u + cos * v);
	}

	/**
	 * Gives the board coordinates given the screen coordinates given
	 * the rotation angle and the scale factors.
	 * @param u
	 * @param v
	 * @param theta The rotation angle applied to the board
	 * @param su
	 * @param sv
	 * @return The screen coordinates
	 */
	private double[] _screenToBoard(final double u, final double v, final double theta,
			final double su, final double sv) {
		return _screenToBoard(u / su, v / sv, theta);
	}

	/**
	 * Gives the board coordinates given the screen coordinates, the rotation angle,
	 * the scale factors and the screen origin.
	 * @param u
	 * @param v
	 * @return The board coordinates
	 */
	public double[] screenToBoard(final double u, final double v) {
		return _screenToBoard(u - origin.x, v - origin.y, angle, scale.x, scale.y);
	}

	/**
	 * Gives the exact board coordinates given the screen coordinates.
	 * @param u
	 * @param v
	 * @return The screen coordinates
	 */
	public Coordinates screenToSquare(final double u, final double v) {
		double[] p = screenToBoard(u, v);
		return nearestSquare(p[0], p[1], p[2]);
	}

	/**
	 * Gives the exact board coordinates given the screen coordinates.
	 * @param point
	 * @return The screen coordinates
	 */
	public Coordinates screenToSquare(final Point2D point) {
		return screenToSquare(point.getX(), point.getY());
	}

	/**
	 * Returns the coordinates of the square in which (x,y,z) is located.
	 * @param x
	 * @param y
	 * @param z
	 * @return The square coordinates
	 */
	public Coordinates nearestSquare(final double x, final double y, final double z) {
		int q = (int) Math.round(x);
		int r = (int) Math.round(y);
		int s = (int) Math.round(z);

		final double q_diff = Math.abs(q - x);
		final double r_diff = Math.abs(r - y);
		final double s_diff = Math.abs(s - z);
		if (q_diff > r_diff && q_diff > s_diff) {
			q = -r - s;
		} else if (r_diff > s_diff) {
			r = -q - s;
		} else {
			s = -q - r;
		}

		final Coordinates result = new Coordinates(q, r, s);
		return board.coordinates().contains(result) ? result : null;
	}

	public Path2D.Double hexagon(final Coordinates h) {
		final Path2D.Double path = new Path2D.Double();
		final Point2D.Double center = boardToScreen(h.x, h.y, h.z);
		for (int i = 0; i <= 6; i++) {
			final double theta = i * Math.PI / 3.0 + Math.PI / 6.0 + angle;
			final Point2D.Double point = new Point2D.Double(
				center.x + scale.x * Math.cos(theta) * radius,
				center.y + scale.y * Math.sin(theta) * radius);
			if (i == 0) {
				path.moveTo(point.x, point.y);
			} else {
				path.lineTo(point.x, point.y);
			}
		}

		return path;
	}
}
