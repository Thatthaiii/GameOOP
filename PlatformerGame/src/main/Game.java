package main;

import java.awt.Graphics;

import Gamestate.GameOptions;
import Gamestate.GameState;
import Gamestate.playing;
import utilz.LoadSave;
import ui.AudioOptions;

import Gamestate.menu;

public class Game implements Runnable{
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private playing playing ;
	private menu menu;
	private GameOptions gameOptions;
	//cant use audio so leave it here
	private AudioOptions audioOptions;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_INWIDTH = 26;
	public final static int TILES_INHEIGHT = 14;
	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_INWIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_INHEIGHT;

	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		startGameLoop();
	}
	
	private void initClasses() {
		audioOptions = new AudioOptions(this);
		menu = new menu(this);
		playing = new playing(this);
		gameOptions = new GameOptions(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void update() {
		switch (GameState.state) {
		case MENU -> menu.update();
		case PLAYING -> playing.update();
		case OPTIONS -> gameOptions.update();
		case QUIT -> System.exit(0);
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public void render(Graphics g) {
		switch (GameState.state) {
		case MENU -> menu.draw(g);
		case PLAYING -> playing.draw(g);
		case OPTIONS -> gameOptions.draw(g);

		}
	}
	
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}

	}
	public void WindowFocusLost() {
		if(GameState.state == GameState.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	}
	public menu getMenu() {
		return menu;
	}
	public playing getPlaying() {
		return playing;
	}
	public GameOptions getGameOptions() {
		return gameOptions;
	}
	public AudioOptions getAudioOptions() {
		return audioOptions;
	}

	
}
