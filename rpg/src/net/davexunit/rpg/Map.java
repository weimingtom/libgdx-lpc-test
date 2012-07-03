package net.davexunit.rpg;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Map {
	private Stage stage;
	private TiledMap map;
	private TileAtlas tileAtlas;
	private TileMapRenderer renderer;
	private OrthographicCamera camera;
	private MapActorLayer actors;
	private int[] underLayers;
	private int[] overLayers;
	
	public Map(FileHandle mapFile, FileHandle atlasDir, float viewportWidth, float viewportHeight) {
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		stage = new Stage(viewportWidth, viewportHeight, false);
		stage.setCamera(camera);
		map = TiledLoader.createMap(mapFile);
		tileAtlas = new TileAtlas(map, atlasDir);
		renderer = new TileMapRenderer(map, tileAtlas, map.width, map.height);
		actors = new MapActorLayer(map.width, map.height);
	}
	
	public void draw() {
		if(underLayers == null || overLayers == null) {
			renderer.render();
			stage.draw();
		} else {
			renderer.render(camera, underLayers);
			stage.draw();
			renderer.render(camera, overLayers);
		}
	}
	
	public void dispose() {
		renderer.dispose();
		tileAtlas.dispose();
		stage.dispose();
	}
	
	public void act(float delta) {
		stage.act(delta);
	}
	
	public boolean addActor(MapActor actor) {
		if(actor.getMap() != null)
			return false;
		
		if(actors.add(actor)) {
			stage.addActor(actor);
			actor.setMap(this);
			
			return true;
		}
		
		return false;
	}
	
	public boolean isActorOpen(int x, int y) {
		return actors.isOpen(x, y);
	}
	
	public MapActor getActor(int x, int y) {
		return actors.get(x, y);
	}
	
	public MapActor remove(int x, int y) {
		MapActor actor = actors.remove(x, y);
		
		actor.setMap(null);
		
		return actor;
	}
	
	public void clearActors() {
		actors.clear();
	}
	
	public int getWidth() {
		return map.width;
	}
	
	public int getHeight() {
		return map.height;
	}
	
	public int getTileWidth() {
		return map.tileWidth;
	}
	
	public int getTileHeight() {
		return map.tileHeight;
	}
	
	public int getMapWidthUnits() {
		return renderer.getMapWidthUnits();
	}
	
	public int getMapHeightUnits() {
		return renderer.getMapHeightUnits();
	}
	
	public int[] getUnderLayers() {
		return underLayers;
	}

	public void setUnderLayers(int[] underLayers) {
		this.underLayers = underLayers;
	}

	public int[] getOverLayers() {
		return overLayers;
	}

	public void setOverLayers(int[] overLayers) {
		this.overLayers = overLayers;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
		this.stage.setCamera(camera);
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public void toMapCoordinates(int x, int y, Vector2 pos) {
		stage.toStageCoordinates(x, y, pos);
	}
}
