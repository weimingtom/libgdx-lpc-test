package net.davexunit.rpg;

import java.util.LinkedList;

public class MapActorLayer {
	private LinkedList<MapActor> actors;
	private int width;
	private int height;
	
	public MapActorLayer(int width, int height) {
		this.width = width;
		this.height = height;
		this.actors = new LinkedList<MapActor>();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean checkCollision(MapActor actor, int x, int y) {
		for(MapActor other: actors) {
			if(actor == other)
				continue;
			
			if(x == other.getTileX() &&
			   y == other.getTileY() && 
			   (actor.getGroup() & other.getCollisionGroup()) >= 1) {
				return true;
			}
		}
		
		return false;
	}
	
	public LinkedList<MapActor> get(int x, int y) {
		LinkedList<MapActor> list = new LinkedList<MapActor>();
		
		for(MapActor actor: actors) {
			if(actor.getTileX() == x && actor.getTileY() == y)
				list.add(actor);
		}
		
		return list;
	}
	
	public boolean add(MapActor actor) {
		// Don't add duplicates
		if(actors.contains(actor))
			return false;
		
		actors.add(actor);
		
		return true;
	}
	
	public void remove(MapActor actor) {
		actors.remove(actor);
	}
	
	public void clear() {
		actors.clear();
	}
}
