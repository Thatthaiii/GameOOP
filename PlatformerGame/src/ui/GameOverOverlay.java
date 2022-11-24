package ui;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


import Gamestate.GameState;
import Gamestate.playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {

	private playing playing;
	private BufferedImage img;
	private int imgX, imgY, imgW, imgH;
	private UrmButton menu, play;

	public GameOverOverlay(playing playing) {
		this.playing = playing;
		createImg();
		createButtons();
	}

	private void createButtons() {
		int menuX = (int) (320 * Game.SCALE);
		int playX = (int) (425 * Game.SCALE);
		int y = (int) (185 * Game.SCALE);
		play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 1);

	}

	private void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
		imgW = (int) (img.getWidth() * Game.SCALE);
		imgH = (int) (img.getHeight() * Game.SCALE);
		imgX = Game.GAME_WIDTH / 2 - imgW / 2;
		imgY = (int) (100 * Game.SCALE);

	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

		g.drawImage(img, imgX, imgY, imgW, imgH, null);

		menu.draw(g);
		play.draw(g);
	}

	public void update() {
		menu.update();
		play.update();
	}

	private boolean isIn(UrmButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(play, e))
			play.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				playing.setGamestate(GameState.MENU);
			}
		} else if (isIn(play, e))
			if (play.isMousePressed()) {
				playing.resetAll();
				playing.getGame();
			}

		menu.resetBools();
		play.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(play, e))
			play.setMousePressed(true);
	}

}
