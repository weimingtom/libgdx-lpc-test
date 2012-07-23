package net.davexunit.rpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;

public class RPG extends Game {
	public final GameState state;
	AssetManager manager;
	LoadingScreen loadingScreen;
	MainMenuScreen mainMenuScreen;
	LoadGameScreen loadGameScreen;
	ExploreScreen exploreScreen;
	InventoryScreen inventoryScreen;
	BattleScreen battleScreen;
	
	public RPG() {
		state = new GameState();
	}
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		
		manager = new AssetManager();
		manager.setLoader(Map.class, new MapLoader(new InternalFileHandleResolver()));
		
		state.setDatabaseHelper(new DummyDatabaseHelper());
		state.setConfigHelper(new DummyConfigHelper());
		state.load(Gdx.files.internal("data/saves/save1"));
		
		loadingScreen = new LoadingScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		loadGameScreen = new LoadGameScreen(this);
		exploreScreen = new ExploreScreen(this);
		inventoryScreen = new InventoryScreen(this);
		battleScreen = new BattleScreen(this);
		setScreen(loadingScreen);
	}
}
