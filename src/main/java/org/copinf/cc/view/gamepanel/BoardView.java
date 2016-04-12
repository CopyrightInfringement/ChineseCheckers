package org.copinf.cc.view.gamepanel;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.BoardZone;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Square;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Provides methods to draw and interact with a board.
 */
public class BoardView {

	private final AbstractBoard board;
	private final Map<Player, PlayerView> playerViews;
	private final int width;
	private final int height;

	private final DisplayManager displayManager;

	/**
	 * Constructs a new BoardView.
	 * @param board a board to display
	 * @param player the main player
	 * @param playerViews PlayerView of each Player
	 * @param width available width
	 * @param height available height
	 */
	public BoardView(final AbstractBoard board, final Player player,
			final Map<Player, PlayerView> playerViews,
			final int width, final int height) {
		this.board = board;
		this.playerViews = playerViews;

		this.width = width;
		this.height = height;

		final double optimalSizeWidth =
				(double) (width * 80 / 100) / (Math.sqrt(3.0) * ((double) board.getWidth() + 0.5));
		final double optimalSizeHeight =
				((double) (height * 80 / 100) * 2.0) / (3.0 * (double) board.getHeight() + 1.0);
		final double size = Math.min(optimalSizeWidth, optimalSizeHeight);

		this.displayManager = new DisplayManager(size, 1.0, 1.0, 0.0, width / 2.0, height / 2.0, board);
		
		this.displayManager.setAngle(getPlayerAngle(player, width / 2, 0));
	}
	
	public DisplayManager getDisplayManager() {
		return displayManager;
	}

	private Point2D.Double getBoardZoneCenter(final BoardZone zone) {
		double x = 0;
		double y = 0;
		for (final Coordinates c : zone.coordinates()) {
			final Point2D.Double p = displayManager.squareToScreen(c);
			x += p.x;
			y += p.y;
		}
		x /= (double) zone.coordinates().size();
		y /= (double) zone.coordinates().size();
		return new Point2D.Double(x,y);
	}

	private Point2D.Double getBoardZonesCenter(final Collection<BoardZone> zones) {
		double x = 0;
		double y = 0;
		for (final BoardZone z : zones) {
			final Point2D.Double p = getBoardZoneCenter(z);
			x += p.x;
			y += p.y;
		}
		x /= (double) zones.size();
		y /= (double) zones.size();
		return new Point2D.Double(x,y);
	}

	private double getPlayerAngle(final Player player, final double x, final double y) {
		final Point2D.Double O = displayManager.getOrigin();
		final Point2D.Double P = getBoardZonesCenter(player.getInitialZones());

		final Point2D.Double OPn = new Point2D.Double(
			(O.getX() - P.getX()) / O.distance(P), (O.getY() - P.getY()) / O.distance(P));
		final Point2D.Double OCn = new Point2D.Double(
			(O.getX() - x) / O.distance(x, y), (O.getY() - y) / O.distance(x, y));

		final double dot = OPn.x * OCn.x + OPn.y * OCn.y;
		final double det = OPn.x * OCn.y - OPn.y * OCn.x;

		final double angle = Math.acos(dot) * (det < 0.0 ? -1 : 1);
		return (displayManager.getAngle() + angle) % (2 * Math.PI);
	}

	/**
	 * Paint this BoardView.
	 * @param g the Graphics context in which to paint
	 * @param mouse the screen coordinates of the mouse pointer
	 */
	public void paint(final Graphics2D g, final Point mouse) {
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		drawHexagons(g);
		
		drawFilledHexagons(g, mouse);

		drawNames(g);
	}
	
	private void drawHexagons(final Graphics2D g){
		Shape hexagon;
		Square square;
		Color color;

		final Stroke defaultStroke = g.getStroke();
		final Color defaultColor = g.getColor();
		
		for (final Coordinates coord : board.coordinates()) {
			hexagon = displayManager.hexagon(coord);
			square = board.getSquare(coord);
			g.setColor(Color.WHITE);
			g.fill(hexagon);

			if(!square.isFree()){
				Player owner = square.getPawn().getOwner();
				if(playerViews.containsKey(owner)){
					color = playerViews.get(owner).color;
					
					g.setColor(color);
					g.fill(hexagon);
				}
			}
			g.setColor(Color.BLACK);
			g.draw(hexagon);
		}
		
		g.setStroke(defaultStroke);
		g.setColor(defaultColor);
	}
	
	private void drawFilledHexagons(final Graphics2D g, final Point mouse){
		final Stroke hoveredStroke = new BasicStroke(4.0f);
		final Coordinates hovered = displayManager.screenToSquare(mouse.getX(), mouse.getY());
		boolean hasHovered = false;

		final Stroke defaultStroke = g.getStroke();
		final Color defaultColor = g.getColor();
		
		Color color;
		Shape hexagon;
		final BasicStroke stroke = new BasicStroke(2.0f);
		g.setStroke(stroke);
		for (final Map.Entry<Player, PlayerView> entry : playerViews.entrySet()) {
			color = entry.getValue().color;
			g.setColor(color.darker());
			for (final BoardZone zone : entry.getKey().getInitialZones()) {
				for (final Coordinates coord : zone.coordinates()) {
					hexagon = displayManager.hexagon(coord);
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
			hexagon = displayManager.hexagon(hovered);
			g.setColor(Color.BLACK);
			g.setStroke(stroke);
			g.draw(hexagon);
		}
		
		g.setStroke(defaultStroke);
		g.setColor(defaultColor);
	}
	
	private void drawNames(Graphics2D g){
		final Stroke defaultStroke = g.getStroke();
		final Color defaultColor = g.getColor();
		final Font defaultFont = g.getFont();
		final Font newFont = new Font(defaultFont.getName(), Font.BOLD, 22);
		g.setFont(newFont);
		g.setColor(Color.BLACK);

		for(PlayerView view : playerViews.values()){
			Point2D.Double center = getBoardZonesCenter(view.player.getInitialZones());
			double x = center.x - width / 2;
			double y = center.y - height / 2;
			
			g.setColor(view.color);
			String name = view.player.getName();
			int sW =g.getFontMetrics().stringWidth(name);
			int sH =g.getFontMetrics().getHeight();
			Point2D.Double p = calculateNamePosition(x, y, width, height, sW, sH);
			g.drawString(name, (int) p.x, (int) p.y);
		}

		g.setFont(defaultFont);		
		g.setStroke(defaultStroke);
		g.setColor(defaultColor);
	}
	
	private Point2D.Double calculateNamePosition(double x, double y, double width, double height, double sW, double sH){
		Point2D.Double p = calculateNamePositionAux(x, y, width, height, sW, sH);
		return new Point2D.Double(Math.max(0, p.x + width/2 - sW / 2), p.y + height / 2);
	}
	
	private Point2D.Double calculateNamePositionAux(double x, double y, double width, double height, double sW, double sH){
		if(x == 0){
			return new Point2D.Double(0, y > 0 ? height / 2 - sH : -height / 2 + sH);
		}else if (x > 0){
			double tanB = ((double) height) / ((double) width);
			if(-tanB < y / x && y / x < tanB)
				return new Point2D.Double(width / 2 - sW, y);
			else
				return new Point2D.Double(x, y > 0 ? height / 2 - sH : -height / 2 + sH);
		}else{
			Point2D.Double p = calculateNamePosition(y, -x, width, height, sW, sH);
			return new Point2D.Double(-p.x, p.y);
		}
	}
}
