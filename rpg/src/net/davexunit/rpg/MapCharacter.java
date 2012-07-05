package net.davexunit.rpg;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapCharacter extends MapActor {
	public static final int dirUp = 1;
	public static final int dirDown = 2;
	public static final int dirLeft = 3;
	public static final int dirRight = 4;
	
	final HashMap<String, Animation> animations;
	Animation currentAnimation;
	float animTime;
	int direction;
	boolean walking;
	
	public MapCharacter(HashMap<String, Animation> animations) {
		this.animations = new HashMap<String, Animation>(animations);
		this.direction = dirDown;
		this.walking = false;
		updateAnimation();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		animTime += Gdx.graphics.getDeltaTime();
		
		if(currentAnimation != null)
			batch.draw(currentAnimation.getKeyFrame(animTime), getX(), getY());
	}
	
	public HashMap<String, Animation> getAnimations() {
		return animations;
	}
	
	private void setCurrentAnim(String name) {
		currentAnimation = animations.get(name);
		animTime = 0;
	}
	
	protected TextureRegion getKeyFrame() {
		if(currentAnimation == null)
			return null;
		
		return currentAnimation.getKeyFrame(animTime);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		if(this.direction != direction) {
			this.direction = direction;
			updateAnimation();
		}
	}

	public boolean isWalking() {
		return walking;
	}

	public void setWalking(boolean walking) {
		if(this.walking != walking) {
			this.walking = walking;
			updateAnimation();
		}
	}
	
	private void updateAnimation() {
		String animPrefix = "stand";
		
		if(walking)
			animPrefix = "walk";
		
		switch(direction) {
			case dirUp:
				setCurrentAnim(animPrefix + "_up");
				break;
				
			case dirDown:
				setCurrentAnim(animPrefix + "_down");
				break;
				
			case dirLeft:
				setCurrentAnim(animPrefix + "_left");
				break;
				
			case dirRight:
				setCurrentAnim(animPrefix + "_right");
				break;
		}
	}
}