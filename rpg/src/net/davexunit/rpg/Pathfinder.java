package net.davexunit.rpg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Pathfinder {
	private PathfinderStrategy strategy;
	private PathNodePool pathNodePool;
	private PriorityQueue<PathNode> openList;
	private HashMap<Integer, PathNode> closedList;
	private ArrayList<PathNode> neighbors; 
	
	public Pathfinder(PathfinderStrategy strategy) {
		this.strategy = strategy;
		this.pathNodePool = new PathNodePool();
		this.openList = new PriorityQueue<PathNode>(50, new PathNodeComparator());
		this.closedList = new HashMap<Integer, PathNode>();
		this.neighbors = new ArrayList<PathNode>();
	}
	
	private static class PathNodeComparator implements Comparator<PathNode> {
		@Override
		public int compare(PathNode a, PathNode b) {
			return a.f - b.f;
		}
	}
	
	public Path searchPath(int startX, int startY, int endX, int endY) {
		PathNode start = pathNodePool.obtain(startX, startY);
		PathNode end = pathNodePool.obtain(endX, endY);
		
		openList.add(start);
		start.g = 0;
		start.f = start.g + strategy.getHeuristicCost(start, end);
		
		while(!openList.isEmpty()) {
			PathNode current = openList.peek();
			
			if(current.equals(end)) {
				Path path = reconstructPath(current);
				cleanUp();
				return path;
			}
			
			openList.poll();
			closedList.put(strategy.nodeHash(current), current);
			neighbors.clear();
			strategy.getNeighborNodes(neighbors, pathNodePool, current);
			
			for(PathNode neighbor: neighbors) {
				if(closedList.containsKey(strategy.nodeHash(neighbor))) {
					pathNodePool.free(neighbor);
					continue;
				}
				
				int tentativeG = current.g + strategy.getCost(neighbor);
				
				if(!openList.contains(current) || tentativeG < neighbor.g) {
					neighbor.g = tentativeG;
					neighbor.f = neighbor.g + strategy.getHeuristicCost(neighbor, end);
					openList.add(neighbor);
					neighbor.parent = current;
				}
				else {
					pathNodePool.free(neighbor);
				}
			}
		}
		
		cleanUp();
		
		return null;
	}
	
	private void cleanUp() {
		pathNodePool.clear();
		openList.clear();
		closedList.clear();
		neighbors.clear();
	}
	
	private Path reconstructPath(PathNode current) {
		if(current.parent != null) {
			Path path = reconstructPath(current.parent);
			path.addPoint(current.x, current.y);
			return path;
		}
		
		Path path = new Path();
		path.addPoint(current.x, current.y);
		return path;
	}
}
