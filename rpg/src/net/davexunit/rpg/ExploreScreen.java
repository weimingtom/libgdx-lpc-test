package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;

public class ExploreScreen extends InputAdapter implements Screen {
	private RPG game;
	private SpriteBatch batch;
	private Texture texture;
	//private Sprite sprite;
	private Stage stage;
	private Player player;
	private TiledMap map;
	private TileAtlas tileAtlas;
	private TileMapRenderer tileMapRenderer;
	private OrthographicCamera camera;
	
	public ExploreScreen(RPG game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		centerCamera();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tileMapRenderer.render(camera);
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
	}
	
	public void centerCamera() {
		float x = player.x;
		float y = player.y;
		float halfW = Gdx.graphics.getWidth() / 2;
		float halfH = Gdx.graphics.getHeight() / 2;
		float mapW = tileMapRenderer.getMapWidthUnits();
		float mapH = tileMapRenderer.getMapHeightUnits();
		
		if(x < halfW)
			x = halfW;
		else if(x > mapW - halfW) {
			x = mapW - halfW;
		}
		
		if(y < halfH) {
			y = halfH;
		} else if(y > mapH - halfH) {
			y = mapH - halfH;
		}
		
		camera.position.set(x, y, 0);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		camera.translate(100, 100);
		
		batch = new SpriteBatch();
		
/*		texture = new Texture(Gdx.files.internal("data/grass.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);*/
		
		stage = new Stage(w, h, false, batch);
		stage.setCamera(camera);
		player = new Player();
		player.x = 20;
		player.y = 20;
		
		stage.addActor(player);
		
		map = TiledLoader.createMap(Gdx.files.internal("data/maps/test2.tmx"));
		tileAtlas = new TileAtlas(map, Gdx.files.internal("data/maps/"));
		tileMapRenderer = new TileMapRenderer(map, tileAtlas, 32, 32);
		
		Gdx.input.setInputProcessor(this);
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
		batch.dispose();
		texture.dispose();
		stage.dispose();
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		final float speed = 200;
		Vector2 pos = new Vector2();
		stage.toStageCoordinates(x, y, pos);
		//x += camera.position.x;
		//y = (int) ((Gdx.graphics.getHeight() - y) + camera.position.y);
		float dx = pos.x - player.x;
		float dy = pos.y - player.y;
		float distance = (float) Math.sqrt(dx * dx + dy * dy);
		player.action(MoveBy.$(dx, dy, distance / speed));
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
			case Input.Keys.BACK:
			case Input.Keys.ESCAPE:
				game.setScreen(game.mainMenuScreen);
				return true;
		}
		
		return false;
	}
}
