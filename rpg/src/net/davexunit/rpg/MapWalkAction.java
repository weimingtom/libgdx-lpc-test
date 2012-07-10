package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.Action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MapWalkAction extends Action {
	public static final int dirUp = 1;
	public static final int dirDown = 2;
	public static final int dirLeft = 3;
	public static final int dirRight = 4;
	
	private int direction;
	private float speed;
	private float time, moveTime;
	private float duration;
	private MapActor mapActor;
	
	public MapWalkAction() {
		super();
		
		this.direction = 0;
		this.speed = 5;
		this.time = 0;
		this.moveTime = 0;
		this.duration = 0;
		this.mapActor = null;
	}

	@Override
	public boolean act(float delta) {
		if(time == 0)
			initialize();
		
		time += delta;
		
		if(moveTime == 0) {
			int oldTileX = mapActor.getTileX();
			int oldTileY = mapActor.getTileY();
			int tileX = oldTileX;
			int tileY = oldTileY;
			
			switch(direction) {
			case dirUp:
				tileY -= 1;
				break;
				
			case dirDown:
				tileY += 1;
				break;
			
			case dirLeft:
				tileX -= 1;
				break;
				
			case dirRight:
				tileX += 1;
				break;
			}
			
			int tileWidth = mapActor.getMap().getTileWidth();
			int tileHeight = mapActor.getMap().getTileHeight();
			
			if(mapActor.move(tileX, tileY)) {
				mapActor.addAction(moveBy((tileX - oldTileX) * tileWidth, (oldTileY - tileY) * tileHeight, duration));
			}
		}
		
		if(direction != 0 || moveTime != 0)
			moveTime += delta;
		
		if(moveTime >= duration) {
			moveTime = 0;
		}
		
		return false;
	}
	
	protected void initialize() {
		duration = 1.0f / speed;
		moveTime = 0;
		mapActor = (MapActor) actor;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
