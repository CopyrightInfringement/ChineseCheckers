package org.copinf.cc.controller;

import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Player;
import org.copinf.cc.view.gamepanel.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

import javax.swing.JPanel;

/**
 * Controls the game state.
 */
public class GameController extends AbstractController implements ActionListener, MouseListener {

	private final static Logger LOGGER = Logger.getLogger(GameController.class.getName());

	private final Game game;
	private Movement currentMovement;
	private final GamePanel gamePanel;

	/**
	 * Constructs a new GameController.
	 * @param game the current game
	 * @param player the playing player
	 * @param window the window to display the game on
	 */
	public GameController(final MainController mainController, final Game game, final Player player) {
		super(mainController);
		this.game = game;
		this.gamePanel = new GamePanel(game, player);

		gamePanel.addMouseListener(this);
		gamePanel.getDrawZone().addMouseListener(this);
		gamePanel.getActionZone().addMouseListener(this);
		gamePanel.getActionZone().getNextButton().addMouseListener(this);
		gamePanel.getActionZone().getResetButton().addMouseListener(this);

		this.currentMovement = new Movement();
	}

	@Override
	public JPanel start() {
		return gamePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LOGGER.entering("GameController", "actionPerformed");
		LOGGER.exiting("GameController", "actionPerformed");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		LOGGER.entering("GameController", "mouseClicked");

		if (e.getSource().equals(gamePanel)) {
			LOGGER.info("gamePanel");

		} else if (e.getSource().equals(gamePanel.getDrawZone())) {
			LOGGER.info("drawZone");

			Coordinates hovered = gamePanel.getDrawZone().getBoardView().hoveredSquare(e.getPoint());
			LOGGER.info("clicked " + hovered);
			if (hovered != null) {
				if (currentMovement.size() == 1) {
					Movement mvt = new Movement(hovered);
					if (game.getBoard().checkMove(mvt, game.getCurrentPlayer())) {
						currentMovement = mvt;
					}
				} else if (currentMovement.size() > 1) {
					game.getBoard().move(currentMovement.getReversedCondensed());
				}
				currentMovement.push(hovered);
				if (game.getBoard().checkMove(currentMovement, game.getCurrentPlayer())) {
					game.getBoard().move(currentMovement);
					gamePanel.getDrawZone().repaint();
				} else {
					currentMovement.pop();
					if (currentMovement.size() > 1) {
						game.getBoard().move(currentMovement);
					}
				}
			}
			LOGGER.info(currentMovement.toString());

		} else if (e.getSource().equals(gamePanel.getActionZone().getNextButton())) {
			LOGGER.info("nextButton");
			if (currentMovement.isEmpty() || currentMovement.size() == 1) {
				LOGGER.info("No movement");
			} else {

				currentMovement = new Movement();
				game.nextTurn();
			}

		} else if (e.getSource().equals(gamePanel.getActionZone().getResetButton())) {
			LOGGER.info("resetButton");
			if (currentMovement.isEmpty()) {
				LOGGER.info("No movement");
			} else {

				game.getBoard().move(currentMovement.getReversedCondensed());
				currentMovement = new Movement();
				gamePanel.repaint();
			}
		}

		LOGGER.exiting("GameController", "mouseClicked");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		LOGGER.entering("GameController", "mouseEntered");
		LOGGER.exiting("GameController", "mouseEntered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		LOGGER.entering("GameController", "mouseExited");
		LOGGER.exiting("GameController", "mouseExited");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		LOGGER.entering("GameController", "mousePressed");
		LOGGER.exiting("GameController", "mousePressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		LOGGER.entering("GameController", "mouseReleased");
		LOGGER.exiting("GameController", "mouseReleased");
	}
}
