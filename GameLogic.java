import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * @author Daniel Zateikin
 * this class represents and controls the logic of the game. it holds a JFrame object and a JPanel
 * Object and is itself a key listener and an Action Listener. 
 * it holds all the game objects and controls them.
 *
 */

public class GameLogic implements ActionListener, KeyListener {
	
	public static final String PARACHUT_IMG = "images\\parachuteS.png";
	public static final String BOAT_IMG = "images\\bboatS.gif";
	public static final String PLANE_IMG = "images\\planeS.png";
	public static final String BACKGROUND_IMG = "images\\seaS.jpg";
	public static final String GAME_OVER_IMG = "images\\game_over_s.png";
	public static final String FRAME_TITLE = "Catch The Parachutist";
	public static final String LIVES_TITLE = "Lives: ";
	public static final String SCORE_TITLE = "Score: ";

	public static final int ACTION_DELAY_MILLI_SECONDS = 3;
	public static final int PLANE_SPEED = 1;
	public static final int PLANE_Y_LOCATION = 20;
	public static final int BOAT_SPEED = 3;
	public static final int PARACHUT_SPEED = 3;
	public static final int DISTANCE_IN_WATER = 200;
	public static final int INITIAL_LIVES = 3;
	public static final int SCORE_INCREASE = 10;
	public static final int FRAME_WIDTH = 830;
	public static final int FRAME_HEIGHT = 500;
	public static final int MAX_SCORE = 300;
	private static final int COLLISION_OVERLAP_THRESHOLD = 500;
	
	private GameUI gui;
	private Timer tm;
	private int dropX;
	private int lives;
	private JLabel livesLabel;
	private JLabel restartGame;
	private int score;
	private JLabel scoreLabel;
	private Plane _plane;
	private Boat _boat;
	private Parachut _parachut;
	private Image background;
	private JFrame jf;
	private DrawableObject gameOverDrawable;
	private boolean isGameOver;
	
	public GameLogic(){
		
		gameOverDrawable = new DrawableObject(0, 0, GAME_OVER_IMG);
		gameOverDrawable.setX(FRAME_WIDTH/2 - gameOverDrawable.getWidth()/2);
		gameOverDrawable.setY(FRAME_HEIGHT/2 - gameOverDrawable.getHeight()/2);
		
		livesLabel = new JLabel(LIVES_TITLE + lives);
		scoreLabel = new JLabel(SCORE_TITLE + score);
		restartGame = new JLabel("press 'ENTER' to restart");
		
		background = Toolkit.getDefaultToolkit().createImage(BACKGROUND_IMG);
		setFrameAndGUI();
		
		_plane = new Plane(FRAME_WIDTH, PLANE_Y_LOCATION, PLANE_IMG, -PLANE_SPEED,0);
		_boat = new Boat(FRAME_WIDTH/2, FRAME_HEIGHT - DISTANCE_IN_WATER, BOAT_IMG, FRAME_WIDTH,0, 0);
		_parachut = new Parachut(0, 0, PARACHUT_IMG, 0, PARACHUT_SPEED);
		_parachut.reset(setDropX(),PLANE_Y_LOCATION);
		
		startGame();
		
		tm = new Timer(ACTION_DELAY_MILLI_SECONDS, this);
		
		gui.addDrawable(_plane);
		gui.addDrawable(_boat);
		gui.addDrawable(_parachut);
		gui.addDrawable(gameOverDrawable);
		
	}
	
