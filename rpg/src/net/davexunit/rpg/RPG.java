package net.davexunit.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RPG extends Game {
	private final GameState state;
	AssetManager manager;
	TextureAtlas atlas;
	MainMenuScreen mainMenuScreen;
	ExploreScreen exploreScreen;
	
	public RPG() {
		state = new GameState();
	}
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		
		manager = new AssetManager();
		manager.load("data/sprites/spritepack.atlas", TextureAtlas.class);
		
		atlas = new TextureAtlas("data/sprites/spritepack.atlas");
		
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
