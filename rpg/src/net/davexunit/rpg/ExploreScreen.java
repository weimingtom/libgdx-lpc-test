package net.davexunit.rpg;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import static net.davexunit.rpg.MapActions.*;

public class ExploreScreen extends InputAdapter implements Screen {
	private RPG game;
	private TextureRegion texture;
	private Tileset tileset;
	private Stage uiStage;
	private MapCharacter player;
	private Map map;
	private Pathfinder pathfinder;
	private final int[] underLayers = { 0, 1, 2 };
	private final int[] overLayers = { 3, 4 };
	private final float playerSpeed = 6; // tiles per second
	private FPSLogger fps;
	private FollowPathAction followPathAction;
	private SequenceAction pathSequence;
	private Random random;
	
	public ExploreScreen(RPG game) {
		this.game = game;
		this.fps = new FPSLogger();
		this.followPathAction = null;
		this.pathSequence = sequence();
		this.random = new Random();
	}

	@Override
	public void render(float delta) {
		map.act(Gdx.graphics.getDeltaTime());
		centerCamera();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		map.draw();
		uiStage.draw();
		fps.log();
	}

	public void centerCamera() {
		float x = player.getX();
		float y = player.getY();
		float halfW = Gdx.graphics.getWidth() / 2;
		float halfH = Gdx.graphics.getHeight() / 2;
		float mapW = map.getMapWidthUnits();
		float mapH = map.getMapHeightUnits();

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

		map.getCamera().position.set(x, y, 0);
		map.getCamera().update();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}
	
	public MapCharacter makeCharacter() {
		Animation animDown = new Animation(0.15f, tileset.getTileRange(tileset.coordToIndex(0, 2), 3));
		animDown.setPlayMode(Animation.LOOP_PINGPONG);
		
		Animation animUp = new Animation(0.15f, tileset.getTileRange(tileset.coordToIndex(0, 0), 3));
		animUp.setPlayMode(Animation.LOOP_PINGPONG);
		
		Animation animLeft = new Animation(0.15f, tileset.getTileRange(tileset.coordToIndex(0, 1), 3));
		animLeft.setPlayMode(Animation.LOOP_PINGPONG);
		
		Animation animRight = new Animation(0.15f, tileset.getTileRange(tileset.coordToIndex(0, 3), 3));
		animRight.setPlayMode(Animation.LOOP_PINGPONG);
		
		HashMap<String, Animation> animations = new HashMap<String, Animation>();
		animations.put("walk_down", animDown);
		animations.put("walk_up", animUp);
		animations.put("walk_left", animLeft);
		animations.put("walk_right", animRight);
		animations.put("stand_down", animDown);
		animations.put("stand_up", animUp);
		animations.put("stand_left", animLeft);
		animations.put("stand_right", animRight);
		
		MapCharacter character = new MapCharacter(animations);
		
		return character;
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		texture = game.atlas.findRegion("ghost");
		
		/*
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);*/
		
		tileset = new Tileset(texture, 40, 46, 0, 0);
		player = makeCharacter();
		player.setGroup(MapActor.groupPlayer);
		player.setCollisionGroup(MapActor.groupNPC);
		
		map = game.getState().loadMap("Test");
		map.setUnderLayers(underLayers);
		map.setOverLayers(overLayers);
		map.addActor(player);
		
		player.warp(0, 29);
		
		pathfinder = new Pathfinder(new MapPathfinderStrategy(map));
		
		// testing a bunch of actors!
		for(int i = 0; i < 50; ++i) {
			MapCharacter npc = makeCharacter();
			npc.setGroup(MapActor.groupNPC);
			npc.setCollisionGroup(MapActor.groupNPC | MapActor.groupPlayer);
			map.addActor(npc);
			
			// Keep attempting warp until it works
			while(!npc.warp(random.nextInt(map.getWidth()), random.nextInt(map.getHeight())));
			
			Path path = pathfinder.searchPath(npc.getTileX(), npc.getTileY(), random.nextInt(map.getWidth()), random.nextInt(map.getHeight()), null);
			
			if(path != null) {
				npc.addAction(followPath(map, pathfinder, path, (float) path.points.size() / playerSpeed));
			}
		}
		
		uiStage = new Stage(w, h, false);
		
		NinePatch patch = new NinePatch(game.atlas.findRegion("dialogue_box"), 32, 16, 32, 16);
		
		TextBox.TextBoxStyle textBoxStyle = new TextBox.TextBoxStyle();
		textBoxStyle.background = new NinePatchDrawable(patch);
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
		textBox.setWidth(Gdx.graphics.getWidth());
		textBox.setHeight(Gdx.graphics.getHeight() / 4);
		
		uiStage.addActor(textBox);
		
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
		map.dispose();
		uiStage.dispose();
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		Vector2 pos = new Vector2();
		pos.set(x, y);
		map.screenToMapCoordinates(pos);
		int tileWidth = map.getTileWidth();
		int tileHeight = map.getTileHeight();
		int height = map.getHeight();
		int endX = (int) pos.x / tileWidth;
		int endY = height - (int) pos.y / tileHeight - 1;
		
		Path path = null;
		
		if(followPathAction != null && followPathAction.getActor() == player) {
			followPathAction.setStopIndex(followPathAction.getIndex() + 2);
			Path.Point p = followPathAction.getPath().points.get(followPathAction.getStopIndex() - 1);
			path = pathfinder.searchPath(p.x, p.y, endX, endY, null);
		} else {
			path = pathfinder.searchPath(player.getTileX(), player.getTileY(), endX, endY, null);
		}
		
		if(path != null) {
			followPathAction = followPath(map, pathfinder, path, (float) path.points.size() / playerSpeed);
			
			if(pathSequence.getActor() == null) {
				pathSequence.addAction(followPathAction);
				player.addAction(pathSequence);
			} else if(pathSequence.getActor() == player) {
				pathSequence.addAction(followPathAction);
			}
		}
			
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
