package net.davexunit.rpg;

import java.util.HashMap;

public class Party {
	private int mapId;
	private int mapX, mapY;
	private final HashMap<String, PartyMember> members;
	
	public Party() {
		members = new HashMap<String, PartyMember>();
	}
	
	public void addMember(PartyMember member) {
		members.put(member.getName(), member);
	}
	
	public PartyMember getMember(String name) {
		return members.get(name);
	}
	
	public void removeMember(String name) {
		members.remove(name);
	}
	
	public int getMapId() {
		return mapId;
	}
	
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	
	public int getMapX() {
		return mapX;
	}
	
	public void setMapX(int mapX) {
		this.mapX = mapX;
	}
	
	public int getMapY() {
		return mapY;
	}
	
	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public HashMap<String, PartyMember> getMembers() {
		return members;
	}
}
