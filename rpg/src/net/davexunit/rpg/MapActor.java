package net.davexunit.rpg;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class MapActor extends Actor {
	public final int collidePlayer = 1;
	public final int collideNPC = 2;
	
	private Map map;
	private int tileX;
	private int tileY;
	private float offsetX;
	private float offsetY;
	private int collision;
	
	public MapActor() {
		super();
		
		this.tileX = 0;
		this.tileY = 0;
		this.offsetX = 0;
		this.offsetY = 0;
		this.collision = 0;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(map != null) {
			float x = (tileX + offsetX) * map.getTileWidth();
			float y = map.getMapHeightUnits() - (tileY + offsetY) * map.getTileHeight() - map.getTileHeight();
			setPosition(x, y);
		}
	}

	public int getCollision() {
		return collision;
	}

	public void setCollision(int collision) {
		this.collision = collision;
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

	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public void setOffset(float offsetX, float offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void warp(int tileX, int tileY) {
		if(map == null)
			return;
		
		if(this.tileX == tileX && this.tileY == tileY)
			return;
		
		map.warpActor(this, tileX, tileY);
	}
	
}
