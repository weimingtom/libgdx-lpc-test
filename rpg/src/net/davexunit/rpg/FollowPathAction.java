package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class FollowPathAction extends TemporalAction {
	protected Path path;
	protected Map map;

	@Override
	protected void initialize() {
		Path.Point p = path.points.get(0);
		
		actor.setPosition(p.x * map.getTileWidth(), map.getMapHeightUnits() - p.y * map.getTileHeight() - map.getTileHeight());
		
		if(actor instanceof MapActor) {
			((MapActor)actor).setTilePos(p.x, p.y);
		}
	}

	@Override
	protected void update(float percent) {
		int index = (int) Math.floor(percent * path.points.size());
		
		if(index == path.points.size()) {
			Path.Point p = path.points.get(path.points.size() - 1);
			actor.setPosition(p.x * map.getTileWidth(), map.getMapHeightUnits() - p.y * map.getTileHeight() - map.getTileHeight());
			
			if(actor instanceof MapActor) {
				((MapActor)actor).setTilePos(p.x, p.y);
			}
			
			return;
		}
		
		// Calculate the percentage (from 0.0 to 1.0) of tile that has been walked upon.
		float tileStartAlpha =  (float) index / path.points.size();
		float perTileAlpha = (float) 1 / path.points.size();
		float tileDelta = (percent - tileStartAlpha) / perTileAlpha;
		
		Path.Point current = path.points.get(index);
		Path.Point next = path.points.get(Math.min(index + 1, path.points.size() - 1));
		
		// Calculate tile coordinates using the next tile in the path to determine direction of movement.
		float x = current.x + (next.x - current.x) * tileDelta;
		float y = current.y + (next.y - current.y) * tileDelta;
		
		actor.setPosition(x * map.getTileWidth(), map.getMapHeightUnits() - y * map.getTileHeight() - map.getTileHeight());
		
		if(actor instanceof MapActor) {
			((MapActor)actor).setTilePos(next.x, next.y);
		}
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	/*public static FollowPath $(Path path, TiledMap map, float duration) {
		FollowPath action = pool.obtain();
		action.duration = duration;
		action.invDuration = 1 / duration;
		action.path = path;
		action.map = map;
		return action;
	}*/
	
	/*@Override
	public void setTarget(Actor actor) {
		this.target = actor;
		this.taken = 0;
		this.done = false;
	}*/

	/*@Override
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
			
			target.setX(x * map.tileWidth);
			target.setY(height - y * map.tileHeight - map.tileHeight);
			
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
	}*/
}
