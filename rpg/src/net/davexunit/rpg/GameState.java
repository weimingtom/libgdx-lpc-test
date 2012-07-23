package net.davexunit.rpg;

import com.badlogic.gdx.files.FileHandle;

public class GameState {
	private Party party;
	private Inventory inventory;
	private DatabaseHelper db;
	private ConfigHelper config;
	
	public GameState() {
		this.party = null;
		this.inventory = null;
		this.db = null;
		this.config = null;
	}
	
	public void load(FileHandle file) {
		db.open(file);
		party = db.loadParty();
		inventory = db.loadInventory();
	}
	
	public void save(FileHandle file) {
		db.saveParty(party);
		db.saveInventory(inventory);
	}
	
	public Map loadMap(String name) {
		return db.loadMap(name);
	}
	
	public Party getParty() {
		return party;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public DatabaseHelper getDatabaseHelper() {
		return db;
	}

	public void setDatabaseHelper(DatabaseHelper db) {
		this.db = db;
	}

	public ConfigHelper getConfigHelper() {
		return config;
	}

	public void setConfigHelper(ConfigHelper config) {
		this.config = config;
	}
}
