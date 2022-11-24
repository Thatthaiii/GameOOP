package utilz;

import main.Game;

public class Constants {
	public static final float GRAVITY = (float)(0.04f * Game.SCALE);
	public static final int ANI_SPEED = 30;
	public static class Dialogue {
		public static final int QUESTION = 0;
		public static final int EXCLAMATION = 1;

		public static final int DIALOGUE_WIDTH = (int) (14 * Game.SCALE);
		public static final int DIALOGUE_HEIGHT = (int) (12 * Game.SCALE);

		public static int GetSpriteAmount(int type) {
			switch (type) {
			case QUESTION, EXCLAMATION:
				return 5;
			}

			return 0;
		}
	}


	public static class EnemyConstants{
		public static final int FRIES = 0;
		public static final int DONUT = 1;
		public static final int BURGER = 2;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		public static final int FRIES_WIDTH_DEFAULT = 32;
		public static final int FRIES_HEIGHT_DEFAULT = 32;
		
		public static final int FRIES_WIDTH = (int)(FRIES_WIDTH_DEFAULT * Game.SCALE);
		public static final int FRIES_HEIGHT = (int)(FRIES_HEIGHT_DEFAULT * Game.SCALE);
		public static final int FRIES_DRAWOFFSET_X = (int)(10 * Game.SCALE);
		public static final int FRIES_DRAWOFFSET_Y = (int)(11 * Game.SCALE);
		public static final int DONUT_WIDTH_DEFAULT = 32;
		public static final int DONUT_HEIGHT_DEFAULT = 32;
		public static final int DONUT_WIDTH = (int) (DONUT_WIDTH_DEFAULT * Game.SCALE);
		public static final int DONUT_HEIGHT = (int) (DONUT_HEIGHT_DEFAULT * Game.SCALE);
		public static final int DONUT_DRAWOFFSET_X = (int) (9 * Game.SCALE);
		public static final int DONUT_DRAWOFFSET_Y = (int) (7 * Game.SCALE);

		public static final int BURG_WIDTH_DEFAULT = 32;
		public static final int BURG_HEIGHT_DEFAULT = 32;
		public static final int BURG_WIDTH = (int) (BURG_WIDTH_DEFAULT * Game.SCALE);
		public static final int BURG_HEIGHT = (int) (BURG_HEIGHT_DEFAULT * Game.SCALE);
		public static final int BURG_DRAWOFFSET_X = (int) (8 * Game.SCALE);
		public static final int BURG_DRAWOFFSET_Y = (int) (6 * Game.SCALE);



		
		public static int GetSpriteAmount(int enemy_type, int enemy_state){
			
				
					switch(enemy_state) {
					case IDLE:
						if (enemy_type == FRIES)
							return 4;
						else if (enemy_type == DONUT || enemy_type == BURGER)
							return 8;

					case RUNNING:
						return 5;
					case ATTACK:
						if (enemy_type == BURGER)
							return 8;
						if (enemy_type == DONUT)
							return 7;
						return 9;
					case HIT:
						return 5;
					case DEAD:
						return 4;
					}
			
			return 0;
		}
		//Set Enemy HP here
		public static int GetMaxHealth(int enemy_type) {
			switch(enemy_type) {
			case FRIES:
				return 20;
			case DONUT:
				return 25;
			case BURGER:
				return 500;
			default:
				return 1;

			}
		}
		//Set Enemy Damage here
		public static int GetEnemyDmg(int enemy_type) {
			switch(enemy_type) {
			case FRIES:
				return 15;
			case DONUT:
				return 20;
			case BURGER:
				return 30;
			default:
				return 0;

			}
		}
	}


	
	
	public static class Projectiles{
		public static final double SPEED = 0.5f * Game.SCALE;
	}
	
	public static class UI {
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
		}
		public static class PauseButton{
			public static final int SOUND_SIZE_DEFAULT = 64;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		public static class URMButtons{
			public static final int URM_SIZE_DEFAULT = 64;
			public static final int URM_SIZE = (int)(URM_SIZE_DEFAULT * Game.SCALE);
		}
		public static class VolumeButtons{
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	public static class PlayerConstants{
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int HIT = 2;
		public static final int JUMP = 3;
		public static final int FALLING = 4;
		public static final int DEAD = 5;
		public static final int ATTACK_1 = 6;
		public static final int ATTACK_2 = 7;
		
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case IDLE:
				return 5;
			case RUNNING:
				return 5;
			case HIT:
				return 5;
			case JUMP:
				return 2;
			case FALLING:
				return 1;
			case DEAD:
				return 5;
			case ATTACK_1:
				return 3;
			case ATTACK_2:
				return 7;
			default:
				return 1;
			}
		}
	}
}
