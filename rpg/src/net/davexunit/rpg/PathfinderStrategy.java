package net.davexunit.rpg;

import java.util.ArrayList;
import java.util.List;

public interface PathfinderStrategy {
	public int getCost(PathNode p);
	public int getHeuristicCost(PathNode start, PathNode end);
	public void getNeighborNodes(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode p, List<Path.Point> ignore);
	public int nodeHash(PathNode node);
}
