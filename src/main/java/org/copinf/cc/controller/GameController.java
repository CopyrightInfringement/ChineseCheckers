package org.copinf.cc.controller;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Pawn;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Team;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Message;
import org.copinf.cc.net.Request;
import org.copinf.cc.view.gamepanel.ActionZone;
import org.copinf.cc.view.gamepanel.DisplayManager;
import org.copinf.cc.view.gamepanel.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Controls the game state.
 */
public class GameController extends AbstractController implements ActionListener, MouseListener, KeyListener {

	/**
	 * Error messages enumeration.
	 */
	private enum ErrorMsg {
		WRONG_MOVE("You can't move this way !"), WRONG_PLAYER("This isn't your pawn !"), NOT_LEGAL(
				"This is not a legal movement !"), SERVER_REFUSED("The server refused your movement !");

		private final String msg;

		ErrorMsg(final String msg) {
			this.msg = msg;
		}

		public String getMessage() {
			return msg;
		}
	}

	private static final int MAX_MESSAGE_LENGTH = 50;

	private final Game game;
	private final Movement currentMovement;
	private final GamePanel gamePanel;
	private final DisplayManager displayManager;
	private final Player mainPlayer;
	private final Map<String, Player> players;

	private boolean waitingForAnswer;

	/**
	 * Constructs a new GameController.
	 * @param mainController the main controller
	 * @param gameInfo the current game
	 * @param mainPlayerName the main player name
	 * @param teamList the list of teams and players usernames
	 */
	public GameController(final MainController mainController, final GameInfo gameInfo, final String mainPlayerName,
			final List<List<String>> teamList) {
		super(mainController, "game");

		this.game = new Game(new DefaultBoard(gameInfo.getSize()));
		this.players = new HashMap<String, Player>();

		// players is never used
		for (final List<String> teamMates : teamList) {
			final Team team = new Team();
			for (final String username : teamMates) {
				final Player player = new Player(username);
				team.addPlayer(player);
				players.put(username, player);
			}
			this.game.addTeam(team);
		}
		this.mainPlayer = players.get(mainPlayerName);

		this.game.setNumberOfZones(gameInfo.getNbZones());

		game.nextTurn();

		this.currentMovement = new Movement();

		this.gamePanel = new GamePanel(game, this.mainPlayer, this.currentMovement);
		this.displayManager = gamePanel.getDrawZone().getBoardView().getDisplayManager();

		this.waitingForAnswer = false;

		gamePanel.addMouseListener(this);
		gamePanel.getDrawZone().addMouseListener(this);
		gamePanel.getActionZone().addMouseListener(this);
		gamePanel.getActionZone().getNextButton().addMouseListener(this);
		gamePanel.getActionZone().getResetButton().addMouseListener(this);
		gamePanel.getActionZone().getChatField().addKeyListener(this);

		gamePanel.getInfoBar().updateLabels();
		setButtonsVisibility();
	}

	/**
	 * Method to call when a turn is over.
	 */
	private void onNextTurn() {
		gamePanel.getDrawZone().setSelectedSquare(null);
		if (!currentMovement.isEmpty()) {
			game.getBoard().move(currentMovement.getReversedCondensed());
			currentMovement.clear();
			gamePanel.repaint();
		}
		gamePanel.getActionZone().getNextButton().setText("Next turn");
		game.nextTurn();
		gamePanel.getInfoBar().updateLabels();
		setButtonsVisibility();
		gamePanel.getDrawZone().getBoardView().updateMovement();
	}

	/**
	 * Method to call to set the correct visibility of the GamePanel buttons.
	 */
	private void setButtonsVisibility() {
		gamePanel.getActionZone().setVisibility(currentMovement.size(), game.getCurrentPlayer() == mainPlayer);
	}

	@Override
	public JPanel getContentPane() {
		return gamePanel;
	}

	@Override
	public void actionPerformed(final ActionEvent ev) {
	}

	@Override
	public void mouseClicked(final MouseEvent ev) {
		final ActionZone az = gamePanel.getActionZone();
		if (ev.getSource() == az.getNextButton()) {
			nextButtonClicked();
		} else if (ev.getSource() == az.getResetButton()) {
			resetButtonClicked();
		} else {
			final Coordinates coordinates = displayManager.screenToSquare(ev.getX(), ev.getY());
			if (coordinates != null) {
				squareClicked(coordinates);
			}
		}
	}

