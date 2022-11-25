package Gamestate;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


import utilz.LoadSave;
import main.Game;
import ui.MenuButton;

public class menu extends State implements StateMethod{
	
	private MenuButton[] button  = new MenuButton[3];
	private BufferedImage backgroundImg;
	private int menuX, menuY, menuWidth, menuHeight;
	
	public menu(Game game) {
		super(game);
		loadButton();
		loadBackground();
		
	}

	private void loadButton() {
		button[0] = new MenuButton(Game.GAME_WIDTH / 2, (int)(150 * Game.SCALE), 0, GameState.PLAYING);
		button[1] = new MenuButton(Game.GAME_WIDTH / 2, (int)(220 * Game.SCALE), 1, GameState.OPTIONS);
		button[2] = new MenuButton(Game.GAME_WIDTH / 2, (int)(290 * Game.SCALE), 2, GameState.QUIT);
	}
	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BG);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int)(1 * Game.SCALE);

	}
	@Override
	public void update() {
		for(MenuButton mb : button)
			mb.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

		for(MenuButton mb : button)
			mb.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : button) {
			if(isIn(e,mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : button) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed())
					mb.applyGameState();
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton mb : button) 
			mb.resetBools();
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : button) 
			mb.setMouseOver(false);
		for(MenuButton mb : button)
			if(isIn(e,mb)) {
				mb.setMouseOver(true);
				break;
			}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			GameState.state = GameState.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}


	
}
