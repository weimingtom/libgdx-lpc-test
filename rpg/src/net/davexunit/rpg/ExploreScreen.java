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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import static net.davexunit.rpg.MapActions.*;

public class ExploreScreen extends InputAdapter implements Screen {
	private RPG game;
	private TextureAtlas atlas;
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
		character.setGroup(MapActor.groupPlayer);
		character.setCollisionGroup(MapActor.groupNPC);
		
		return character;
	}

	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		atlas = game.manager.get("data/sprites/spritepack.atlas", TextureAtlas.class);
		texture = atlas.findRegion("ghost");
		
		tileset = new Tileset(texture, 40, 46, 0, 0);
		player = makeCharacter();
		
		Door door = new Door();
		door.setMapFile("data/maps/test3.tmx");
		door.setTilePos(9, 15);
		
		map = game.getState().loadMap("Test");
		map.setUnderLayers(underLayers);
		map.setOverLayers(overLayers);
		map.addActor(player);
		map.addActor(door);
		map.setMapListener(new MapListener() {
			@Override
			public void collided(MapActor actor1, MapActor actor2) {
				//System.out.println("Collision!");
			}

			@Override
			public void overlapped(MapActor actor1, MapActor actor2) {
				if(actor1 == player && actor2 instanceof Door) {
					float w = Gdx.graphics.getWidth();
					float h = Gdx.graphics.getHeight();
					Door door = (Door) actor2;
					map.dispose();
					map = new Map(Gdx.files.internal(door.getMapFile()), Gdx.files.internal("data/maps"), w, h);
					map.setUnderLayers(underLayers);
					map.setOverLayers(overLayers);
					player = makeCharacter();
					map.addActor(player);
					player.warp(0, 29);
					pathfinder = new Pathfinder(new MapPathfinderStrategy(map));
					followPathAction = null;
					pathSequence = sequence();
				}
			}
		});
		
		player.warp(9, 16);
		
		pathfinder = new Pathfinder(new MapPathfinderStrategy(map));
		
		// testing a bunch of actors!
		for(int i = 0; i < 30; ++i) {
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
		
		NinePatch patch = atlas.createPatch("dialog-box");
		
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
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
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
