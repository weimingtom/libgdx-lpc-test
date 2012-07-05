package net.davexunit.rpg;

import java.util.LinkedList;

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
	
	public boolean checkCollision(MapActor actor, int x, int y) {
		return checkMapCollision(x, y) && checkActorCollision(actor, x, y);
	}
	
	public boolean checkMapCollision(int x, int y) {
		int tile = map.layers.get(5).tiles[y][x];
		String property = map.getTileProperty(tile, "collidable");
		boolean collidable = false;
		
		if(property != null)
			collidable = property.equals("true") ? true: false;
		
		return collidable;
	}
	
	public boolean checkActorCollision(MapActor actor, int x, int y) {
		return actors.checkCollision(actor, x, y);
	}
	
	public LinkedList<MapActor> getActor(int x, int y) {
		return actors.get(x, y);
	}
	
	public void removeActor(MapActor actor) {
		actors.remove(actor);
		actor.remove();
		actor.setMap(null);
	}
	
	public void clearActors() {
		actors.clear();
	}
	
	public void warpActor(MapActor actor, int tileX, int tileY) {
		if(actor.getMap() != this)
			return;
		
		if(!checkCollision(actor, tileX, tileY)) {
			actor.setTilePos(tileX, tileY);
		} else {
			// TODO: Fire collision event
		}
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
	
	public void screenToMapCoordinates(Vector2 pos) {
		stage.screenToStageCoordinates(pos);
	}
}
