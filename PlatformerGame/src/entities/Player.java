package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.*;

import static Gamestate.playing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static utilz.HelpMethods.*;

import javax.imageio.ImageIO;


import Gamestate.playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.*;
public class Player extends Entity {
	private BufferedImage[][] animations;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	
	private playing playing;

	private int[][] lvlData;
	private double xDrawOffset = 10* Game.SCALE;
	private double yDrawOffset = 4 * Game.SCALE;
	
	// Jumping / Gravity
	
	
	private float jumpSpeed = (float)(-2.25f * Game.SCALE);
	private float fallSpeedAfterCollision = (float)(0.5f * Game.SCALE);
	
	// StatusBarUI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	
	private int powerBarWidth = (int) (104 * Game.SCALE);
	private int powerBarHeight = (int) (2 * Game.SCALE);
	private int powerBarXStart = (int) (44 * Game.SCALE);
	private int powerBarYStart = (int) (34 * Game.SCALE);
	private int powerWidth = powerBarWidth;
	private int powerMaxValue = 200;
	private int powerValue = powerMaxValue;

	private int maxHealth = 100;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	
	
	private int tileY = 0;

	
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean powerAttackActive;
	private int powerAttackTick;
	private int powerGrowSpeed = 15;
	private int powerGrowTick;
	private boolean attackChecked;
	
	public Player(float x, float y , int width , int height, playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 100;
		this.currentHealth = maxHealth;
		this.walkSpeed = Game.SCALE * 0.7f;
		loadAnimations();
		initAttackBox();
		initHitbox(10, 24);
		
		
	}
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}
	
	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (10 * Game.SCALE), (int) (10 * Game.SCALE));
	}

	public void update() {
		updateHealthBar();
		updatePowerBar();

		if (currentHealth <= 0) {
			if (state != DEAD) {
				state = DEAD;
				aniTick = 0;
				aniIndex = 0;
				playing.setPlayerDying(true);
//				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);

				// Check if player died in air
				if (!IsEntityOnFloor(hitbox, lvlData)) {
					inAir = true;
					airSpeed = 0;
				}
			} else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
				playing.setGameOver(true);
//				playing.getGame().getAudioPlayer().stopSong();
//				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else {
				updateAnimationTick();

				// Fall if in air
				if (inAir)
					if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
						hitbox.y += airSpeed;
						airSpeed += GRAVITY;
					} else
						inAir = false;

			}

			return;
		}

		updateAttackBox();

		if (state == HIT) {
			if (aniIndex <= GetSpriteAmount(state) - 3)
				pushBack(pushBackDir, lvlData, 0.2f);
			updatePushBackDrawOffset();
		} else
			updatePos();

		if (moving) {

			checkInsideWater();

			tileY = (int) (hitbox.y / Game.TILES_SIZE);
			if (powerAttackActive) {
				powerAttackTick++;
				if (powerAttackTick >= 35) {
					powerAttackTick = 0;
					powerAttackActive = false;
				}
			}
		}

		if (attacking || powerAttackActive)
			checkAttack();

		updateAnimationTick();
		setAnimation();
	}

	


	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;

		if (powerAttackActive)
			attackChecked = false;

		playing.checkEnemyHit(attackBox);

	}


	private void updateAttackBox() {
		if(right && left) {
			if(flipW == 1) {
				attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 1);
			}else {
				attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 1);
			}
				
		}else if (right || powerAttackActive && flipW == 1)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 1);
		else if (left || powerAttackActive && flipW == -1)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 1);

		attackBox.y = hitbox.y + (Game.SCALE * 8);
	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}
	private void updatePowerBar() {
		powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);
		
		powerGrowTick++;
		if(powerGrowTick >= powerGrowSpeed){
			powerGrowTick = 0;
			changePower(1);
		}
		
	}



	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset + (int) (pushDrawOffset)), width * flipW, height, null);

//		drawAttackBox(g, lvlOffset);
		drawUI(g);
	}



	
	private void drawUI(Graphics g) {
		//BG UI
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		
		//Healthbar UI
		g.setColor(Color.RED);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
		
		//Powerbar UI
		g.setColor(Color.YELLOW);
		g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
	}



	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
				if (state == HIT) {
					newState(IDLE);
					airSpeed = 0f;
					if (!IsFloor(hitbox, 0, lvlData))
						inAir = true;
				}
			}
		}
	}

	
	private void setAnimation() {
		int startAni = state;
		
		if (state == HIT) {
			return;
		}
		if (moving)
			state = RUNNING;
		else
			state = IDLE;

		if (inAir) {
			if (airSpeed <= 0)
				state = JUMP;
			else
				state = FALLING;
		}
		if (powerAttackActive) {
			state = ATTACK_2;
			aniIndex = 1;
			aniTick = 0;
			return;
			
		}

		if (attacking) {
			state = ATTACK_1;
			if (startAni != ATTACK_1) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}
		if (startAni != state)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}


	
	
		

	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}
	private void resetInAir() {
		inAir = false;

		airSpeed = 0;
	}



	
	
	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!inAir)
			if(!powerAttackActive)
				if ((!left && !right) || (right && left))
					return;

		float xSpeed = 0;

		if (left && !right) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right && !left) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}
		
		if (powerAttackActive) {
			if ((!left && !right) || (left && right)) {
				if (flipW == -1)
					xSpeed = -walkSpeed;
				else
					xSpeed = walkSpeed;
			}

			xSpeed *= 2;
		}
			

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir && !powerAttackActive) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		} else
			updateXPos(xSpeed);
		moving = true;
	}
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
			if(powerAttackActive) {
				powerAttackActive = false;
				powerAttackTick = 0;
			}
		}

	}
	public void changeHealth(int value) {
		if (value < 0) {
			if (state == HIT)
				return;
			else
				newState(HIT);
		}
		currentHealth += value;
		currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
	}

	public void changeHealth(int value, Enemy e) {
		if (state == HIT)
			return;
		changeHealth(value);
		pushBackOffsetDir = UP;
		pushDrawOffset = 0;

		if (e.getHitbox().x < hitbox.x)
			pushBackDir = RIGHT;
		else
			pushBackDir = LEFT;
	}

	private void changePower(int value) {
		powerValue += value;
		if(powerValue >= powerMaxValue)
			powerValue = powerMaxValue;
		else if(powerValue <= 0)
			powerValue = 0;
	}

	//Load Animation from png files to an Array
	private void loadAnimations() {

			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[10][10];
			for (int j = 0; j < animations.length; j++)
				for (int i = 0; i < animations[j].length; i++)
					animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
			
			statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);


	}
	private void checkInsideWater() {
		if (IsEntityInWater(hitbox, playing.getLevelManager().getCurrentLevel().getLevelData()))
			currentHealth = 0;
	}


	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		

	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		state = IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	public void powerAttack() {
		if (powerAttackActive)
			return;
		if (powerValue >= 60) {
			powerAttackActive = true;
			changePower(-60);
		}

	}
	public int getTileY() {
		return tileY;
	}

			
		
}


	
	


