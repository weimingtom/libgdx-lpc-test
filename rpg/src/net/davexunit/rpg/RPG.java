package net.davexunit.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class RPG extends Game {
	private final GameState state;
	AssetManager manager;
	LoadingScreen loadingScreen;
	MainMenuScreen mainMenuScreen;
	ExploreScreen exploreScreen;
	BattleScreen battleScreen;
	
	public RPG() {
		state = new GameState();
	}
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		
		manager = new AssetManager();
		
		state.setDatabaseHelper(new DummyDatabaseHelper());
		state.setConfigHelper(new DummyConfigHelper());
		state.load(Gdx.files.internal("data/saves/save1"));
		
		loadingScreen = new LoadingScreen(this);
		exploreScreen = new ExploreScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		battleScreen = new BattleScreen(this);
		setScreen(loadingScreen);
	}

	public GameState getState() {
		return state;
	}
}
