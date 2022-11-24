package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.PauseButton.*;

public class SoundButton extends PauseButton{
	private BufferedImage[][] soundImgs;
	private boolean mouseOver,mousePressed;
	private boolean muted;
	private int rowIndex, colIndex;
	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		LoadSoundImgs();
	}
	private void LoadSoundImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTON);
		soundImgs = new BufferedImage[2][3];
		for(int j = 0; j < soundImgs.length; j++)
			for(int i = 0; i < soundImgs[j].length ; i++)
				soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
	}
	public void update() {
		if(muted)
			rowIndex = 1;
		else
			rowIndex = 0;
		
		
		colIndex = 0;
		if(mouseOver)
			colIndex = 1;
		if(mousePressed)
			colIndex = 2;
	}
	public void draw(Graphics g) {
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
	}
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}
	public boolean isMousePressed() {
		return mousePressed;
	}
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	public boolean isMuted() {
		return muted;
	}
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
}
