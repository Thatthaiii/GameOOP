package Gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import utilz.LoadSave;
import Levels.LevelManager;


import entities.EnemyManager;
import entities.Player;
import main.Game;
import ui.GameCompletedOverlay;
import ui.GameOverOverlay;
import ui.LevelCompleteOverLay;
import ui.PauseOverlay;


public class playing extends State implements StateMethod{
	private Player player;
	private int xLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;


	
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private GameCompletedOverlay gameCompletedOverlay;

	private LevelCompleteOverLay levelCompletedOverlay;
	private boolean paused = false;
	



	
	
	private boolean gameOver;
	private boolean levelCompleted;
	private boolean gameCompleted;
	private boolean playerDying;
	

	
	public playing(Game game) {
		super(game);
		initClasses();
		calcLvlOffset();
		loadStartLevel();
		


		
	}


	
	public void loadNextLevel() {
		levelManager.setLevelIndex(levelManager.getLevelIndex() + 1);
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		resetAll();
		
	}

	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
		
	}

	private void calcLvlOffset() {
		maxLvlOffsetX  = levelManager.getCurrentLevel().getLvlOffset();
		
	}
	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);


		player = new Player(200, 200, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), this);
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompleteOverLay(this);
		gameCompletedOverlay = new GameCompletedOverlay(this);


	}


	@Override
	public void update() {
		if (paused) {
			pauseOverlay.update();
		}else if (levelCompleted) {
			levelCompletedOverlay.update();
		}else if (gameOver) {
			gameOverOverlay.update();
		}else if (playerDying) {
			player.update();
		}else {
			levelManager.update();

			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData());
			checkCloseToBorder();

		}
	}


	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
//		drawDialogue(g, xLvlOffset);
		
//		if (drawRain)
//			rain.draw(g, xLvlOffset);
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)
			gameOverOverlay.draw(g);
		else if (levelCompleted)
			levelCompletedOverlay.draw(g);
		else if (gameCompleted)
			gameCompletedOverlay.draw(g);

	}
	
	public void resetAll() {
		//reset everything in game
		gameOver = false;
		paused = false;
		levelCompleted = false;
		playerDying = false;
		

		

		player.resetAll();
		enemyManager.resetAllEnemies();


	}
	public void setGameCompleted() {
		gameCompleted = true;
	}
	public void resetGameCompleted() {
		gameCompleted = false;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
//		if(!gameOver)
//			if (e.getButton() == MouseEvent.BUTTON1)
//				player.setAttacking(true);
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mousePressed(e);
		else if (paused)
			pauseOverlay.mousePressed(e);
		else if (levelCompleted)
			levelCompletedOverlay.mousePressed(e);
		else if (gameCompleted)
			gameCompletedOverlay.mousePressed(e);


	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mouseReleased(e);
		else if (paused)
			pauseOverlay.mouseReleased(e);
		else if (levelCompleted)
			levelCompletedOverlay.mouseReleased(e);
		else if (gameCompleted)
			gameCompletedOverlay.mousePressed(e);

		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mouseMoved(e);
		else if (paused)
			pauseOverlay.mouseMoved(e);
		else if (levelCompleted)
			levelCompletedOverlay.mouseMoved(e);
		else if (gameCompleted)
			gameCompletedOverlay.mousePressed(e);

	}
	
	
	public void mouseDragged(MouseEvent e) {
		if(!gameOver) 
			if(paused)
				pauseOverlay.mouseDragged(e);	
				
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (!gameOver && !gameCompleted && !levelCompleted)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				player.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT:

				player.setRight(true);
				break;
			case KeyEvent.VK_UP:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
			case KeyEvent.VK_SPACE:
				player.setAttacking(true);
				break;
			case KeyEvent.VK_Z:
				player.powerAttack();
				break;
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				player.setJump(false);
				break;
			case KeyEvent.VK_LEFT:
				player.setLeft(false);
				break;
			case KeyEvent.VK_S:
				player.setDown(false);
				break;
			case KeyEvent.VK_RIGHT:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setAttacking(false);
				break;

			}
		
	}


	public void setLevelCompleted(boolean levelCompleted) {
//		game.getAudioPlayer().lvlCompleted();
		if (levelManager.getLevelIndex() + 1 >= levelManager.getAmountOfLevels()) {
			// No more levels
			gameCompleted = true;
			levelManager.setLevelIndex(0);
			levelManager.loadNextLevel();
			resetAll();
			return;
		}
		this.levelCompleted = levelCompleted;
	}

	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	
	public void unpauseGame() {
		paused = false;
	}
	public void WindowFocusLost() {
		player.resetDirBooleans();
	}
	public Player getPlayer() {
		return player;
	}
	public LevelManager getLevelManager() {
		return levelManager;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	public void setPlayerDying(boolean playerDying) {
		this.playerDying = playerDying;
		
	}




}
