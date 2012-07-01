package net.davexunit.rpg;

import com.badlogic.gdx.utils.Pool;

public class PathNodePool extends Pool<PathNode> {
	public PathNodePool() {
		super(50);
	}
	
	@Override
	protected PathNode newObject() {
		return new PathNode();
	}
	
	public PathNode obtain(int x, int y) {
		PathNode node = obtain();
		
		node.setPos(x, y);
		
		return node;
	}
}
