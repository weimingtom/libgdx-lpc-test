package net.davexunit.rpg;

public class PathNode {
	public int x;
	public int y;
	public int f;
	public int g;
	public PathNode parent;
	
	public PathNode() {
		this.x = 0;
		this.y = 0;
		this.f = 0;
		this.g = 0;
		this.parent = null;
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		PathNode node = (PathNode) obj;
		
		return x == node.x && y == node.y;
	}
}
