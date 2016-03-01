package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Square;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Provides methods to draw and interact with a board.
 */
@SuppressWarnings("serial")
public class BoardView {

	private final AbstractBoard board;
	private final Shape hexagon;
	private final double size;

	/**
	 * Constructs a new BoardView.
	 * @param board a board to display
	 * @param width available width
	 * @param height available height
	 */
	public BoardView(final AbstractBoard board, final int width, final int height) {
		this.board = board;
		this.size = Math.min((double) width / (double) board.getWidth(),
			(double) height / (double) board.getHeight()) / 2.;

		final Path2D.Double path = new Path2D.Double();
		final Point2D.Double center = new Point2D.Double(size, size);
		Point2D.Double point = hexagonCorner(center, 0);
		path.moveTo(point.x, point.y);
		for (int i = 1; i < 6; i++) {
			point = hexagonCorner(center, i);
			path.lineTo(point.x, point.y);
		}
		path.closePath();
		this.hexagon = path;
	}

	/**
	 * Gets the coordinates of i-nth corner of an hexagon.
	 * @param center center of the hexagon
	 * @param i corner number
	 * @return the coordinates
	 */
	private Point2D.Double hexagonCorner(final Point2D.Double center, final int i) {
		return new Point2D.Double(
		center.x + size * Math.cos(Math.PI * i / 3. + Math.PI / 6.),
		center.y + size * Math.sin(Math.PI * i / 3. + Math.PI / 6.));
	}

	/**
	 * Paint this BoardView.
	 * @param g the Graphics context in which to paint
	 */
	public void paint(final Graphics2D g) {
		double tx;
		double ty;
		Square square;
		Coordinates coord;
		g.setColor(Color.BLACK);
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				coord = new Coordinates(x, y);
				square = board.getSquare(coord);
				if (square != null) {
					tx = (double) x * Math.sqrt(3.) * size + (y % 2 == 0 ? 0. : Math.sqrt(3.) * size / 2.);
					ty = (double) y * size * 3. / 2.;
					g.translate(tx, ty);
					if (!square.isFree()) {
						g.setColor(new Color(square.getPawn().getOwner().hashCode()));
						g.fill(hexagon);
						g.setColor(Color.BLACK);
					}
					g.draw(hexagon);
					g.translate(-tx, -ty);
				}
			}
		}
	}
}
