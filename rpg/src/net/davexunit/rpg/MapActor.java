package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class MapActor extends Actor {
	private Map map;
	private int tileX;
	private int tileY;
	
	public MapActor() {
		super();
		
		this.tileX = 0;
		this.tileY = 0;
	}
	
	public MapActor(String name) {
		super(name);
		
		this.tileX = 0;
		this.tileY = 0;
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public int getTileX() {
		return tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
	
	public void setTileX(int tileX) {
		this.tileX = tileX;
	}
	
	public void setTileY(int tileY) {
		this.tileY = tileY;
	}
	
	public void setTilePos(int tileX, int tileY) {
		this.tileX = tileX;
		this.tileY = tileY;
	}
	
	public void warp(int tileX, int tileY) {
		if(map == null)
			return;
		
		if(map.isActorOpen(tileX, tileY)) {
			setTilePos(tileX, tileY);
			x = tileX * map.getTileWidth();
			y = tileY * map.getTileHeight();
		}
	}
}
