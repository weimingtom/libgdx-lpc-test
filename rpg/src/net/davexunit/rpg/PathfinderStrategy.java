package net.davexunit.rpg;

import java.util.ArrayList;

public interface PathfinderStrategy {
	public int getCost(PathNode p);
	public int getHeuristicCost(PathNode start, PathNode end);
	public void getNeighborNodes(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode p);
	public int nodeHash(PathNode node);
}
