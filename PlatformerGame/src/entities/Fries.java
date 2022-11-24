package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;
import static utilz.Constants.Dialogue.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import Gamestate.playing;

import static utilz.Constants.Directions.*;
import main.Game;

public class Fries extends Enemy {


	
	private int attackBoxOffsetX;
	
	public Fries(float x, float y) {
		super(x, y, FRIES_WIDTH, FRIES_HEIGHT, FRIES);
		initHitbox(13,19);
		iniAttackBox();
		
	}

	private void iniAttackBox() {
		attackBox = new Rectangle2D.Float(x,y,(int)(10 * Game.SCALE),(int)(10 * Game.SCALE));
		attackBoxOffsetX = (int)(Game.SCALE*3);
	}

	public void update(int[][] lvlData, playing playing) {
		updateBehavior(lvlData, playing);
		updateAnimationTick();
		updateAttackBox();
	}

	private void updateAttackBox() {
		if (walkDir == RIGHT)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 0.5);
		else if (walkDir == LEFT)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 0.5);
		attackBox.y = hitbox.y + (Game.SCALE * 2);

	}
	private void updateBehavior(int[][] lvlData, playing playing) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir)
			inAirChecks(lvlData, playing);
		else {
			switch (state) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				if (canSeePlayer(lvlData, playing.getPlayer())) {
					turnTowardsPlayer(playing.getPlayer());
					if (isPlayerCloseForAttack(playing.getPlayer()))
						newState(ATTACK);
				}

				move(lvlData);
				
					

				break;

			case ATTACK:
				if (aniIndex == 0)
					attackChecked = false;
				if (aniIndex == 7 && !attackChecked)
					checkPlayerHit(attackBox, playing.getPlayer());
				break;
			case HIT:
				if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
					pushBack(pushBackDir, lvlData, 2f);
				updatePushBackDrawOffset();
				break;
			}
		}
	}


	



	public int flipX() {
		if(walkDir == RIGHT)
			return width;
		else
			return 0;
	}
	public int flipW() {
		if(walkDir == RIGHT)
			return -1;
		else
			return 1;
	}

}