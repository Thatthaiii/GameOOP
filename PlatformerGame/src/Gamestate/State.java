package Gamestate;

import java.awt.event.MouseEvent;


import main.Game;
import ui.MenuButton;

public class State {
	
	public Game game;
	public State(Game game) {
		this.game = game;
	}
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(),e.getY());
	}
	public Game getGame() {
		return game;
	}
	@SuppressWarnings("incomplete-switch")
	public void setGamestate(GameState state) {
		GameState.state = state;
	}

}
