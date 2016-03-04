package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.BoardZone;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Square;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * Provides methods to draw and interact with a board.
 */
@SuppressWarnings("serial")
public class BoardView {

	private final AbstractBoard board;
	private Layout layout;
	private final Map<Player, PlayerView> playerViews;

	/** Describes the orientation of the board. */
	public static class Orientation {
		private final double f0;
		private final double f1;
		private final double f2;
		private final double f3;
		private final double b0;
		private final double b1;
		private final double b2;
		private final double b3;
		private final double startAngle; // in multiples of 60°

		/** Default orientation with a corner at the top. */
		public static final Orientation POINTY = new Orientation(
			Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0,
			Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0,
			0.5);
		/** Orientation with a side at the top. */
		public static final Orientation FLAT = new Orientation(
			3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0),
			2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0,
			0.0);

		/**
		 * Constructs a new Orientation.
		 * @param f0 a coefficient of the 2×2 forward matrix
		 * @param f1 a coefficient of the 2×2 forward matrix
		 * @param f2 a coefficient of the 2×2 forward matrix
		 * @param f3 a coefficient of the 2×2 forward matrix
		 * @param b0 a coefficient of the 2x2 inverse matrix
		 * @param b1 a coefficient of the 2x2 inverse matrix
		 * @param b2 a coefficient of the 2x2 inverse matrix
		 * @param b3 a coefficient of the 2x2 inverse matrix
		 * @param startAngle the starting angle
		 */
		public Orientation(final double f0, final double f1, final double f2, final double f3,
				final double b0, final double b1, final double b2, final double b3,
				final double startAngle) {
			this.f0 = f0;
			this.f1 = f1;
			this.f2 = f2;
			this.f3 = f3;
			this.b0 = b0;
			this.b1 = b1;
			this.b2 = b2;
			this.b3 = b3;
			this.startAngle = startAngle;
		}
	}

	/** Board layout. */
	public static class Layout {
		private final Orientation orientation;
		private final Point2D.Double size;
		private final Point2D.Double origin;

		/**
		 * Constructs a new Layout.
		 * @param orientation board orientation
		 * @param size size of an hexagon
		 * @param origin origin of the board
		 */
		public Layout(final Orientation orientation, final Point2D.Double size,
				final Point2D.Double origin) {
			this.orientation = orientation;
			this.size = size;
			this.origin = origin;
		}
	}

	/**
	 * Constructs a new BoardView.
	 * @param board a board to display
	 * @param width available width
	 * @param height available height
	 */
	public BoardView(final AbstractBoard board, final Map<Player, PlayerView> playerViews,
			final int width, final int height) {
		this.board = board;
		this.playerViews = playerViews;

		final double optimalSizeWidth =
			(double) width / (Math.sqrt(3.0) * ((double) board.getWidth() + 0.5));
		final double optimalSizeHeight =
			((double) height  * 2.0) / (3.0 * (double) board.getHeight() + 1);
		final double size = Math.min(optimalSizeWidth, optimalSizeHeight);

		this.layout = new Layout(Orientation.POINTY,
			new Point2D.Double(size, size),
			new Point2D.Double((double) width / 2.0, (double) height / 2.0));
	}

	/**
	 * Gets current layout.
	 * @return current layout
	 */
	public Layout getLayout() {
		return layout;
	}

	/**
	 * Sets a new layout.
	 * @param layout a new layout
	 */
	public void setLayout(final Layout layout) {
		this.layout = layout;
	}

	/**
	 * Finds the center of an hexagon given its coordinates and a layout.
	 * @param layout a layout
	 * @param h coordinates of an hexagon
	 * @return center of this hexagon
	 */
	private static Point2D.Double hexagonCenter(final Layout layout, final Coordinates h) {
		final Orientation m = layout.orientation;
		final double x = (m.f0 * h.x + m.f1 * h.y) * layout.size.x;
		final double y = (m.f2 * h.x + m.f3 * h.y) * layout.size.y;
		return new Point2D.Double(x + layout.origin.x, y + layout.origin.y);
	}

	/**
	 * Finds the corners of and hexagon given a layout.
	 * @param layout a layout
	 * @param corner i-th corner
	 * @return location of the corner
	 */
	private static Point2D.Double hexagonCornerOffset(final Layout layout, final int corner) {
		final Point2D.Double size = layout.size;
		final double angle = 2.0 * Math.PI * (corner + layout.orientation.startAngle) / 6.0;
		return new Point2D.Double(size.x * Math.cos(angle), size.y * Math.sin(angle));
	}

	/**
	 * Constructs the Shape of an hexagon given its coordinates and a layout.
	 * @param layout a layout
	 * @param h coordinates of an hexagon
	 * @return the shape of this hexagon
	 */
	private static Path2D.Double hexagon(final Layout layout, final Coordinates h) {
		final Path2D.Double path = new Path2D.Double();
		final Point2D.Double center = hexagonCenter(layout, h);
		Point2D.Double offset = hexagonCornerOffset(layout, 0);
		Point2D.Double point = new Point2D.Double(center.x + offset.x, center.y + offset.y);
		path.moveTo(point.x, point.y);
		for (int i = 1; i < 6; i++) {
			offset = hexagonCornerOffset(layout, i);
			point = new Point2D.Double(center.x + offset.x, center.y + offset.y);
			path.lineTo(point.x, point.y);
		}
		path.closePath();
		return path;
	}

	/**
	 * Paint this BoardView.
	 * @param g the Graphics context in which to paint
	 */
	public void paint(final Graphics2D g, final Point mouse) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		final Stroke defaultStroke = g.getStroke();
		final Color defaultColor = g.getColor();

		Shape hexagon;
		Square square;
		Color color;
		for (final Coordinates coord : board.coordinates()) {
			hexagon = hexagon(layout, coord);
			square = board.getSquare(coord);
			if (square.isFree()) {
				g.setColor(Color.WHITE);
				g.fill(hexagon);
			} else {
				color = playerViews.get(square.getPawn().getOwner()).getColor();
				g.setColor(color);
				g.fill(hexagon);
			}
			g.setColor(Color.BLACK);
			g.draw(hexagon);
		}

		final Stroke hoveredStroke = new BasicStroke(4.0f);
		final Coordinates hovered = hoveredSquare(mouse);
		boolean hasHovered = false;

		BasicStroke stroke = new BasicStroke(2.0f);
		g.setStroke(stroke);
		for (final Map.Entry<Player, PlayerView> entry : playerViews.entrySet()) {
			color = entry.getValue().getColor();
			g.setColor(color.darker());
			for (final BoardZone zone : entry.getKey().getInitialZones()) {
				for (final Coordinates coord : zone.coordinates()) {
					hexagon = hexagon(layout, coord);
					if (coord.equals(hovered)) {
						g.setStroke(hoveredStroke);
						g.draw(hexagon);
						g.setStroke(stroke);
						hasHovered = true;
					} else {
						g.draw(hexagon);
					}
				}
			}
		}
		if (!hasHovered && hovered != null) {
			hexagon = hexagon(layout, hovered);
			g.setColor(Color.BLACK);
			g.setStroke(stroke);
			g.draw(hexagon);
		}
		g.setStroke(defaultStroke);

		Font defaultFont = g.getFont();
		Font newFont = new Font(defaultFont.getName(), defaultFont.getStyle(), 8);
		g.setFont(newFont);
		g.setColor(Color.BLACK);
		for (final Coordinates coord : board.coordinates()) {
			hexagon = hexagon(layout, coord);
			square = board.getSquare(coord);
			Rectangle2D rect1 = hexagon.getBounds2D();
			Rectangle2D.Double rect = (Rectangle2D.Double) rect1;
			g.drawString(coord.toString(), (int) (rect.x), (int) (rect.y + rect.height / 2));
		}
		g.setFont(defaultFont);

		g.setStroke(defaultStroke);
		g.setColor(defaultColor);
	}

	public Coordinates hoveredSquare(final Point p) {
		final Orientation m = layout.orientation;
		Point2D.Double pt = new Point2D.Double((p.x - layout.origin.x) / layout.size.x,
			(p.y - layout.origin.y) / layout.size.y);

		final double qq = m.b0 * pt.x + m.b1 * pt.y;
		final double rr = m.b2 * pt.x + m.b3 * pt.y;
		final double ss = -qq - rr;

		int q = (int) Math.round(qq);
		int r = (int) Math.round(rr);
		int s = (int) Math.round(ss);

		double q_diff = Math.abs(q - qq);
		double r_diff = Math.abs(r - rr);
		double s_diff = Math.abs(s - ss);
		if (q_diff > r_diff && q_diff > s_diff) {
			q = -r - s;
		} else if (r_diff > s_diff) {
			r = -q - s;
		} else {
			s = -q - r;
		}

		Coordinates result = new Coordinates(q, r, s);
		return board.coordinates().contains(result) ? result : null;
	}
}
