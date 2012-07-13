package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LoadingScreen implements Screen {
	public static final int loadingTextures = 1;
	
	private RPG game;
	private int state;
	private SpriteBatch batch;
	private BitmapFont font;
	
	public LoadingScreen(RPG game) {
		this.game = game;
		this.state = loadingTextures;
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
	}
	
	@Override
	public void render(float delta) {
		if(game.manager.update())
			game.setScreen(game.exploreScreen);
		else {
			int w = Gdx.graphics.getWidth();
			int h = Gdx.graphics.getHeight();
			
			String text = null;
			
			switch(state) {
			case loadingTextures:
				text = "Loading textures...";
				break;
				
			default:
				text = "Loading...";
				break;
			}
			
			text += game.manager.getProgress();
			
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			batch.begin();
			font.draw(batch, text, w / 2 -  font.getBounds(text).width / 2, h / 2);
			batch.end();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		game.manager.load("data/sprites/spritepack.atlas", TextureAtlas.class);
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
	}
}
