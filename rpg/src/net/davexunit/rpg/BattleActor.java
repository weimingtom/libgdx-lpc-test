package net.davexunit.rpg;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BattleActor extends Actor {
	public static final int stateIdle = 1;
	public static final int stateAttacking = 2;
	public static final int stateCasting = 3;
	
	public final HashMap<String, Animation> animations;
	private Animation currentAnimation;
	private float animTime;
	private int state;
	private Stats stats;
	
	public BattleActor() {
		this.animations = new HashMap<String, Animation>();
		this.currentAnimation = null;
		this.animTime = 0;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		if(currentAnimation == null)
			return;
		
		batch.draw(currentAnimation.getKeyFrame(animTime), getX(), getY());
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		
		updateAnimations();
	}
	
	private void updateAnimations() {
		animTime = 0;
		
		switch(state) {
		case stateIdle:
			currentAnimation = animations.get("idle");
			break;
		}
	}
	
	public int getStat(String name) {
		return stats.getStat(name);
	}
	
	public int turnCost() {
		return stats.getMaxStat("speed") - stats.getStat("speed");
	}
	
	public boolean isAlive() {
		return stats.getStat("HP") > 0;
	}
	
	public void executeAction(String action, BattleActor target){
	}
	
	public void executeAction(int pos, BattleActor target) {
		/*if (pos >= mActions.size())
			return;
		
		Action a = mActions.get(pos);
		a.execute();*/
	}
	
	public void setInBattle(boolean inBattle) {
		/*if (inBattle) {
			this._x = this.x;
			this._y = this.y;
		}
		else {
			this.x = this._x;
			this.y = this._y;
		}*/
	}
}
