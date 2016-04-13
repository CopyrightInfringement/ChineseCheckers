package org.copinf.cc.controller;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.DefaultBoard;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Pawn;
import org.copinf.cc.model.Player;
import org.copinf.cc.model.Team;
import org.copinf.cc.net.Request;
import org.copinf.cc.net.GameInfo;
import org.copinf.cc.net.Message;
import org.copinf.cc.view.gamepanel.ActionZone;
import org.copinf.cc.view.gamepanel.DisplayManager;
import org.copinf.cc.view.gamepanel.GamePanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.JPanel;

/**
 * Controls the game state.
 */
public class GameController extends AbstractController implements ActionListener, MouseListener {

	/**
	 * Error messages enumeration
	 */
	private enum ErrorMsg {
		WRONG_MOVE("You can't move this way !"),
		WRONG_PLAYER("This isn't your pawn !"),
		NOT_LEGAL("This is not a legal movement !"),
		SERVER_REFUSED("The server refused your movement !");

		private final String msg;

		ErrorMsg(final String msg) {
			this.msg = msg;
		}

		public String getMessage() {
			return msg;
		}
	}

	private static final Logger LOGGER = Logger.getLogger(GameController.class.getName());

	private final Game game;
	private final GameInfo gameInfo;
	private final Movement currentMovement;
	private final GamePanel gamePanel;
	private final DisplayManager displayManager;
	private final Player mainPlayer;
	private static final Pattern MESSAGE_PATTERN = Pattern.compile("^[(.+)](.+)$");
	private final Map<String, Player> players;
	
	private boolean waitingForAnswer;

	/**
	 * Constructs a new GameController.
	 * @param mainController the main controller
	 * @param gameInfo the current game
	 * @param mainPlayer the main player
	 */
	public GameController(final MainController mainController, final GameInfo gameInfo,
			final String mainPlayerName, final List<List<String>> teamList) {
		super(mainController, "game");

		this.gameInfo = gameInfo;
		this.game = new Game(new DefaultBoard(gameInfo.size));
		this.players = new HashMap<String, Player>();
		
		for(List<String> teamMates : teamList){
			Team team = new Team();
			for(String username : teamMates){
				Player player = new Player(username);
				team.addPlayer(player);
				players.put(username, player);
			}
			this.game.addTeam(team);
		}

		this.game.setNumberOfZones(gameInfo.nbZones);
		
		this.mainPlayer = players.get(mainPlayerName);
		
		game.nextTurn();
		
		this.gamePanel = new GamePanel(game, this.mainPlayer);
		this.displayManager = gamePanel.getDrawZone().getBoardView().getDisplayManager();

		this.waitingForAnswer = false;
		
		gamePanel.addMouseListener(this);
		gamePanel.getDrawZone().addMouseListener(this);
		gamePanel.getActionZone().addMouseListener(this);
		gamePanel.getActionZone().getNextButton().addMouseListener(this);
		gamePanel.getActionZone().getResetButton().addMouseListener(this);
		gamePanel.getActionZone().getSendButton().addMouseListener(this);
		
		setButtonsVisibility();
		
		this.currentMovement = new Movement();
	}

	/**
	 * Method to call when a turn is over
	 */
	private void onNextTurn(){
		game.nextTurn();
		setButtonsVisibility();
	}
	
	/**
	 * Method to call to sets the correct visibility to the GamePanel buttons
	 */
	private void setButtonsVisibility(){
		boolean visibility = mainPlayer == game.getCurrentPlayer();
		gamePanel.getActionZone().getNextButton().setVisible(visibility);
		gamePanel.getActionZone().getResetButton().setVisible(visibility);
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
		ActionZone az = gamePanel.getActionZone();
		if (ev.getSource() == az.getNextButton()) {
			nextButtonClicked();
		} else if (ev.getSource() == az.getResetButton()) {
			resetButtonClicked();
		} else if (ev.getSource() == az.getSendButton()) {
			Message message = new Message(az.getMessage(), mainPlayer.getName());
			sendRequest(new Request("client.game.message", (Serializable) message));
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
		if(currentMovement.size() >= 2)
			board.move(currentMovement.getReversedCondensed());
		currentMovement.push(coordinates);
		if (!board.checkMove(currentMovement, mainPlayer)) {
			final Pawn pawn = board.getPawn(coordinates);
			final ErrorMsg errorMsg;
			if (pawn == null) {
				errorMsg = ErrorMsg.WRONG_MOVE;
			} else if (pawn.owner != mainPlayer) {
				errorMsg = ErrorMsg.WRONG_PLAYER;
			} else {
				errorMsg = ErrorMsg.NOT_LEGAL;
			}
			gamePanel.getDrawZone().addMessage(errorMsg.getMessage());
			currentMovement.pop();
		}

		
		movePawn(currentMovement);
	}

	/**
	 * Method to call when the "next" button is clicked.
	 */
	private void nextButtonClicked() {
		if (game.getCurrentPlayer() != mainPlayer || waitingForAnswer) {
			return;
		}
		sendRequest(new Request("client.game.move.request", currentMovement));
		waitingForAnswer = true;
	}

	/**
	 * Method to call when the "reset" button is clicked.
	 */
	private void resetButtonClicked() {
		if(currentMovement.size() >= 2)
			movePawn(currentMovement.getReversedCondensed());
		currentMovement.clear();
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
		}else if("end".equals(sub2)){
			int teamID = (Integer) request.getContent();
			if(teamID < 0){
				javax.swing.JOptionPane.showMessageDialog(null, "A player has left the game", "Game over", javax.swing.JOptionPane.ERROR_MESSAGE);
				finish();
			}else{
				Team team = game.getWinner();
				javax.swing.JOptionPane.showMessageDialog(null, team.get(0).getName() + (team.size() == 2 ? "'s team" : "") + " has won" , "Game over", javax.swing.JOptionPane.INFORMATION_MESSAGE);
				finish();
			}
		}
	}

	/**
	 * Process a "client.game.move" request
	 * @param request The request
	 */
	private void processMoveRequest(final Request request) {
		String sub = request.getSubRequest(3);
		if(currentMovement.size() >= 2)
			movePawn(currentMovement.getReversedCondensed());
		currentMovement.clear();
		if("request".equals(sub)) {
			if(!(Boolean) request.getContent())
				gamePanel.getDrawZone().addMessage(ErrorMsg.SERVER_REFUSED.msg);
		}else if(sub == null) {
			Movement movement = (Movement) request.getContent();
			movePawn(movement);
		}
	}

	/**
	 * Moves a pawn then repaint the board
	 * @param movement
	 */
	private void movePawn(Movement movement){
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
}
