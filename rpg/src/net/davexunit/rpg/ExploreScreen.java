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
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import static net.davexunit.rpg.MapActions.*;

public class ExploreScreen extends InputAdapter implements Screen {
	public static final int stateWalk = 0;
	public static final int stateDialog = 1;
	
	private RPG game;
	private int state;
	private TextureAtlas atlas;
	private TextureRegion texture;
	private Tileset tileset;
	private Stage uiStage;
	private MapCharacter player;
	private Map map;
	private Pathfinder pathfinder;
	private final int[] underLayers = { 0, 1, 2 };
	private final int[] overLayers = { 3, 4 };
	private final float playerSpeed = 6.5f; // tiles per second
	private TextBox dialog;
	private FPSLogger fps;
	private FollowPathAction followPathAction;
	private Random random;
	private MapWalkAction walkAction;
	private StyledTable.TableStyle textBoxStyle;
	private boolean mMenuActive;
	
	public ExploreScreen(RPG game) {
		this.game = game;
		this.fps = new FPSLogger();
		this.followPathAction = null;
		this.random = new Random();
		this.walkAction = null;
		this.mMenuActive = false;
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
	
	public Chest makeChest() {
		Chest chest = new Chest();
		
		Tileset tileset = new Tileset(atlas.findRegion("chests"), 32, 32, 0, 0);
		Gdx.app.log("chest", "" + atlas.findRegion("chests").getRegionWidth() + ", " + tileset.getWidth());
		
		Animation animClosed = new Animation(0.15f, tileset.getTile(1));
		animClosed.setPlayMode(Animation.NORMAL);
		
		Animation animOpen = new Animation(0.15f, tileset.getTile(3));
		animOpen.setPlayMode(Animation.NORMAL);
		
		chest.animations.put("closed", animClosed);
		chest.animations.put("open", animOpen);
		chest.setGroup(MapActor.groupNPC);
		chest.setCollisionGroup(MapActor.groupPlayer | MapActor.groupNPC);
		chest.setState(Chest.stateClosed);
		
		return chest;
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
		
		Sign sign = new Sign();
		sign.setMapCollidable(false);
		sign.setText("Hello, world!");
		
		Chest chest = makeChest();
		
		map = game.getState().loadMap("Test");
		map.setUnderLayers(underLayers);
		map.setOverLayers(overLayers);
		map.addActor(player);
		map.addActor(door);
		map.addActor(sign);
		map.addActor(chest);
		map.setMapListener(new MapListener() {
			@Override
			public void collided(MapActor actor1, MapActor actor2) {
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
					walkAction = mapWalk(0, playerSpeed);
					followPathAction = followPath(pathfinder, null, playerSpeed);
					player.addAction(walkAction);
					player.addAction(followPathAction);
				}
			}
		});

		chest.warp(6, 15);
		sign.warp(8, 15);
		door.warp(9, 15);
		player.warp(9, 16);
		
		pathfinder = new Pathfinder(new MapPathfinderStrategy(map));

		walkAction = mapWalk(0, playerSpeed);
		followPathAction = followPath(pathfinder, null, playerSpeed);
		player.addAction(walkAction);
		player.addAction(followPathAction);
		
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
				npc.addAction(followPath(pathfinder, path, playerSpeed));
			}
		}
		
		uiStage = new Stage(w, h, false);
		
		NinePatch patch = atlas.createPatch("dialog-box");
		
		textBoxStyle = new StyledTable.TableStyle();
		textBoxStyle.background = new NinePatchDrawable(patch);
		textBoxStyle.font = new BitmapFont();
		textBoxStyle.padX = 8;
		textBoxStyle.padY = 4;
				
		dialog = new TextBox("", textBoxStyle);
		dialog.setWidth(Gdx.graphics.getWidth());
		dialog.setHeight(Gdx.graphics.getHeight() / 4);
		dialog.setVisible(false);
		
		uiStage.addActor(dialog);
		
		String [] options = {"Option1", "Option2", "Option3"};
		textBoxStyle.padY = 0;
		Menu menu = new Menu(options, textBoxStyle);
		menu.setWidth(w / 4);
		menu.setHeight(h / 2);
		menu.setPosition(w - w / 4, h/ 3);
		
		//uiStage.addActor(menu);
		
		Gdx.input.setInputProcessor(this);
	}
	
	private void interact() {
		MapActor actor = null;
		int tileX = player.getTileX();
		int tileY = player.getTileY();
		
		switch(player.getDirection()) {
		case MapCharacter.dirUp:
			actor = map.getFirstActor(tileX, tileY - 1);
			break;

		case MapCharacter.dirDown:
			actor = map.getFirstActor(tileX, tileY + 1);
			break;

		case MapCharacter.dirLeft:
			actor = map.getFirstActor(tileX - 1, tileY);
			break;

		case MapCharacter.dirRight:
			actor = map.getFirstActor(tileX + 1, tileY);
			break;
		}
		
		if(actor == null)
			return;
		
		if(actor instanceof Sign) {
			Sign sign = (Sign) actor;
			
			state = stateDialog;
			
			dialog.setText(sign.getText());
			dialog.setVisible(true);
		} else if(actor instanceof Chest) {
			Chest chest = (Chest) actor;
			
			chest.setState(Chest.stateOpen);
		}
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
		if(state == stateWalk) {
			Vector2 pos = new Vector2();
			pos.set(x, y);
			map.screenToMapCoordinates(pos);
			int tileWidth = map.getTileWidth();
			int tileHeight = map.getTileHeight();
			int height = map.getHeight();
			int endX = (int) pos.x / tileWidth;
			int endY = height - (int) pos.y / tileHeight - 1;
			
			Path path = pathfinder.searchPath(player.getTileX(), player.getTileY(), endX, endY, null);
			
			if(path != null) {
				followPathAction.setPath(path);
			}
		}
			
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		if(state == stateWalk) {
			switch(keycode) {
			case Input.Keys.DPAD_UP:
			case Input.Keys.DPAD_DOWN:
			case Input.Keys.DPAD_LEFT:
			case Input.Keys.DPAD_RIGHT:
				updateMovement();
				break;
				
			case Input.Keys.Z:
				interact();
				break;
			}
		} else if(state == stateDialog) {
			switch(keycode) {
			case Input.Keys.Z:
				state = stateWalk;
				dialog.setVisible(false);
			}
		}

		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(state == stateWalk) {
			switch (keycode) {
			case Input.Keys.BACK:
			case Input.Keys.ESCAPE:
				game.setScreen(game.mainMenuScreen);
				return true;
			
			case Input.Keys.DPAD_UP:
			case Input.Keys.DPAD_DOWN:
			case Input.Keys.DPAD_LEFT:
			case Input.Keys.DPAD_RIGHT:
				updateMovement();
				break;
				
			case Input.Keys.B:
				game.setScreen(game.battleScreen);
				return true;
				
			case Input.Keys.M:
				mMenuActive = ! mMenuActive;
				return false;
			}
		}

		return false;
	}
	
	private void updateMovement() {
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
			walkAction.setDirection(MapWalkAction.dirUp);
		} else if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
			walkAction.setDirection(MapWalkAction.dirDown);
		} else if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
			walkAction.setDirection(MapWalkAction.dirLeft);
		} else if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
			walkAction.setDirection(MapWalkAction.dirRight);
		} else {
			walkAction.setDirection(0);
		}
	}
}
