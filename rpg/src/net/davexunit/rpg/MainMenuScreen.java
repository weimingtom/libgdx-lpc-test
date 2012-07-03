package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenuScreen extends InputAdapter implements Screen {
	RPG game;
	InputMultiplexer input;
	Stage stage;
	Tileset buttonTileset;
	NinePatch buttonPatchUp;
	NinePatch buttonPatchDown;
	TextButton button;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		buttonTileset = new Tileset(new Texture(Gdx.files.internal("data/button.png")), 32, 32, 0, 0);
		
		buttonPatchUp = new NinePatch(buttonTileset.getTile(0), 8, 8, 8, 8);
		buttonPatchDown = new NinePatch(buttonTileset.getTile(1), 8, 8, 8, 8);
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = new NinePatchDrawable(buttonPatchUp);
		buttonStyle.down = new NinePatchDrawable(buttonPatchDown);
		buttonStyle.checked = new NinePatchDrawable(buttonPatchDown);
		buttonStyle.font = new BitmapFont();
		
		button = new TextButton("Play Game", buttonStyle);
		button.setX(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2);
		button.setY(Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(ActorEvent event, float x, float y) {
				game.setScreen(game.exploreScreen);
			}
	    });
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.fontColor = Color.BLACK;
		labelStyle.font = new BitmapFont();
		
		Label title = new Label("LPC Test Game", labelStyle);
		title.setX(Gdx.graphics.getWidth() / 2 - title.getWidth() / 2);
		title.setY(Gdx.graphics.getHeight() / 2 + 40);
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		stage.addActor(button);
		stage.addActor(title);
		
		input = new InputMultiplexer();
		input.addProcessor(this);
		input.addProcessor(stage);
		Gdx.input.setInputProcessor(input);
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
