package net.davexunit.rpg;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

public class MapPathfinderStrategy implements PathfinderStrategy {
	private TiledMap map;
	
	public MapPathfinderStrategy(TiledMap map) {
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
	public void getNeighborNodes(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode p) {
		checkNeighbor(neighbors, pool, pool.obtain(p.x - 1, p.y));
		checkNeighbor(neighbors, pool, pool.obtain(p.x + 1, p.y));
		checkNeighbor(neighbors, pool, pool.obtain(p.x, p.y - 1));
		checkNeighbor(neighbors, pool, pool.obtain(p.x, p.y + 1));
	}
	
	private void checkNeighbor(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode neighbor) {
		if(neighbor.x < 0 || neighbor.x >= map.width || neighbor.y < 0 || neighbor.y >= map.height) {
			pool.free(neighbor);
			return;
		}
		
		if(map.layers.get(5).tiles[neighbor.y][neighbor.x] == 202) {
			pool.free(neighbor);
			return;
		}
		
		neighbors.add(neighbor);
	}

	@Override
	public int nodeHash(PathNode node) {
		return node.y * map.width + node.x;
	}
}
