package org.copinf.cc.controller;

import org.copinf.cc.model.AbstractBoard;
import org.copinf.cc.model.Coordinates;
import org.copinf.cc.model.Game;
import org.copinf.cc.model.Movement;
import org.copinf.cc.model.Pawn;
import org.copinf.cc.model.Player;
import org.copinf.cc.net.Request;
import org.copinf.cc.net.client.Client;
import org.copinf.cc.view.gamepanel.DisplayManager;
import org.copinf.cc.view.gamepanel.GamePanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
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
	private final DisplayManager displayManager;
	private final Player player;
	private Client client;
	private boolean waitingForAnswer;

	/**
	 * Constructs a new GameController.
	 * @param game the current game
	 * @param player the playing player
	 * @param window the window to display the game on
	 */
	public GameController(final MainController mainController, final Game game, final Player player, final Client client) {
		super(mainController, "game");
		this.game = game;
		this.gamePanel = new GamePanel(game, player);
		this.displayManager = gamePanel.getDrawZone().getBoardView().getDisplayManager();
		this.player = player;
		this.client = client;
		this.waitingForAnswer = false;

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
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == gamePanel.getActionZone().getNextButton())
			nextButtonClicked();
		else if(e.getSource() == gamePanel.getActionZone().getResetButton())
			resetButtonClicked();
		else{
			Coordinates coordinates = displayManager.screenToSquare(e.getX(), e.getY());
			if (coordinates != null){
				squareClicked(coordinates);
				return;
			}
		}
	}

	private void squareClicked(Coordinates coordinates){
		if(game.getCurrentPlayer() != player || waitingForAnswer)
			return;
		AbstractBoard board = game.getBoard();
		board.move(currentMovement.getReversedCondensed());
		currentMovement.push(coordinates);
		if (!board.checkMove(currentMovement, player)){
			Pawn pawn = board.getPawn(coordinates);
			String errorMsg;
			if(pawn == null)
				errorMsg = "You can't move this way !";
			else if(pawn.getOwner() != player)
				errorMsg = "This isn't your pawn goddemmit !";
			else
				errorMsg = "This is not a legal movement !";
			gamePanel.getDrawZone().addMessage(errorMsg, Color.RED);
			currentMovement.pop();
		}

		board.move(currentMovement);
	}

	private void nextButtonClicked(){
		if(game.getCurrentPlayer() != player || waitingForAnswer)
			return;
		client.send(new Request("client.game.move.request", currentMovement));
		waitingForAnswer = true;
	}

	private void resetButtonClicked(){
		currentMovement.empty();
	}

	@Override
	public void processRequest(final Request request){
		String requestID = request.getSubRequest(2);
		if (requestID.equals("next"))
			game.nextTurn();
		else if(requestID.equals("move"))
			processMoveRequest(request);
		waitingForAnswer = false;	//	If we were waiting for answer, now we're not !
	}

	private void processMoveRequest(Request request){
		//	If it's an answer we were waiting for
		if (request.getSubRequestSize() != 3 && waitingForAnswer){
			//	If the server has disagreed to the move request
			if (!(Boolean)request.getContent()){
				LOGGER.log(Level.ALL, "Server refused movement");
				currentMovement.empty();
			//	If the server has agreed
			}else
				client.send(new Request("client.game.next"));
		//	If it's a move notification
		}else
			game.getBoard().move((Movement)request.getContent());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
