package net.davexunit.rpg;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class MapActions {
	public static <T extends Action> T action(Class<T> type) {
		Pool<T> pool = Pools.get(type);
		T action = pool.obtain();
		action.setPool(pool);
		return action;
	}
	
	public static FollowPathAction followPath(Map map, Pathfinder pathfinder, Path path, float duration) {
		FollowPathAction action = action(FollowPathAction.class);
		
		action.setPathfinder(pathfinder);
		action.setPath(path);
		action.setMap(map);
		action.setDuration(duration);
		
		return action;
	}
}
