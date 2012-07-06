package net.davexunit.rpg;

import java.util.HashMap;

public class Items {
	private final HashMap<String, Item> items;
	
	public Items() {
		items = new HashMap<String, Item>();
	}

	public HashMap<String, Item> getItems() {
		return items;
	}
	
	public Item getItem(String name) {
		Item item = items.get(name);
		
		if(item != null)
			return item.copy();
		
		return null;
	}
	
	public void addItem(Item item) {
		items.put(item.getName(), item);
	}
}
