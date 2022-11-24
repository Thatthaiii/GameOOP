package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Gamestate.playing;
import Levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

	private playing playing;
	private Level currentLevel;
	private BufferedImage[][] FriesArr,DonutArr, BurgerArr;
	

	public EnemyManager(playing playing) {
		this.playing = playing;
		loadEnemyImgs();

	}

	public void loadEnemies(Level level) {
		this.currentLevel = level;
	}

	public void update(int[][] lvlData) {
		boolean isAnyActive = false;
		for (Fries c : currentLevel.getFries())
			if (c.isActive()) {
				c.update(lvlData, playing);
				isAnyActive = true;
				
			}
		for (Donut p : currentLevel.getDonut())
			if (p.isActive()) {
				p.update(lvlData, playing);
				isAnyActive = true;
			}

		for (Burger s : currentLevel.getBurgers())
			if (s.isActive()) {
				s.update(lvlData, playing);
				isAnyActive = true;
			}

		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawFries(g, xLvlOffset);
		drawDONUTS(g, xLvlOffset);
		drawBurgers(g, xLvlOffset);
	}
	
	private void drawBurgers(Graphics g, int xLvlOffset) {
		for (Burger s : currentLevel.getBurgers())
			if (s.isActive()) {
				g.drawImage(BurgerArr[s.getState()][s.getAniIndex()], (int) s.getHitbox().x - xLvlOffset - BURG_DRAWOFFSET_X + s.flipX(),
						(int) s.getHitbox().y - BURG_DRAWOFFSET_Y + (int) s.getPushDrawOffset(), BURG_WIDTH * s.flipW(), BURG_HEIGHT, null);
//				s.drawHitbox(g, xLvlOffset);
//				s.drawAttackBox(g, xLvlOffset);
			}
	}

	private void drawDONUTS(Graphics g, int xLvlOffset) {
		for (Donut p : currentLevel.getDonut())
			if (p.isActive()) {
				g.drawImage(DonutArr[p.getState()][p.getAniIndex()], (int) p.getHitbox().x - xLvlOffset - DONUT_DRAWOFFSET_X + p.flipX(),
						(int) p.getHitbox().y - DONUT_DRAWOFFSET_Y + (int) p.getPushDrawOffset(), DONUT_WIDTH * p.flipW(), DONUT_HEIGHT, null);
//				p.drawHitbox(g, xLvlOffset);
			}
	}


	private void drawFries(Graphics g, int xLvlOffset) {
		for (Fries c : currentLevel.getFries())
			if (c.isActive()) {
				g.drawImage(FriesArr[c.getState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - FRIES_DRAWOFFSET_X + c.flipX(), (int) c.getHitbox().y - FRIES_DRAWOFFSET_Y,
						FRIES_WIDTH * c.flipW(), FRIES_HEIGHT, null);
//				c.drawHitbox(g, xLvlOffset);
//				c.drawAttackBox(g, xLvlOffset);

			}

	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Fries c : currentLevel.getFries())
			if (c.isActive())
				if (c.getState() != DEAD && c.getState() != HIT)
					if (attackBox.intersects(c.getHitbox())) {
						//set damage taken
						c.hurt(500);
						return;
					}

		for (Donut p : currentLevel.getDonut())
			if (p.isActive()) {
				if (p.getState() == ATTACK && p.getAniIndex() >= 3)
					return;
				else {
					if (p.getState() != DEAD && p.getState() != HIT)
						if (attackBox.intersects(p.getHitbox())) {
							//set damage taken
							p.hurt(500);
							return;
						}
				}
			}

		for (Burger s : currentLevel.getBurgers())
			if (s.isActive()) {
				if (s.getState() != DEAD && s.getState() != HIT)
					if (attackBox.intersects(s.getHitbox())) {
						//set damage taken
						s.hurt(500);
						return;
					}
			}
	}


	private void loadEnemyImgs() {
		FriesArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.FRIES_ATLAS), 9, 5, FRIES_WIDTH_DEFAULT, FRIES_HEIGHT_DEFAULT);
		DonutArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.DONUT_ATLAS), 7, 5, DONUT_WIDTH_DEFAULT, DONUT_HEIGHT_DEFAULT);
		BurgerArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.BURGER_ATLAS), 8, 5, BURG_WIDTH_DEFAULT, BURG_HEIGHT_DEFAULT);
	}

	private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH){
		BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
		for (int j = 0; j < tempArr.length; j++)
			for (int i = 0; i < tempArr[j].length; i++)
				tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
		return tempArr;
	}

	public void resetAllEnemies() {
		for (Fries c : currentLevel.getFries())
			c.resetEnemy();
		for (Donut p : currentLevel.getDonut())
			p.resetEnemy();
		for (Burger s : currentLevel.getBurgers())
			s.resetEnemy();
	}






}