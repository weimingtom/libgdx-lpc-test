package net.davexunit.rpg;

public abstract class Item {
	public static final int itemStatus = 1;
	public static final int itemWeapon = 2;
	public static final int itemArmor = 3;
	public static final int itemBoots = 4;
	public static final int itemHelm = 5;
	
	protected String name;
	protected String description;
	protected int type;
	
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
	
	public int getType() {
		return type;
	}
}