	/**
	 * this method sets all the necessary fields for the JPanel object and te JFrame Object
	 */
	public void setFrameAndGUI(){
		gui = new GameUI();
		gui.add(scoreLabel);
		gui.add(livesLabel);
		gui.add(restartGame);
		gui.setBackground(background);
		
		jf = new JFrame();
		jf.setTitle(GameLogic.FRAME_TITLE);
		jf.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(gui);
		jf.addKeyListener(this);
		jf.setFocusable(true);
	}
	
	
	@Override
	/**
	 * on left or right key press change the velocity of the boat .
	 */
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_LEFT){
			_boat.setVelX(-BOAT_SPEED);
		}
		if (c == KeyEvent.VK_RIGHT){
			_boat.setVelX(BOAT_SPEED);
		}
		if (c == KeyEvent.VK_ENTER && isGameOver){
			startGame();
		}
		if(c == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}

	@Override
	/**
	 * when the user releases the left and right keys the boat velocity will
	 * return to being zero
	 */
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		if ((c == KeyEvent.VK_LEFT && _boat.getVelX() == -BOAT_SPEED)
				|| (c == KeyEvent.VK_RIGHT && _boat.getVelX() == BOAT_SPEED)){
			_boat.setVelX(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	/**
	 * the action listener function, called every ACTION_DELAY_MILLI_SECONDS.
	 * each time should move the objects of the game, check the status of the parachut and update 
	 * the game status. eventually sending JPanel a request to repaint the scene
	 */
	public void actionPerformed(ActionEvent e) {
		
		if (!isGameOver){
			_plane.move();
			_boat.move();
			_parachut.move();
			checkPlane();
			checkParachut();
		}
		gui.repaint();
	}
	
	/**
	 * this method sets the plain location back to the initial location
	 * once it is off the screen.
	 */
	public void checkPlane(){
		if (_plane.getX() + _plane.getWidth() < 0){
			_plane.setX(FRAME_WIDTH);
		}
	}
	
	/**
	 * when the parachutist is done falling, the game gets updated, a new random
	 * location is being set for the next parachutist drop and the parachutist is being reset.
	 * in case the lives in the game are zero or it has reached max score, the game will exit.
	 */
	public void updateGame(int livesLost, int scoreGain){
		score += scoreGain;
		lives -= livesLost;
		scoreLabel.setText(SCORE_TITLE + score);
		livesLabel.setText(LIVES_TITLE + lives);
		setDropX();
		_parachut.reset(this.dropX, PLANE_Y_LOCATION);
		
		if (lives <= 0){
			gameOver();
		}
		
	}
	/**
	 * a function that checks the status of the parachutist each game moment.
	 * if the parachut is alive, the function check whether it has collided with the boat,
	 * or reached the water, in which case it will call the update game method, if the parachutist
	 * isn't alive, it does nothing unless the plane gets to the dropping point, where
	 * it sets the parachutist as alive.
	 */
	public void checkParachut(){
		if (!_parachut.isVisible() && _plane.getX() > dropX - 5 && _plane.getX() < dropX + 5){
			_parachut.setVisible(true);
		}
		else if (_parachut.isVisible()){
			if (_parachut.getOverlap(_boat) >= COLLISION_OVERLAP_THRESHOLD){
				updateGame(0, SCORE_INCREASE);
			}
			else if (_parachut.getY() >= _boat.getY() + _boat.getHeight()){
				updateGame(1, 0);
			}
		}
	}
	/**
	 * a function that sets a random drop point for the parachutist
	 */
	public int setDropX(){	
		this.dropX = (int)(Math.random() * ((FRAME_WIDTH - _plane.getWidth()) + 1));
		return this.dropX;
	}
	
	
	private void gameOver(){
		this.isGameOver = true;
		gameOverDrawable.setVisible(true);
		restartGame.setVisible(true);
	}
	private void startGame(){
		lives = INITIAL_LIVES;
		score = 0;
		_plane.setVelX(-PLANE_SPEED);
		scoreLabel.setText(SCORE_TITLE + score);
		livesLabel.setText(LIVES_TITLE + lives);
		gameOverDrawable.setVisible(false);
		restartGame.setVisible(false);
		isGameOver = false;
		gui.setBackground(background);
		_plane.setXY(FRAME_WIDTH, PLANE_Y_LOCATION);
		_boat.setXY(FRAME_WIDTH/2, FRAME_HEIGHT - DISTANCE_IN_WATER);
	}
	
	/**
	 * the main function, creates a game instance and starts the event timer.
	 */
	public static void main(String[] args){
		GameLogic game = new GameLogic();
		game.tm.start();
	}
	
	
}
