package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class FollowPathAction extends TemporalAction {
	protected Path path;
	protected Map map;
	protected MapCharacter mapCharacter;

	@Override
	public void setActor(Actor actor) {
		super.setActor(actor);
		mapCharacter = (MapCharacter) actor;
	}

	@Override
	protected void initialize() {
		Path.Point p = path.points.get(0);
		
		mapCharacter.warp(p.x, p.y);
		mapCharacter.setOffset(0, 0);
		mapCharacter.setWalking(true);
	}

	@Override
	protected void update(float percent) {
		int index = (int) Math.floor(percent * path.points.size());
		
		if(index == path.points.size()) {
			Path.Point p = path.points.get(path.points.size() - 1);
			
			mapCharacter.warp(p.x, p.y);
			mapCharacter.setOffset(0, 0);
			
			return;
		}
		
		// Calculate the percentage (from 0.0 to 1.0) of tile that has been walked upon.
		float tileStartAlpha =  (float) index / path.points.size();
		float perTileAlpha = (float) 1 / path.points.size();
		float tileDelta = (percent - tileStartAlpha) / perTileAlpha;
		
		Path.Point current = path.points.get(index);
		Path.Point next = path.points.get(Math.min(index + 1, path.points.size() - 1));
		
		// Get differences in position to determine direction
		float dx = next.x - current.x;
		float dy = next.y - current.y;
		
		// Set position
		mapCharacter.warp(current.x, current.y);
		mapCharacter.setOffset(dx * tileDelta, dy * tileDelta);
		
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
}
