package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Provides methods to draw and interact with a board.
 */
@SuppressWarnings("serial")
public class BoardView {

	/**
	 * Provides methods to draw and interact with a square.
	 */
	private static class SquareView extends Polygon {

		private final Coordinates coordinates;

		/**
		 * Constructs a new SquareView.
		 * @param coordinates at coordinates
		 */
		public SquareView(final Coordinates coordinates) {
			super();
			this.coordinates = coordinates;
		}

		/**
		 * Constructs a new SquareView.
		 * @param squareView copy from this squareView
		 */
		public SquareView(final SquareView squareView) {
			super(squareView.xpoints, squareView.ypoints, squareView.npoints);
			coordinates = squareView.coordinates;
		}

		/**
		 * Gets this SquareView coordinates.
		 * @return this coordinates
		 */
		public Coordinates getCoordinates() {
			return coordinates;
		}
	}

	private final AbstractBoard board;
	private final SquareView[][] squares;

	/**
	 * Constructs a new BoardView.
	 * @param board a board to display
	 * @param width available width
	 * @param height available height
	 */
	public BoardView(final AbstractBoard board, final int width, final int height) {
		this.board = board;
		this.squares = new SquareView[board.getWidth()][board.getHeight()];

		final double diameter = Math.min((double) width / (double) board.getWidth(),
			(double) height / (double) board.getHeight());
		final double radius = diameter / 2.0;

		final Point center = new Point((int) radius, (int) radius);
		final SquareView topLeftSquare = new SquareView(new Coordinates(0, 0));
		squares[0][0] = topLeftSquare;
		for (double i = 1; i <= 6.0; i++) {
			topLeftSquare.addPoint((int) (center.getX() + radius * (Math.cos(i * Math.PI / 3.0 + Math.PI / 6.0))),
				(int) (center.getY() + radius * (Math.sin(i * Math.PI / 3.0 + Math.PI / 6.0))));
		}

		SquareView square;
		for (int y = 0; y < board.getHeight(); y++) {
			for (int x = 0; x < board.getWidth(); x++) {
				if (x == 0 && y == 0) {
					continue;
				}
				square = new SquareView(topLeftSquare);
				if (y % 2 == 0) {
					square.translate(x * (int) diameter, y * (int) (diameter + radius));
				} else {
					square.translate(x * (int) diameter + (int) radius, y * (int) (diameter + radius));
				}
				squares[x][y] = square;
			}
		}
	}

	/**
	 * Paint this BoardView.
	 * @param g the Graphics context in which to paint
	 */
	public void paint(Graphics2D g) {
		final Color color = g.getColor();
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				g.setColor(Color.BLACK);
				g.drawPolygon(squares[x][y]);
				g.setColor(Color.LIGHT_GRAY);
				g.fillPolygon(squares[x][y]);
			}
		}
		g.setColor(color);
	}
}
