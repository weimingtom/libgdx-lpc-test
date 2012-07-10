package net.davexunit.rpg;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class BattleScreen extends InputAdapter implements Screen {
	BattleEngine engine;
	InputMultiplexer input;
	TextureAtlas atlas;
	Stage stage;
	Stage uiStage;
	RPG game;
	
	public BattleScreen (RPG game) {
		this.game = game;
	}
	
	/*public BattleScreen (ArrayList<Player> party, String area) {
		mEngine = new BattleEngine(party, area);
	}*/

	public void newBattle(String area) {
		BattleEngine.setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		engine = new BattleEngine(area, atlas);	
		
		if (stage == null) {
			stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
			uiStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		}
		
		stage.clear();
		
		for(BattleActor player : engine.getParty())
			stage.addActor(player);
		
		for(BattleActor player : engine.getEnemies())
			stage.addActor(player);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.act(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.draw();	
		uiStage.draw();
	}
	


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if (stage == null) {
			stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
			uiStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		}
		
		OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage.setCamera(camera);
		
		atlas = game.manager.get("data/sprites/spritepack.atlas", TextureAtlas.class);
		
		NinePatch patch = atlas.createPatch("dialog-box");
		
		StyledTable.TableStyle textBoxStyle = new StyledTable.TableStyle();
		textBoxStyle.background = new NinePatchDrawable(patch);
		textBoxStyle.font = new BitmapFont();
		textBoxStyle.padX = 8;
		textBoxStyle.padY = 4;
		TextBox textBox = new TextBox("This is a test....", textBoxStyle);
		textBox.setWidth(Gdx.graphics.getWidth());
		textBox.setHeight(Gdx.graphics.getHeight() / 4);
		uiStage.addActor(textBox);
		
		input = new InputMultiplexer();
		input.addProcessor(this);
		input.addProcessor(stage);
		Gdx.input.setInputProcessor(input);
		
		newBattle("grassland");
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
		stage.dispose();
		uiStage.dispose();
	}
}
