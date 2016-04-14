package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.Coordinates;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Handles the display of the board.
 */
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

	/**
	 * Constructs a new DisplayManager.
	 * @param radius The radius of an hexagon
	 * @param horizontalScaling The horizontal scaling factor
	 * @param verticalScaling The vertical scaling factor
	 * @param angle The rotation angle
	 * @param centerX The abscissa of the center of the board on the screen
	 * @param centerY The ordinates of the center of the board on the screen
	 * @param board The board to display
	 */
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

	/**
	 * Set the size of an hexagon.
	 * @param size The size of an hexagon
	 */
	public void setSize(final Point2D.Double size) {
		this.size = size;
	}

	/**
	 * Returns the size of an hexagon.
	 * @return the size of an hexagon
	 */
	public Point2D.Double getSize() {
		return this.size;
	}

	/**
	 * Sets the scaling factor of the board.
	 * @param scale the scaling factor.
	 */
	public void setScale(final Point2D.Double scale) {
		this.scale = scale;
	}

	/**
	 * Returns the scaling factor of he board.
	 * @return the scaling factor
	 */
	public Point2D.Double getScale() {
		return this.scale;
	}

	/**
	 * Sets the rotation angle of the board.
	 * @param angle the rotation angle.
	 */
	public void setAngle(final double angle) {
		this.angle = angle;
	}

	/**
	 * Returns the rotation angle of the board.
	 * @return the rotation angle of the board
	 */
	public double getAngle() {
		return this.angle;
	}

	/**
	 * Sets the position of the center of the board on the screen.
	 * @param origin where the center of the board is displayed on the screen
	 */
	public void setOrigin(final Point2D.Double origin) {
		this.origin = origin;
	}

	/**
	 * Returns the position of the center of the board on the screen.
	 * @return the position of the center of the board on the screen
	 */
	public Point2D.Double getOrigin() {
		return this.origin;
	}

	/**
	 * Gives the screen coordinates given the board coordinates and the tilt angle of the board.
	 * NOTE: The origin is (0,0)
	 * @param theta the angle
	 * @return The screen coordinates
	 */
	private Point2D.Double boardToScreen(final double x, final double y, final double z,
			final double theta) {
		final double cos = Math.cos(theta);
		final double sin = Math.sin(theta);
		final Point2D.Double point = boardToScreen(x, y, z);
		return new Point2D.Double(point.x * cos - sin * point.y, point.x * sin + cos * point.y);
	}

	/**
	 * Gives the screen coordinates given the board coordinates,
	 * the tilt angle of the board and the scaling factors.
	 * NOTE: The origin is (0,0)
	 * @return The screen coordinates
	 */
	private Point2D.Double boardToScreen(final double x, final double y, final double z,
			final double theta, final double horizontalScale, final double verticalScale) {
		final Point2D.Double point = boardToScreen(x, y, z, theta);
		return new Point2D.Double(point.x * horizontalScale, point.y * verticalScale);
	}

	/**
	 * Gives the screen coordinates given the board coordinates.
	 * @return The screen coordinates
	 */
	public Point2D.Double boardToScreen(final double x, final double y, final double z) {
		final Point2D.Double point = boardToScreen(x, y, z, angle, scale.x, scale.y);
		return new Point2D.Double(point.x + origin.x, point.y + origin.y);
	}

	/**
	 * Gives the screen coordinates given the square coordinates.
	 * @return The screen coordinates
	 */
	public Point2D.Double squareToScreen(final Coordinates coordinates) {
		return boardToScreen(coordinates.x, coordinates.y, coordinates.z);
	}


	/**
	 * Gives the board coordinates given the screen coordinates (u,v).
	 * NOTE: The origin is (0,0)
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
	 * @param theta The rotation angle applied to the board
	 * @return The screen coordinates
	 */
	private double[] _screenToBoard(final double u, final double v, final double theta,
			final double su, final double sv) {
		return _screenToBoard(u / su, v / sv, theta);
	}

	/**
	 * Gives the board coordinates given the screen coordinates, the rotation angle,
	 * the scale factors and the screen origin.
	 * @return The board coordinates
	 */
	public double[] screenToBoard(final double u, final double v) {
		return _screenToBoard(u - origin.x, v - origin.y, angle, scale.x, scale.y);
	}

	/**
	 * Gives the exact board coordinates given the screen coordinates.
	 * @return The screen coordinates
	 */
	public Coordinates screenToSquare(final double u, final double v) {
		final double[] p = screenToBoard(u, v);
		return nearestSquare(p[0], p[1], p[2]);
	}

	/**
	 * Gives the exact board coordinates given the screen coordinates.
	 * @return The screen coordinates
	 */
	public Coordinates screenToSquare(final Point2D point) {
		return screenToSquare(point.getX(), point.getY());
	}

	/**
	 * Returns the coordinates of the square in which (x,y,z) is located.
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

	/**
	 * Creates an hexagon.
	 * @return the hexagon
	 */
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
