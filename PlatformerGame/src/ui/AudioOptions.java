package ui;

import static utilz.Constants.UI.PauseButton.SOUND_SIZE;
import static utilz.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utilz.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import Gamestate.GameState;
import main.Game;

public class AudioOptions {
	private volumeButton volumeButton;
	private SoundButton soundButton;
	private SongButton musicButton;
	public AudioOptions() {
		createSoundButtons();
		createVolumeButtons();
	}
	private void createVolumeButtons() {
		int vX = (int)(309*Game.SCALE);
		int vY = (int)(235*Game.SCALE);
		volumeButton = new volumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}



	private void createSoundButtons() {
		int soundX = (int)(435*Game.SCALE);
		int musicX = (int)(320*Game.SCALE);
		int soundY = (int)(150*Game.SCALE);
		musicButton = new SongButton(musicX, soundY, SOUND_SIZE, SOUND_SIZE);
		soundButton = new SoundButton(soundX, soundY, SOUND_SIZE, SOUND_SIZE);
	}
	
	public void update() {
		musicButton.update();
		soundButton.update();
		volumeButton.update();
	}
	public void draw(Graphics g) {
		//Sound Buttons
		musicButton.draw(g);
		soundButton.draw(g);
		//Volume Slider
		volumeButton.draw(g);
	}
	public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if (isIn(e,musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e,soundButton))
			soundButton.setMousePressed(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMousePressed(true);

		
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(e,musicButton)) {
			if(musicButton.isMousePressed()) 
				musicButton.setMuted(!musicButton.isMuted());	
		
		}else if(isIn(e,soundButton)) {
			if(soundButton.isMousePressed())
				soundButton.setMuted(!soundButton.isMuted());
		}
		
		musicButton.resetBools();
		soundButton.resetBools();
		volumeButton.resetBools();
	}
	
	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		soundButton.setMouseOver(false);

		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, soundButton))
			soundButton.setMouseOver(true);

		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(),e.getY());
	}
}
