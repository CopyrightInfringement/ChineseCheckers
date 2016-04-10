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
import org.copinf.cc.view.gamepanel.DisplayManager;
import org.copinf.cc.view.gamepanel.GamePanel;
import org.copinf.cc.view.gamepanel.PlayerView;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;

/**
 * Controls the game state.
 */
public class GameController extends AbstractController implements ActionListener, MouseListener {

	private enum ErrorMsg {
		WRONG_MOVE("You can't move this way !"),
		WRONG_PLAYER("This isn't your pawn !"),
		NOT_LEGAL("This is not a legal movement !");

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
			final Player mainPlayer) {
		super(mainController, "game");

		this.game = new Game(new DefaultBoard(gameInfo.size));
		this.gameInfo = gameInfo;

		this.gamePanel = new GamePanel(game, mainPlayer);
		this.displayManager = gamePanel.getDrawZone().getBoardView().getDisplayManager();
		this.mainPlayer = mainPlayer;
		this.players = new HashMap<String, Player>();
		this.players.put(mainPlayer.getName(), mainPlayer);

		this.waitingForAnswer = false;

		gamePanel.addMouseListener(this);
		gamePanel.getDrawZone().addMouseListener(this);
		gamePanel.getActionZone().addMouseListener(this);
		gamePanel.getActionZone().getNextButton().addMouseListener(this);
		gamePanel.getActionZone().getResetButton().addMouseListener(this);

		this.currentMovement = new Movement();
	}
	
	public GameController(final MainController mainController, final GameInfo gameInfo,
			final Player mainPlayer, final List<List<String>> teams) {
		this(mainController, gameInfo, mainPlayer);
		for(List<String> teamMates : teams){
			Team team = new Team();
			for(String name : teamMates){
				Player player = new Player(name);
				team.addPlayer(player);
			}
			game.addTeam(team);
		}
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
		if (ev.getSource() == gamePanel.getActionZone().getNextButton()) {
			nextButtonClicked();
		} else if (ev.getSource() == gamePanel.getActionZone().getResetButton()) {
			resetButtonClicked();
		} else {
			final Coordinates coordinates = displayManager.screenToSquare(ev.getX(), ev.getY());
			if (coordinates != null) {
				squareClicked(coordinates);
			}
		}
	}

	private void squareClicked(final Coordinates coordinates) {
		if (game.getCurrentPlayer() != mainPlayer || waitingForAnswer) {
			return;
		}
		final AbstractBoard board = game.getBoard();
		board.move(currentMovement.getReversedCondensed());
		currentMovement.push(coordinates);
		if (!board.checkMove(currentMovement, mainPlayer)) {
			final Pawn pawn = board.getPawn(coordinates);
			final ErrorMsg errorMsg;
			if (pawn == null) {
				errorMsg = ErrorMsg.WRONG_MOVE;
			} else if (pawn.getOwner() != mainPlayer) {
				errorMsg = ErrorMsg.WRONG_PLAYER;
			} else {
				errorMsg = ErrorMsg.NOT_LEGAL;
			}
			gamePanel.getDrawZone().addMessage(errorMsg.getMessage(), Color.RED);
			currentMovement.pop();
		}

		board.move(currentMovement);
	}

	private void nextButtonClicked() {
		if (game.getCurrentPlayer() != mainPlayer || waitingForAnswer) {
			return;
		}
		sendRequest(new Request("client.game.move.request", currentMovement));
		waitingForAnswer = true;
	}

	private void resetButtonClicked() {
		currentMovement.clear();
	}

	@Override
	public void processRequest(final Request request) {
		final String sub2 = request.getSubRequest(2);
		if ("players".equals(sub2)) {
			if ("refresh".equals(request.getSubRequest(3))) {
				processPlayersRefresh(request);
			}
		} else if ("next".equals(sub2)) {
			processGameNext();
			waitingForAnswer = false;
		} else if ("move".equals(sub2)) {
			processMoveRequest(request);
			waitingForAnswer = false;
		} else if ("message".equals(sub2)) {
			final Matcher matcher = MESSAGE_PATTERN.matcher((String) request.getContent());
			final String name = matcher.group(1);
			final String message = matcher.group(2);
			final PlayerView pv = gamePanel.getPlayerViews().get(players.get(name));
			gamePanel.getDrawZone().addMessage(message, pv.color);
		}
	}
	
	private void processGameNext(){
		if(!gameInfo.teams){
			for(Player player : players.values()){
				Team team = new Team();
				team.addPlayer(player);
				game.addTeam(team);
			}
		}
		game.setNumberOfZones(gameInfo.nbZones);
		game.nextTurn();
		gamePanel.initPlayerViews();
	}

	@SuppressWarnings("unchecked")
	private void processPlayersRefresh(final Request request) {
		final List<String> playersName = (List<String>) request.getContent();
		for(String name : playersName){
			if(!players.containsKey(name))
				players.put(name, new Player(name));
		}
	}

	private void processMoveRequest(final Request request) {
		//	If it's an answer we were waiting for
		if (request.getSubRequestSize() != 3 && waitingForAnswer) {
			//	If the server has agreed to the move request
			if ((Boolean) request.getContent()) {
				sendRequest(new Request("client.game.next"));
			//	If the server has disagreed
			} else {
				LOGGER.log(Level.ALL, "Server refused movement");
				currentMovement.clear();
			}
		//	If it's a move notification
		} else {
			game.getBoard().move((Movement) request.getContent());
		}
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
