package net.davexunit.rpg;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BattleScreen extends InputAdapter implements Screen {
	
	BattleEngine mEngine;
	InputMultiplexer mInput;
	Stage mStage;
	Stage mUiStage;
	RPG game;
	
	public BattleScreen (RPG game) {
		this.game = game;
	}
	
	/*public BattleScreen (ArrayList<Player> party, String area) {
		mEngine = new BattleEngine(party, area);
	}*/

	public void newBattle(Player p, String area) {
		BattleEngine.setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mEngine = new BattleEngine(p, area);
		
		if (mStage == null) {
			mStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
			mUiStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		}
		
		mStage.clear();
		
		for(Player player : mEngine.getParty())
			mStage.addActor(player);
		
		for(Player player : mEngine.getEnemies())
			mStage.addActor(player);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		mStage.act(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		mStage.draw();	
		mUiStage.draw();
	}
	


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (mStage == null) {
			mStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
			mUiStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		}
		
		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		mStage.setCamera(camera);
		
		TextBox textBox = new TextBox("This is a test....");
		textBox.width = Gdx.graphics.getWidth();
		textBox.height = Gdx.graphics.getHeight() / 4;
		mUiStage.addActor(textBox);
		
		mInput = new InputMultiplexer();
		mInput.addProcessor(this);
		mInput.addProcessor(mStage);
		Gdx.input.setInputProcessor(mInput);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mStage.dispose();
		mUiStage.dispose();
	}
}
