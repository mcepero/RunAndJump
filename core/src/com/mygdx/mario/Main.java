package com.mygdx.mario;

import com.badlogic.gdx.Game;
import com.mygdx.mario.utils.MenuInicio;

public class Main extends Game {
	@Override
	public void create() {
		menu();
	}

	public void menu() {
		setScreen(new MenuInicio(this));
	}

	public void juego() {
		setScreen(new GamePlayScreen("Nivel1"));
	}
}
