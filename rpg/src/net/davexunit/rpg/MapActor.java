package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class MapActor extends Actor {
	public static final int groupPlayer = 1;
	public static final int groupNPC = 2;
	
	private Map map;
	private int tileX;
	private int tileY;
	private int collisionGroup;
	private int group;
	
	public MapActor() {
		super();
		
		this.tileX = -1;
		this.tileY = -1;
		this.collisionGroup = 0;
		this.group = 0;
	}

	public int getCollisionGroup() {
		return collisionGroup;
	}

	public void setCollisionGroup(int collision) {
		this.collisionGroup = collision;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
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
	
	public boolean move(int tileX, int tileY) {
		if(map == null)
			return false;
		
		return map.moveActor(this, tileX, tileY);
	}
	
	public boolean warp(int tileX, int tileY) {
		if(map == null)
			return false;
		
		return map.warpActor(this, tileX, tileY);
	}
}
