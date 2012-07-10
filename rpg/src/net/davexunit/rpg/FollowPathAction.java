package net.davexunit.rpg;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Action;

public class FollowPathAction extends Action {
	private Pathfinder pathfinder;
	private Path path;
	private MapActor mapActor;
	private int index;
	private final LinkedList<Path.Point> ignore;
	private float time, moveTime;
	private float speed;
	private float duration;
	
	public FollowPathAction() {
		this.ignore = new LinkedList<Path.Point>();
	}
	
	@Override
	public void setActor(Actor actor) {
		super.setActor(actor);
		mapActor = (MapActor) actor;
	}

	@Override
	public boolean act(float delta) {
		if(time == 0)
			initialize();
		
		time += delta;
		
		if(moveTime == 0) {
			nextTile();
		}
		
		moveTime += delta;
		
		if(moveTime >= duration) {
			moveTime = 0;
		}
		
		return false;
	}

	protected void initialize() {
		moveTime = 0;
		duration = 1 / speed;
		
		if(path != null) {
			Path.Point p = path.points.get(0);
			
			mapActor.warp(p.x, p.y);
		}
	}
	
	private void nextTile() {
		index += 1;
		
		if(path == null)
			return;
		
		if(index >= path.points.size()) {
			setPath(null);
			return;
		}
		
		Path.Point next = path.points.get(index);
		
		mapActor.moved(next.x, next.y);
		
		if(mapActor.move(next.x, next.y)) {
			Map map = mapActor.getMap();
			
			if(map != null) {
				int tileWidth = mapActor.getMap().getTileWidth();
				int tileHeight = mapActor.getMap().getTileHeight();
				int height = mapActor.getMap().getMapHeightUnits();
				mapActor.addAction(moveTo(next.x * tileWidth, height - next.y * tileHeight - tileHeight, duration));
			}
		} else {
			ignore.add(next);
			findNewPath();
		}
	}
	
	private void findNewPath() {
		Path.Point current = path.points.get(index - 1);
		Path.Point end = path.points.get(path.points.size() - 1);
		Path newPath = null;
		if(pathfinder != null)
			newPath = pathfinder.searchPath(current.x, current.y, end.x, end.y, ignore);
		setPath(newPath);
	}

	@Override
	public void reset() {
		super.reset();
		
		path = null;
		index = 0;
		ignore.clear();
	}
	
	@Override
	public void restart() {
		super.restart();
		
		index = 0;
		time = 0;
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(Pathfinder pathfinder) {
		this.pathfinder = pathfinder;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
		restart();
	}

	public int getIndex() {
		return index;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
