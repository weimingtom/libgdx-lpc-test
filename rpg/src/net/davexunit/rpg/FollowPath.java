package net.davexunit.rpg;

import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.AnimationAction;
import com.badlogic.gdx.scenes.scene2d.actions.ActionResetingPool;

public class FollowPath extends AnimationAction {
	private static final ActionResetingPool<FollowPath> pool = new ActionResetingPool<FollowPath>(4, 100) {
		@Override
		protected FollowPath newObject() {
			return new FollowPath();
		}
	};
	
	protected Path path;
	protected TiledMap map;

	public static FollowPath $(Path path, TiledMap map, float duration) {
		FollowPath action = pool.obtain();
		action.duration = duration;
		action.invDuration = 1 / duration;
		action.path = path;
		action.map = map;
		return action;
	}
	
	@Override
	public void setTarget(Actor actor) {
		this.target = actor;
		this.taken = 0;
		this.done = false;
	}

	@Override
	public void act(float delta) {
		float alpha = createInterpolatedAlpha(delta);
		int height = map.height * map.tileHeight;
		
		if (done) {
			Path.Point p = path.points.get(path.points.size() - 1);
			target.x = p.x * map.tileWidth;
			target.y = height - p.y * map.tileHeight - map.tileHeight;
			
			if(target instanceof MapActor) {
				((MapActor)target).setTilePos(p.x, p.y);
			}
		} else {
			int index = (int) Math.floor(alpha * path.points.size());
			
			if(index == path.points.size() - 1)
				return;
			
			// Calculate the percentage (from 0.0 to 1.0) of tile that has been walked upon.
			float tileStartAlpha =  (float) index / path.points.size();
			float perTileAlpha = (float) 1 / path.points.size();
			float tileDelta = (alpha - tileStartAlpha) / perTileAlpha;
			
			Path.Point current = path.points.get(index);
			Path.Point next = path.points.get(index + 1);
			
			// Calculate tile coordinates using the next tile in the path to determine direction of movement.
			float x = current.x + (next.x - current.x) * tileDelta;
			float y = current.y + (next.y - current.y) * tileDelta;
			
			target.x =  x * map.tileWidth;
			target.y = height - y * map.tileHeight - map.tileHeight;
			
			if(target instanceof MapActor) {
				((MapActor)target).setTilePos(next.x, next.y);
			}
		}
	}

	@Override
	public Action copy() {
		FollowPath followPath = $(path, map, duration);
		if (interpolator != null) followPath.setInterpolator(interpolator.copy());
		return followPath;
	}

}
