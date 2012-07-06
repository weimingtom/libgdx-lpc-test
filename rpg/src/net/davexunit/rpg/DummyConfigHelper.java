package net.davexunit.rpg;

public class DummyConfigHelper implements ConfigHelper {
	@Override
	public Items loadItems() {
		Items items = new Items();
		StatusItem statusItem = new StatusItem();
		
		items.addItem(statusItem);
		
		return items;
	}

}
