package net.davexunit.rpg;

public interface MapListener {
	public void collided(MapActor actor1, MapActor actor2);
	public void overlapped(MapActor actor1, MapActor actor2);
}
