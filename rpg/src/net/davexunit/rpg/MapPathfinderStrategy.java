package net.davexunit.rpg;

import java.util.ArrayList;
import java.util.List;

public class MapPathfinderStrategy implements PathfinderStrategy {
	private Map map;
	
	public MapPathfinderStrategy(Map map) {
		this.map = map;
	}
	
	@Override
	public int getCost(PathNode p) {
		return 1;
	}

	@Override
	public int getHeuristicCost(PathNode start, PathNode end) {
		return Math.abs(start.x - end.x) + Math.abs(start.y - end.y); 
	}

	@Override
	public void getNeighborNodes(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode p, List<Path.Point> ignore) {
		checkNeighbor(neighbors, pool, pool.obtain(p.x - 1, p.y), ignore);
		checkNeighbor(neighbors, pool, pool.obtain(p.x + 1, p.y), ignore);
		checkNeighbor(neighbors, pool, pool.obtain(p.x, p.y - 1), ignore);
		checkNeighbor(neighbors, pool, pool.obtain(p.x, p.y + 1), ignore);
	}
	
	private void checkNeighbor(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode neighbor, List<Path.Point> ignore) {
		if(neighbor.x < 0 || neighbor.x >= map.getWidth() || neighbor.y < 0 || neighbor.y >= map.getHeight()) {
			pool.free(neighbor);
			return;
		}
		
		if(map.checkMapCollision(neighbor.x, neighbor.y)) {
			pool.free(neighbor);
			return;
		}
		
		if(ignore != null) {
			for(Path.Point p: ignore) {
				if(neighbor.x == p.x && neighbor.y == p.y)
					return;
			}
		}
		
		neighbors.add(neighbor);
	}

	@Override
	public int nodeHash(PathNode node) {
		return node.y * map.getWidth() + node.x;
	}
}
