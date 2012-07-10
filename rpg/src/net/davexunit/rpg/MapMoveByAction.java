package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class MapMoveByAction extends TemporalAction {
	private int startX, startY;
	private float amountX, amountY;
	private MapActor mapActor;
	
	public MapMoveByAction() {
		super();
		
		mapActor = null;
	}

	@Override
	protected void initialize() {
		if(!(actor instanceof MapActor))
			return;
		
		mapActor = (MapActor) actor;
		
		startX = mapActor.getTileX();
		startY = mapActor.getTileY();
	}

	@Override
	protected void update(float percent) {
		if(mapActor == null)
			return;
		
		float px = amountX * percent;
		float py = amountY * percent;
		int tileX = startX + (int) px;
		int tileY = startY + (int) py;
		float offsetX = -px;
		float offsetY = -py;
		
		mapActor.move(tileX, tileY);
	}
	
	public float getAmountX() {
		return amountX;
	}

	public void setAmountX(float amountX) {
		this.amountX = amountX;
	}

	public float getAmountY() {
		return amountY;
	}

	public void setAmountY(float amountY) {
		this.amountY = amountY;
	}
	
	public void setAmount(float amountX, float amountY) {
		this.amountX = amountX;
		this.amountY = amountY;
	}
}
