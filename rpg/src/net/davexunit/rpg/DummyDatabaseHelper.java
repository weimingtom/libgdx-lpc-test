package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class DummyDatabaseHelper implements DatabaseHelper {
	@Override
	public void open(FileHandle file) {
	}

	@Override
	public void close() {
	}

	@Override
	public Party loadParty() {
		Party party = new Party();
		PartyMember member = new PartyMember();
		
		member.setName("davexunit");
		
		party.addMember(member);
		
		return party;
	}

	@Override
	public Inventory loadInventory() {
		Inventory inventory = new Inventory();
		
		inventory.addItem("Pumpkin", 5);
		
		return inventory;
	}

	@Override
	public void saveParty(Party party) {
	}

	@Override
	public void saveInventory(Inventory inventory) {
	}
	
	@Override
	public Map loadMap(String name) {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Map map = new Map(Gdx.files.internal("data/maps/test2.tmx"), Gdx.files.internal("data/maps"), w, h);
		return map;
	}

	@Override
	public void saveMap(Map map) {
	}
}