	/**
	 * Method to call when a square is clicked.
	 * @param coordinates The coordinates of the clicked square.
	 */
	private void squareClicked(final Coordinates coordinates) {
		if (game.getCurrentPlayer() != mainPlayer || waitingForAnswer) {
			return;
		}
		final AbstractBoard board = game.getBoard();
		final Pawn pawn = board.getPawn(coordinates);
		final ErrorMsg errorMsg;

		// Si on définit quel pion déplacer
		if (currentMovement.size() == 0) {
			if (pawn == null || pawn.getOwner() != mainPlayer) {
				errorMsg = ErrorMsg.WRONG_MOVE;
			} else {
				currentMovement.push(coordinates);
				errorMsg = null;
			}
			//	Si on veut redéfinir le pion à déplacer
		} else if (currentMovement.size() == 1 && pawn != null && pawn.getOwner() == mainPlayer) {
			currentMovement.pop();
			currentMovement.push(coordinates);
			errorMsg = null;
			//	Si on veut effectuer un déplacement
		} else {
			board.move(currentMovement.getReversedCondensed());
			currentMovement.push(coordinates);
			if (!board.checkMove(currentMovement, mainPlayer)) {
				currentMovement.pop();
				errorMsg = ErrorMsg.WRONG_MOVE;
			} else {
				errorMsg = null;
			}
		}

		if (errorMsg != null) {
			gamePanel.getDrawZone().addMessage(errorMsg.getMessage());
		}

		gamePanel.getActionZone().getResetButton().setEnabled(!currentMovement.isEmpty());
		movePawn(currentMovement);

		gamePanel.getDrawZone().setSelectedSquare(currentMovement.size() == 0 ? null : currentMovement.lastElement());

		gamePanel.getDrawZone().getBoardView().updateMovement();

		setButtonsVisibility();
	}

	/**
	 * Method to call when the "next" button is clicked.
	 */
	private void nextButtonClicked() {
		if (game.getCurrentPlayer() != mainPlayer || waitingForAnswer || currentMovement.size() < 2) {
			return;
		}
		sendRequest(new Request("client.game.move.request", currentMovement));

		waitingForAnswer = true;
	}

	/**
	 * Method to call when the "reset" button is clicked.
	 */
	private void resetButtonClicked() {
		if (currentMovement.size() >= 2) {
			movePawn(currentMovement.getReversedCondensed());
		}
		resetMovement();
		gamePanel.repaint();
		setButtonsVisibility();
	}

	@Override
	public void processRequest(final Request request) {
		final String sub2 = request.getSubRequest(2);
		if ("next".equals(sub2)) {
			onNextTurn();
			waitingForAnswer = false;
		} else if ("move".equals(sub2)) {
			processMoveRequest(request);
			waitingForAnswer = false;
		} else if ("message".equals(sub2)) {
			gamePanel.getDrawZone().addMessage((Message) request.getContent());
			gamePanel.repaint();
		} else if ("end".equals(sub2)) {
			final int teamId = (Integer) request.getContent();
			if (teamId < 0) {
				JOptionPane.showMessageDialog(null,
						"A player has left the game" + (game.getTurnCount() < 0 ? "during team-making" : ""),
						"Game over", JOptionPane.ERROR_MESSAGE);
				end();
			} else {
				final Team team = game.getWinner();
				JOptionPane.showMessageDialog(null,
						team.get(0).getName() + (team.size() == 2 ? "'s team" : "") + " has won", "Game over",
						JOptionPane.INFORMATION_MESSAGE);
				end();
			}
		} else if ("tick".equals(sub2)) {
			gamePanel.getActionZone().getNextButton().setText(request.getContent().toString() + "s");
		}
	}

	private void sendMessageAction() {
		final String text = gamePanel.getActionZone().getMessage().trim();
		boolean valid = true;
		if (text.isEmpty()) {
			valid = false;
		}
		if (text.length() > MAX_MESSAGE_LENGTH) {
			gamePanel.getDrawZone().addMessage("Maximum message length is " + MAX_MESSAGE_LENGTH);
			valid = false;
		}
		if (valid) {
			final Message message = new Message(text, mainPlayer.getName());
			sendRequest(new Request("client.game.message", message));
		}
		gamePanel.getActionZone().clearField();
	}

	/**
	 * Process a "client.game.move" request
	 * @param request The request
	 */
	private void processMoveRequest(final Request request) {
		final String sub = request.getSubRequest(3);
		if ("request".equals(sub)) {
			if (!(Boolean) request.getContent()) {
				gamePanel.getDrawZone().addMessage(ErrorMsg.SERVER_REFUSED.msg);
				game.getBoard().move(currentMovement.getReversedCondensed());
			}
			game.getBoard().move(currentMovement.getReversedCondensed());
			resetMovement();
			gamePanel.repaint();
		} else if (sub == null) {
			final Movement movement = (Movement) request.getContent();
			movePawn(movement);
		}
	}

	private void resetMovement() {
		currentMovement.clear();
		gamePanel.getDrawZone().setSelectedSquare(null);
		gamePanel.getDrawZone().getBoardView().updateMovement();
	}

	/**
	 * Moves a pawn then repaint the board.
	 * @param movement a movement
	 */
	private void movePawn(final Movement movement) {
		game.getBoard().move(movement);
		gamePanel.repaint();
	}

	@Override
	public void mouseEntered(final MouseEvent ev) {
	}

	@Override
	public void mouseExited(final MouseEvent ev) {
	}

	@Override
	public void mousePressed(final MouseEvent ev) {
	}

	@Override
	public void mouseReleased(final MouseEvent ev) {
	}

	@Override
	public void keyTyped(final KeyEvent ev) {
		if (ev.getSource() == gamePanel.getActionZone().getChatField() && ev.getKeyChar() == '\n') {
			sendMessageAction();
		}
	}

	@Override
	public void keyPressed(final KeyEvent ev) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(final KeyEvent ev) {
		// TODO Auto-generated method stub
	}
}
