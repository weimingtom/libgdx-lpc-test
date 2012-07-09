package net.davexunit.rpg;

import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class FollowPathAction extends TemporalAction {
	protected Pathfinder pathfinder;
	protected Path path;
	protected Map map;
	protected MapCharacter mapCharacter;
	protected int index, stopIndex;
	protected final LinkedList<Path.Point> ignore;
	
	public FollowPathAction() {
		this.ignore = new LinkedList<Path.Point>();
	}
	
	@Override
	public void setActor(Actor actor) {
		super.setActor(actor);
		mapCharacter = (MapCharacter) actor;
	}

	@Override
	protected void initialize() {
		if(path != null) {
			Path.Point p = path.points.get(0);
			
			mapCharacter.warp(p.x, p.y);
			mapCharacter.setOffset(0, 0);
			mapCharacter.setWalking(true);
			
			if(1 < path.points.size()) {
				Path.Point p2 = path.points.get(1);
				if(map.checkActorCollision(mapCharacter, p2.x, p2.y) != null) {
					//ignore.add(new Path.Point(p2.x, p2.y));
					//findNewPath(new Path.Point(mapCharacter.getTileX(), mapCharacter.getTileY()));
					setStopIndex(1);
				}
			}
		} else
			finish();
	}

	@Override
	protected void update(float percent) {
		if(path != null) {
			int stop = getStopIndex();
			
			index = (int) Math.floor(percent * stop);
			
			if(index == stop) {
				Path.Point p = path.points.get(stop - 1);
				
				mapCharacter.warp(p.x, p.y);
				mapCharacter.setOffset(0, 0);
				mapCharacter.setWalking(false);
			 	
				return;
			}
			
			// Calculate the percentage (from 0.0 to 1.0) of tile that has been walked upon.
			float tileStartAlpha =  (float) index / stop;
			float perTileAlpha = (float) 1 / stop;
			float tileDelta = (percent - tileStartAlpha) / perTileAlpha;
			
			Path.Point current = path.points.get(index);
			Path.Point next = path.points.get(Math.min(index + 1, stop - 1));
			
			// Get differences in position to determine direction.
			float dx = next.x - current.x;
			float dy = next.y - current.y;
	
			// Recalculate path if next tile is blocked by an actor
			if((current.x != mapCharacter.getTileX() || current.y != mapCharacter.getTileY())) {
				if(map.checkActorCollision(mapCharacter, next.x, next.y) != null) {
					//ignore.add(new Path.Point(next.x, next.y));
					//findNewPath(current);
					setStopIndex(index + 1);
					return;
				}
			}
			
			// Set position
			if(!mapCharacter.warp(current.x, current.y)) {
				setStopIndex(index);
				return;
			}
			mapCharacter.setOffset(-dx * tileDelta, -dy * tileDelta);
			
			// Update animation
			if(dy > 0)
				mapCharacter.setDirection(MapCharacter.dirDown);
			else if(dy < 0)
				mapCharacter.setDirection(MapCharacter.dirUp);
			else if(dx > 0)
				mapCharacter.setDirection(MapCharacter.dirRight);
			else if(dx < 0)
				mapCharacter.setDirection(MapCharacter.dirLeft);
		}
	}
	
	protected void findNewPath(Path.Point current) {
		Path.Point end = path.points.get(getStopIndex() - 1);
		Path newPath = pathfinder.searchPath(current.x, current.y, end.x, end.y, ignore);
		//System.out.println(newPath);
		setStopIndex(0);
		setPath(newPath);
		restart();
	}

	@Override
	public void reset() {
		super.reset();
		
		pathfinder = null;
		path = null;
		map = null;
		mapCharacter = null;
		index = 0;
		stopIndex = -1;
		ignore.clear();
	}
	
	@Override
	public void restart() {
		super.restart();
		
		index = 0;
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
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public MapCharacter getMapCharacter() {
		return mapCharacter;
	}

	public void setMapCharacter(MapCharacter mapCharacter) {
		this.mapCharacter = mapCharacter;
	}

	public int getIndex() {
		return index;
	}

	public int getStopIndex() {
		if(stopIndex == 0)
			return path.points.size();
		
		return stopIndex;
	}

	public void setStopIndex(int stopIndex) {
		// Need to adjust duration.
		int currentStopIndex = getStopIndex();	
		this.stopIndex = Math.max(1, Math.min(stopIndex, path.points.size()));
		int newStopIndex = getStopIndex();
		setDuration(getDuration() * ((float) newStopIndex / currentStopIndex));
	}
}
