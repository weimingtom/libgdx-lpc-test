package net.davexunit.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class RPG extends Game {
	MainMenuScreen mainMenuScreen;
	ExploreScreen exploreScreen;
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		
		exploreScreen = new ExploreScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		setScreen(mainMenuScreen);
	}
}
