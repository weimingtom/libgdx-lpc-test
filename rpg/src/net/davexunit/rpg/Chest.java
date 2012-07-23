package net.davexunit.rpg;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Chest extends MapActor {
	public static final int stateClosed = 1;
	public static final int stateOpen = 2;
	
	public final HashMap<String, Animation> animations;
	public Animation currentAnimation;
	private int animTime;
	private Item item;
	private int quantity;
	private int state;
	
	public Chest() {
		super();
		
		this.animations = new HashMap<String, Animation>();
		this.currentAnimation = null;
		this.item = null;
		this.animTime = 0;
		this.state = 0;
		this.quantity = 1;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(currentAnimation != null)
			batch.draw(currentAnimation.getKeyFrame(animTime), getX(), getY());
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
		
		switch(state) {
		case stateClosed:
			currentAnimation = animations.get("closed");
			break;
			
		case stateOpen:
			currentAnimation = animations.get("open");
			break;
		}
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
