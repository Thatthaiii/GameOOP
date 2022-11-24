package Levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Donut;
import entities.Fries;

import entities.Burger;
import main.Game;
//import objects.BackgroundTree;
//import objects.Cannon;
//import objects.GameContainer;
import objects.Grass;
//import objects.Potion;
//import objects.Spike;

import static utilz.Constants.EnemyConstants.*;
//import static utilz.Constants.ObjectConstants.*;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;

	private ArrayList<Fries> fries = new ArrayList<>();
	private ArrayList<Donut> donuts = new ArrayList<>();
	private ArrayList<Burger> burgers = new ArrayList<>();
	private ArrayList<Grass> grass = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	private void loadLevel() {

		// Looping through the image colors just once. Instead of one per
		// object/enemy/etc..
		// Removed many methods in HelpMethods class.

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				//get value from getColor
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
//				loadObjects(blue, x, y);
			}
	}

	private void loadLevelData(int redValue, int x, int y) {
		if (redValue >= 50)
			lvlData[y][x] = 0;
		else
			lvlData[y][x] = redValue;
		switch (redValue) {
		case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 -> 
		grass.add(new Grass((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
		}
	}

	private int getRndGrassType(int xPos) {
		return xPos % 2;
	}

	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
		case FRIES -> fries.add(new Fries(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
		case DONUT -> donuts.add(new Donut(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
		case BURGER -> burgers.add(new Burger(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
		case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
		}
	}



	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_INWIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Fries> getFries() {
		return fries;
	}

	public ArrayList<Burger> getBurgers() {
		return burgers;
	}
//
//	public ArrayList<Potion> getPotions() {
//		return potions;
//	}
//
//	public ArrayList<GameContainer> getContainers() {
//		return containers;
//	}
//
//	public ArrayList<Spike> getSpikes() {
//		return spikes;
//	}
//
//	public ArrayList<Cannon> getCannons() {
//		return cannons;
//	}
//
	public ArrayList<Donut> getDonut() {
		return donuts;
	}
//
//	public ArrayList<BackgroundTree> getTrees() {
//		return trees;
//	}

	public ArrayList<Grass> getGrass() {
		return grass;
	}

}
