package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction;

public class MapMoveByAction extends RelativeTemporalAction {
	public static final int dirUp = 1;
	public static final int dirDown = 2;
	public static final int dirLeft = 3;
	public static final int dirRight = 4;
	
	private int direction;
	private int tileX, tileY;
	
	@Override
	protected void updateRelative(float percent) {
		
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public void setActor(Actor actor) {
		// TODO Auto-generated method stub
		super.setActor(actor);
	}
}
