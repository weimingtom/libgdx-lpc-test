package net.davexunit.rpg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveBy;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ExploreScreen extends InputAdapter implements Screen {
	private RPG game;
	private SpriteBatch batch;
	private Texture texture;
	// private Sprite sprite;
	private Stage stage;
	private Stage uiStage;
	private Player player;
	private final float playerSpeed;
	private TiledMap map;
	private TileAtlas tileAtlas;
	private TileMapRenderer tileMapRenderer;
	private OrthographicCamera camera;
	private Pathfinder pathfinder;

	public ExploreScreen(RPG game) {
		this.game = game;
		playerSpeed = 250;
	}

	@Override
	public void render(float delta) {
		final int[] underLayers = { 0, 1, 2 };
		final int[] overLayers = { 3, 4 };

		stage.act(Gdx.graphics.getDeltaTime());
		centerCamera();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		tileMapRenderer.render(camera, underLayers);
		stage.draw();
		tileMapRenderer.render(camera, overLayers);
		uiStage.draw();
	}

	public void centerCamera() {
		float x = player.x;
		float y = player.y;
		float halfW = Gdx.graphics.getWidth() / 2;
		float halfH = Gdx.graphics.getHeight() / 2;
		float mapW = tileMapRenderer.getMapWidthUnits();
		float mapH = tileMapRenderer.getMapHeightUnits();

		if (x < halfW)
			x = halfW;
		else if (x > mapW - halfW) {
			x = mapW - halfW;
		}

		if (y < halfH) {
			y = halfH;
		} else if (y > mapH - halfH) {
			y = mapH - halfH;
		}

		camera.position.set(x, y, 0);
		camera.update();
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
		
		texture = new Texture(Gdx.files.internal("data/ghost.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		/*
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);*/
		
		stage = new Stage(w, h, false, batch);
		stage.setCamera(camera);
		player = new Player(texture);
		player.x = 32;
		player.y = 32;
		
		stage.addActor(player);
		
		map = TiledLoader.createMap(Gdx.files.internal("data/maps/test2.tmx"));
		tileAtlas = new TileAtlas(map, Gdx.files.internal("data/maps/"));
		tileMapRenderer = new TileMapRenderer(map, tileAtlas, 30, 30);
		
		pathfinder = new Pathfinder(new MapPathfinderStrategy(map));
		
		/*Player pp = new Player(texture);
		Path.Point p = path.points.getLast();
		pp.x = p.x * map.tileWidth;
		pp.y = (map.height - p.y - 1) * map.tileHeight;
		stage.addActor(pp);*/
		
		uiStage = new Stage(w, h, false);
		
		NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("data/dialogue_box.png")), 16, 16, 16, 16);
		
		TextBox.TextBoxStyle textBoxStyle = new TextBox.TextBoxStyle();
		textBoxStyle.background = patch;
		textBoxStyle.font = new BitmapFont();
		textBoxStyle.padX = 8;
		textBoxStyle.padY = 4;
		
		String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
				"Donec a diam lectus. Sed sit amet ipsum mauris. " +
				"Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. " +
				"Donec et mollis dolor. Praesent et diam eget libero egestas mattis sit amet vitae augue. " +
				"Nam tincidunt congue enim, ut porta lorem lacinia consectetur. " +
				"Donec ut libero sed arcu vehicula ultricies a non tortor. " +
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
				
		TextBox textBox = new TextBox(text, textBoxStyle);
		textBox.width = Gdx.graphics.getWidth();
		textBox.height = Gdx.graphics.getHeight() / 4;
		
		//uiStage.addActor(textBox);
		
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
		tileMapRenderer.dispose();
		tileAtlas.dispose();
		stage.dispose();
		uiStage.dispose();
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		Vector2 pos = new Vector2();
		stage.toStageCoordinates(x, y, pos);
		int endX = (int) pos.x / map.tileWidth;
		int endY = map.height - 1 - (int) pos.y / map.tileHeight;
		int startX = (int) player.x / map.tileWidth;
		int startY = map.height - (int) player.y / map.tileHeight - 1;
		
		Path path = pathfinder.searchPath(startX, startY, endX, endY);
		if(path != null)
			player.action(FollowPath.$(path,  map, (path.points.size() * map.tileWidth) / playerSpeed));

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.BACK:
		case Input.Keys.ESCAPE:
			game.setScreen(game.mainMenuScreen);
			return true;
		}

		return false;
	}
}
