package net.davexunit.rpg;

import java.util.HashMap;

public class Inventory {
	// Mapping from item name to quantity.
	// Look up items in an Items instance.
	private final HashMap<String, Integer> inventory;
	private Items items;
	
	public Inventory() {
		inventory = new HashMap<String, Integer>();
	}
	
	public Item withdrawItem(String name) {
		int quantity = inventory.get(name);
		
		if(quantity > 0) {
			--quantity;
			
			if(quantity <= 0)
				inventory.remove(name);
			else
				inventory.put(name, quantity);
			
			return items.getItem(name);
		}
		
		return null;
	}
	
	public void addItem(String name, int quantity) {
		if(inventory.containsKey(name)) {
			quantity += inventory.get(name);
			inventory.put(name, quantity);
		} else
			inventory.put(name, quantity);
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public HashMap<String, Integer> getInventory() {
		return inventory;
	}
}
