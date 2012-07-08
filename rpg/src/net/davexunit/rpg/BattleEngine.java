package net.davexunit.rpg;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

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
	
	private ArrayList<Player>  mParty;
	private ArrayList<Player>  mEnemies;
	private PriorityQueue<Player> mPlayerQueue; 
	private Player mCurrentPlayer;
	private static int width = 0;
	private static int height = 0;
	private int BORDER = 5; 
	
	public BattleEngine(ArrayList<Player> party, ArrayList<Player> enemies) 
	{
		mParty = party;
		mEnemies = enemies;
		initQueue();
	}
	
	public BattleEngine(ArrayList<Player> party, String area) 
	{
		//TODO: depending on the area generate enemies accordingly to that area
		
	}
	
	public BattleEngine(Player player, String area) 
	{
		//TODO: depending on the area generate enemies accordingly to that area
		mParty = new ArrayList<Player>();
		mParty.add(player);
		
		createEnemies(area);
		
		initPositions();
		initQueue();
		
	}
	
	private void createEnemies(String area) {
		int rand = new Random().nextInt(5) + 1;
		mEnemies = new ArrayList<Player>();
		System.out.println("Enemies: " + rand);
		
		for (int i=0; i < rand; i++) {
			Texture texture;
			texture = new Texture(Gdx.files.internal("data/ghost.png"));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			mEnemies.add(new Player(texture));
		}
	}
	
	private void initQueue() {
		int cap = mParty.size() + mEnemies.size();
		mPlayerQueue = new PriorityQueue<Player>(cap, new StatsComparator());
	}
	
	private void initPositions() {
		int size = mParty.size();
		float fullWidth = BORDER;
		float fullHeight = 0;
		float startY = 0;
		
		//set up party
		for(Player p: mParty) {
			fullWidth += p.width / 2;
			fullHeight += p.height / 2;
		}
		
		fullWidth += mParty.get(size-1).width / 2;
		fullHeight += mParty.get(size-1).height / 2;
		startY = (height / 2) + (fullHeight / 2);
			
		for(Player p: mParty) {
			p.setInBattle(true); //makes a backup of the world coordinates
			p.x = BattleEngine.width - fullWidth;
			p.y = startY;
			fullWidth -= p.width / 2;
			startY -= p.height / 2;
		}
		
		//set up enemies
		fullWidth = BORDER;
		fullHeight = 0;
		size = mEnemies.size();
		
		for(Player p: mEnemies) {
			fullWidth += p.width / 2;
			fullHeight += p.height / 2;
		}
		
		fullWidth += mEnemies.get(size-1).width / 2;
		fullHeight += mEnemies.get(size-1).height / 2;
		startY = (height / 2) + (fullHeight / 2);
		
		for(Player e: mEnemies) {
			e.setInBattle(true);
			e.x = fullWidth;
			e.y = startY;
			fullWidth -= e.width / 2;
			startY -= e.height / 2;
		}
	}
	
	private void getFullWidth() {
	}
	
	/*public boolean run()
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
	}*/
	
	public void nextTurn() {
		
		if (mPlayerQueue.isEmpty()) {
			mPlayerQueue.addAll(mParty);
			mPlayerQueue.addAll(mEnemies);
		}
		
		Player p = mPlayerQueue.poll();
		
		mCurrentPlayer = p;
	}
	
	public void executeAction(String action, Player player) {
	}
	
	public void executeActionAt(int pos, Player player) {
		mCurrentPlayer.executeAction(pos, player);
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
	
	public ArrayList<Player> getParty() {
		return mParty;
	}
	
	public ArrayList<Player> getEnemies() {
		return mEnemies;
	}
	
	public static void setScreenSize(int w, int h) {
		width = w;
		height = h;
	}
}
