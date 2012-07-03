package net.davexunit.rpg;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationManager {
	private HashMap<String, Animation> animations;
	
	public AnimationManager() {
		super();
		
		animations = new HashMap<String, Animation>();
	}
	
	public Animation getAnimation(String name) {
		return animations.get(name);
	}
	
	public void setAnimation(String name, Animation animation) {
		animations.put(name, animation);
	}
}
