package net.davexunit.rpg;

import com.badlogic.gdx.files.FileHandle;

public interface DatabaseHelper {
	public void open(FileHandle file);
	public void close();
	public Party loadParty();
	public Inventory loadInventory();
	public Map loadMap(String name);
	public void saveParty(Party party);
	public void saveInventory(Inventory inventory);
	public void saveMap(Map map);
}
