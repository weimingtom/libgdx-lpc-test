package net.davexunit.rpg;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.ActorListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class LoadGameScreen implements Screen {
	private RPG game;
	private Stage stage;
	
	public LoadGameScreen(RPG game) {
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
		stage = new Stage();
				
		TextureAtlas atlas = game.manager.get("data/sprites/spritepack.atlas", TextureAtlas.class);
		SpriteDrawable selection = new SpriteDrawable(new Sprite(atlas.findRegion("list-selection")));
		selection.setLeftWidth(32);
		selection.setTopHeight(6);
		selection.setBottomHeight(4);
		NinePatchDrawable backgroundPatch = new NinePatchDrawable(atlas.createPatch("list-box"));
		
		Label title = new Label("Load Game", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		Table table = new Table();
		table.debug();
		table.setPosition(0, 0);
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.pad(10).defaults().spaceBottom(10);
		table.row().fill().expand().left().top();
		table.add(title).left();
		table.row().fill().expand().left().top();
		
		stage.addActor(table);
		
		Gdx.input.setInputProcessor(stage);
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
}
