package net.davexunit.rpg;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Chest extends MapActor {
	public static final int stateClosed = 1;
	public static final int stateOpen = 2;
	
	private Animation idleAnimation;
	private Animation openAnimation;
	private Item item;
	private int animTime;
	private int state;
	
	public Chest() {
		super();
		
		this.idleAnimation = null;
		this.openAnimation = null;
		this.item = null;
		this.animTime = 0;
		this.state = stateClosed;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		Animation anim = null;
		
		switch(state) {
		case stateClosed:
			anim = idleAnimation;
			break;
			
		case stateOpen:
			anim = openAnimation;
			break;
		}
		
		batch.draw(anim.getKeyFrame(animTime), getX(), getY());
	}

	public Animation getIdleAnimation() {
		return idleAnimation;
	}

	public void setIdleAnimation(Animation idleAnimation) {
		this.idleAnimation = idleAnimation;
	}

	public Animation getOpenAnimation() {
		return openAnimation;
	}

	public void setOpenAnimation(Animation openAnimation) {
		this.openAnimation = openAnimation;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		this.animTime = 0;
	}
}
