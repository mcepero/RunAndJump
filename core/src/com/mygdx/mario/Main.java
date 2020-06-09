package com.mygdx.mario;

import com.badlogic.gdx.Game;

public class Main extends Game {
	public Main() {
	}

	@Override
	public void create() {
		setScreen(new GamePlayScreen("Nivel1"));
	}
}
