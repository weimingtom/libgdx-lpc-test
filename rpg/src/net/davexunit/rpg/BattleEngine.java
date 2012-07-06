package net.davexunit.rpg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

class StatsComparator implements Comparator<Player> 
{
	@Override
	public int compare(Player p1, Player p2) {
		// TODO Auto-generated method stub
		int turnCost1 = p1.turnCost();
		int turnCost2 = p2.turnCost();
		
		if (turnCost1 > turnCost2)
			return 1;
		else if (turnCost1 == turnCost2)
			return 0;
		
		return -1;
	}
} 


public class BattleEngine {
	
	ArrayList<Player>  mParty;
	ArrayList<Player>  mEnemies;
	PriorityQueue<Player> mPlayerQueue; 
	
	public BattleEngine(ArrayList<Player> party, ArrayList<Player> enemies) 
	{
		mParty = party;
		mEnemies = enemies;
		mPlayerQueue = new PriorityQueue<Player>(party.size(), new StatsComparator());
	}
	
	public BattleEngine(ArrayList<Player> party, String area) 
	{
		//TODO: depending on the area generate enemies accordingly to that area
	}
	
	public boolean run()
	{
		
		while(true) {
			
			mPlayerQueue.clear();
			mPlayerQueue.addAll(mParty);
			mPlayerQueue.addAll(mEnemies);
			
			Iterator<Player> it = mPlayerQueue.iterator();
			
			while(it.hasNext()) {
				Player p = it.next();
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				it.remove();
			}
			
			
		}
	}
	
	public boolean isFinished() {
		Iterator<Player> it = mEnemies.iterator();
		while(it.hasNext()) {
			Player p = it.next();
			if (! p.isAlive())
				it.remove();
		}
		
		if (mEnemies.isEmpty())
			return true;
		
		it = mParty.iterator();
		while(it.hasNext()) {
			Player p = it.next();
			if (! p.isAlive())
				it.remove();
		}
		
		if (mParty.isEmpty())
			return true;
		
		return false;
	}
}
