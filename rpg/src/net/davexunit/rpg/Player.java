package net.davexunit.rpg;

import java.util.HashMap;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {

	private Tileset tileset;
	private Animation anim;
	private float animTime;
	private Stats mStats;
	private ArrayList<Action> mActions;
	//backup coordinates when in battle and vice versa
	private float _x;
	private float _y;
	
	public Player(Texture texture) {
		tileset = new Tileset(texture, 40, 46, 0, 0);
		anim = new Animation(0.15f, tileset.getTileRange(tileset.coordToIndex(0, 2), 3));
		anim.setPlayMode(Animation.LOOP_PINGPONG);
		animTime = 0.0f;
		mActions = new ArrayList<Action>();
		width = tileset.getTileWidth();
		height = tileset.getTileHeight();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		animTime += Gdx.graphics.getDeltaTime();
		batch.draw(anim.getKeyFrame(animTime), x, y);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getStat(String name) {
		return mStats.getStat(name);
	}
	
	public int turnCost() {
		return mStats.getMaxStat(name) - mStats.getStat(name);
	}
	
	public boolean isAlive() {
		return mStats.getStat("HP") > 0;
	}
	
	public void executeAction(String action, Player target){
	}
	
	public void executeAction(int pos, Player target) {
		if (pos >= mActions.size())
			return;
		
		Action a = mActions.get(pos);
		a.execute();
	}
	
	public void setInBattle(boolean inBattle) {
		if (inBattle) {
			this._x = this.x;
			this._y = this.y;
		}
		else {
			this.x = this._x;
			this.y = this._y;
		}
	}
}
