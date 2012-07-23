package net.davexunit.rpg;

public abstract class Item {
	protected String name;
	protected String description;
	
	public Item() {
		this.name = null;
		this.description = null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Item))
			return false;
		
		Item other = (Item) obj;
		
		return name.equals(other.name);
	}

	public abstract Item copy();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
