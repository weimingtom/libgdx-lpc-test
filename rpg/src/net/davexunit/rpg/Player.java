package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends MapActor {

	private Tileset tileset;
	private Animation anim;
	private float animTime;
	
	public Player(Texture texture) {
		super();
		tileset = new Tileset(texture, 40, 46, 0, 0);
		anim = new Animation(0.15f, tileset.getTileRange(tileset.coordToIndex(0, 2), 3));
		anim.setPlayMode(Animation.LOOP_PINGPONG);
		animTime = 0.0f;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		animTime += Gdx.graphics.getDeltaTime();
		batch.draw(anim.getKeyFrame(animTime), getX(), getY());
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
}
