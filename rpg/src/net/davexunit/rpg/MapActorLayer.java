package net.davexunit.rpg;

public class MapActorLayer {
	private MapActor[][] actors;
	private int width;
	private int height;
	
	public MapActorLayer(int width, int height) {
		this.width = width;
		this.height = height;
		this.actors = new MapActor[height][width];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isOpen(int x, int y) {
		return actors[y][x] == null;
	}
	
	public MapActor get(int x, int y) {
		return actors[y][x];
	}
	
	public boolean add(MapActor actor) {
		int x = actor.getTileX();
		int y = actor.getTileY();
		
		if(isOpen(x, y)) {
			actors[y][x] = actor;
			return true;
		}
		
		return false;
	}
	
	public MapActor remove(int x, int y) {
		MapActor actor = actors[y][x];
		
		actor.setMap(null);
		actors[y][x] = null;
		
		return actor;
	}
	
	public void clear() {
		for(int y = 0; y < height; ++y) {
			for(int x = 0; x < width; ++x) {
				actors[y][x] = null;
			}
		}
	}
}
