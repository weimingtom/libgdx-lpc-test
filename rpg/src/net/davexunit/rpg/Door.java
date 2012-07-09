package net.davexunit.rpg;

public class Door extends MapActor {
	private String mapFile;
	
	public Door() {
		this.mapFile = null;
	}

	public String getMapFile() {
		return mapFile;
	}

	public void setMapFile(String mapFile) {
		this.mapFile = mapFile;
	}
}
