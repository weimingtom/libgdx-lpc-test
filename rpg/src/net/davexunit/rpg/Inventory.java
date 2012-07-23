package net.davexunit.rpg;

import java.util.ArrayList;

public class Inventory {
	public static class Element {
		public final Item item;
		public int quantity;
		
		public Element(Item item) {
			this.item = item;
			this.quantity = 1;
		}
		
		public Element(Item item, int quantity) {
			this.item = item;
			this.quantity = quantity;
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof Element))
				return false;
			
			Element other = (Element) obj;
			
			return item.equals(other.item);
		}
	}
	
	public final ArrayList<Element> elements;
	
	public Inventory() {
		elements = new ArrayList<Element>();
	}
	
	public void add(Item item, int quantity) {
		for(Element e: elements) {
			if(e.item.equals(item)) {
				e.quantity += quantity;
				return;
			}
		}
		
		elements.add(new Element(item, quantity));
	}
}
