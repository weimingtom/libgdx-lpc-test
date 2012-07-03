package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapCharacter extends MapActor {
	AnimationManager animations;
	Animation currentAnimation;
	float animTime;
	
	public MapCharacter() {
		animations = new AnimationManager();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		animTime += Gdx.graphics.getDeltaTime();
		
		if(currentAnimation != null)
			batch.draw(currentAnimation.getKeyFrame(animTime), getX(), getY());
	}
	
	public AnimationManager getAnimations() {
		return animations;
	}
	
	public void setAnimations(AnimationManager animations) {
		this.animations = animations;
	}
	
	protected TextureRegion getKeyFrame() {
		if(currentAnimation == null)
			return null;
		
		return currentAnimation.getKeyFrame(animTime);
	}
}
