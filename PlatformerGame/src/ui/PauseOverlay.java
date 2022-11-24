package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Gamestate.GameState;
import Gamestate.playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButton.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
	private playing playing;
	private BufferedImage backgroundImg;
	private int bgX,bgY,bgW,bgH;
	private AudioOptions audioOptions;
	private UrmButton menuB, unPauseB;

	

	
	public PauseOverlay(playing playing){
		this.playing = playing;
		loadBackground();
		audioOptions = playing.getGame().getAudioOptions();

		createUrmButtons();

	}




	private void createUrmButtons() {
		int mainX = (int)(320*Game.SCALE);
		int resumeX = (int)(435*Game.SCALE);
		int BY = (int)(290*Game.SCALE);
		
		menuB = new UrmButton(mainX, BY,URM_SIZE,URM_SIZE,1);
		unPauseB = new UrmButton(resumeX, BY, URM_SIZE, URM_SIZE,0);
	}
	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BG);
		bgW = (int)(backgroundImg.getWidth() * Game.SCALE);
		bgH = (int)(backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = Game.GAME_HEIGHT/2 - bgH/2;
		
	}
	public void update() {

		
		menuB.update();
		unPauseB.update();
		
		audioOptions.update();

	}
	public void draw(Graphics g) {
		//BG
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		

		
		//URM Buttons
		menuB.draw(g);
		unPauseB.draw(g);
		
		audioOptions.draw(g);

	}
	public void mouseMoved() {
		
	}
	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);
	}
	
	public void mousePressed(MouseEvent e) {
		if (isIn(e,menuB))
			menuB.setMousePressed(true);
		else if (isIn(e,unPauseB))
			unPauseB.setMousePressed(true);
		else 
			audioOptions.mousePressed(e);

		
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(e,menuB)) {
			if(menuB.isMousePressed()) {
				GameState.state = GameState.MENU;
				playing.unpauseGame();
			}
		}else if(isIn(e,unPauseB)) {
			if(unPauseB.isMousePressed())
				playing.unpauseGame();
		}else
			audioOptions.mouseReleased(e);
		

		menuB.resetBools();
		unPauseB.resetBools();

	}
	
	public void mouseMoved(MouseEvent e) {

		menuB.setMouseOver(false);
		unPauseB.setMouseOver(false);


		if (isIn(e, menuB))
			menuB.setMouseOver(true);
		else if (isIn(e, unPauseB))
			unPauseB.setMouseOver(true);
		else 
			audioOptions.mouseMoved(e);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(),e.getY());
	}

}
