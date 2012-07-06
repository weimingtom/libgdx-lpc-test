package net.davexunit.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class RPG extends Game {
	private final GameState state;
	MainMenuScreen mainMenuScreen;
	ExploreScreen exploreScreen;
	
	public RPG() {
		state = new GameState();
	}
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		
		state.setDatabaseHelper(new DummyDatabaseHelper());
		state.setConfigHelper(new DummyConfigHelper());
		state.load(Gdx.files.internal("data/saves/save1"));
		
		exploreScreen = new ExploreScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		setScreen(mainMenuScreen);
	}

	public GameState getState() {
		return state;
	}
}
