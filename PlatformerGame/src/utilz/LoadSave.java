package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Fries;
import main.Game;
import static utilz.Constants.EnemyConstants.FRIES;
public class LoadSave {
	//player sprite
	public static final String PLAYER_ATLAS = "Banana-animation.png";
	//tile sheet
	public static final String EXCLAMATION_ATLAS = "exclamation_atlas.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String MENU_BG = "background_menu.png";
	public static final String MENU_BUTTON = "menu_buttons.png";
	public static final String OPTIONS_MENU = "options_background.png";
	public static final String COMPLETED_IMG = "completed_sprite.png";
	public static final String GRASS_ATLAS = "grass_atlas.png";
	public static final String WATER_TOP = "water_atlas_animation.png";
	public static final String WATER_BOTTOM = "water.png";

	public static final String PAUSE_BG = "pause_bg.png";
	public static final String PAUSE_BUTTON = "resume_button.png";
	public static final String SOUND_BUTTON = "sound_buttons.png";
	public static final String SONG_BUTTON = "song_buttons.png";
	public static final String VOLUME_BUTTON = "volume_buttons.png";
	public static final String GAME_COMPLETED = "game_completed.png";
	public static final String DEATH_SCREEN = "death_screen.png";
	//enemy
	public static final String DONUT_ATLAS = "donut_atlas.png";
	public static final String BURGER_ATLAS = "burger_atlas.png";
	public static final String FRIES_ATLAS = "friensefire sprite sheet.png";
	public static final String QUESTION_ATLAS = "question_atlas.png";
	public static final String RAIN_PARTICLE = "rain_particle.png";
	public static final String PISTOL_BULLETS = "pistol_bullet.png";
	
	public static final String LVL_ONE = "1.png";
	public static final String LVL_TWO = "2.png";
	public static final String LVL_THREE = "3.png";
	public static final String LVL_FOUR = "4.png";
	public static final String LVL_FIVE = "5.png";
	public static final String LVL_SIX = "6.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/"+ fileName);	
		try {
			 img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static BufferedImage[] GetAllLevels() {
		BufferedImage lvlOne = GetSpriteAtlas(LVL_ONE);
		BufferedImage lvlTwo = GetSpriteAtlas(LVL_TWO);
		BufferedImage lvlThree = GetSpriteAtlas(LVL_THREE);
		BufferedImage lvlFour = GetSpriteAtlas(LVL_FOUR);
		BufferedImage lvlFive = GetSpriteAtlas(LVL_FIVE);
		BufferedImage lvlSix = GetSpriteAtlas(LVL_SIX);
		
		BufferedImage[] imgs = new BufferedImage[6];
		imgs[0] = lvlOne;
		imgs[1] = lvlTwo;
		imgs[2] = lvlThree;
		imgs[3] = lvlFour;
		imgs[4] = lvlFive;
		imgs[5] = lvlSix;
		return imgs;
	}


	

	

}
