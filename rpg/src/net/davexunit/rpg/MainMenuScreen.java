package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenuScreen extends InputAdapter implements Screen {
	RPG game;
	InputMultiplexer input;
	Stage stage;
	TextureAtlas atlas;
	Tileset buttonTileset;
	NinePatch buttonPatchUp;
	NinePatch buttonPatchDown;
	TextButtonStyle buttonStyle;
	
	public MainMenuScreen(RPG game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		stage.act(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		atlas = game.manager.get("data/sprites/spritepack.atlas", TextureAtlas.class);
		
		buttonPatchUp = atlas.createPatch("button-up");
		buttonPatchDown = atlas.createPatch("button-down");
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = new NinePatchDrawable(buttonPatchUp);
		buttonStyle.down = new NinePatchDrawable(buttonPatchDown);
		buttonStyle.checked = new NinePatchDrawable(buttonPatchDown);
		buttonStyle.font = new BitmapFont();
		
		TextButton continueGame = new TextButton("Continue", buttonStyle);
		continueGame.addListener(new ClickListener() {
			@Override
			public void clicked(ActorEvent event, float x, float y) {
				game.setScreen(game.exploreScreen);
			}
	    });
		
		TextButton loadGame = new TextButton("Load Game", buttonStyle);
		loadGame.addListener(new ClickListener() {
			@Override
			public void clicked(ActorEvent event, float x, float y) {
				game.setScreen(game.exploreScreen);
			}
	    });
		
		TextButton newGame = new TextButton("New Game", buttonStyle);
		newGame.addListener(new ClickListener() {
			@Override
			public void clicked(ActorEvent event, float x, float y) {
				game.setScreen(game.exploreScreen);
			}
	    });
		
		TextButton options = new TextButton("Options", buttonStyle);
		options.addListener(new ClickListener() {
			@Override
			public void clicked(ActorEvent event, float x, float y) {
				game.setScreen(game.exploreScreen);
			}
	    });
		
		Table table = new Table();
		table.setWidth(300);
		table.setPosition(Gdx.graphics.getWidth() / 2 - table.getWidth() / 2, 150);
		table.pad(10).defaults().spaceBottom(10);
		table.row().fill().expandX();
		table.add(continueGame);
		table.row().fill().expandX();
		table.add(loadGame);
		table.row().fill().expandX();
		table.add(newGame);
		table.row().fill().expandX();
		table.add(options);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.fontColor = Color.BLACK;
		labelStyle.font = new BitmapFont();
		
		Label title = new Label("LPC Test Game", labelStyle);
		title.setX(Gdx.graphics.getWidth() / 2 - title.getWidth() / 2);
		title.setY(Gdx.graphics.getHeight() / 2);
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		stage.addActor(table);
		stage.addActor(title);
		
		input = new InputMultiplexer();
		input.addProcessor(this);
		input.addProcessor(stage);
		Gdx.input.setInputProcessor(input);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
			case Input.Keys.BACK:
			case Input.Keys.ESCAPE:
				Gdx.app.exit();
				return true;
		}
		
		return false;
	}
}
