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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

class StatsComparator implements Comparator<BattleActor> 
{
	@Override
	public int compare(BattleActor p1, BattleActor p2) {
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
	
	private ArrayList<BattleActor>  mParty;
	private ArrayList<BattleActor>  mEnemies;
	private PriorityQueue<BattleActor> mBattleActorQueue; 
	private BattleActor mCurrentBattleActor;
	private static int width = 0;
	private static int height = 0;
	private int BORDER = 5;
	private TextureAtlas atlas;
	
	public BattleEngine(ArrayList<BattleActor> party, ArrayList<BattleActor> enemies) 
	{
		mParty = party;
		mEnemies = enemies;
		initQueue();
	}
	
	public BattleEngine(ArrayList<BattleActor> party, String area) 
	{
		//TODO: depending on the area generate enemies accordingly to that area
		
	}
	
	public BattleEngine(String area, TextureAtlas atlas) 
	{
		//TODO: depending on the area generate enemies accordingly to that area
		mParty = new ArrayList<BattleActor>();
		//mParty.add(BattleActor);
		
		this.atlas = atlas;
		
		createEnemies(area);
		
		initPositions();
		initQueue();	
	}
	
	public BattleActor makeActor() {
		Tileset tileset = new Tileset(atlas.findRegion("ghost"), 40, 46, 0, 0);
		
		Animation animIdle = new Animation(0.15f, tileset.getTileRange(tileset.coordToIndex(0, 2), 3));
		animIdle.setPlayMode(Animation.LOOP_PINGPONG);;
		
		BattleActor actor = new BattleActor();
		actor.animations.put("idle", animIdle);
		
		return actor;
	}
	
	private void createEnemies(String area) {
		int rand = new Random().nextInt(5) + 1;
		mEnemies = new ArrayList<BattleActor>();
		System.out.println("Enemies: " + rand);
		
		for (int i=0; i < rand; i++) {
			mEnemies.add(makeActor());
		}
	}
	
	private void initQueue() {
		int cap = mParty.size() + mEnemies.size();
		mBattleActorQueue = new PriorityQueue<BattleActor>(cap, new StatsComparator());
	}
	
	private void initPositions() {
		int size = mParty.size();
		float fullWidth = BORDER;
		float fullHeight = 0;
		float startY = 0;
		
		//set up party
		for(BattleActor p: mParty) {
			fullWidth += p.getWidth() / 2;
			fullHeight += p.getHeight() / 2;
		}
		
		if(size - 1 >= 0) {
			fullWidth += mParty.get(size-1).getWidth() / 2;
			fullHeight += mParty.get(size-1).getHeight() / 2;
			startY = (height / 2) + (fullHeight / 2);
		}
			
		for(BattleActor p: mParty) {
			p.setInBattle(true); //makes a backup of the world coordinates
			p.setX(BattleEngine.width - fullWidth);
			p.setY(startY);
			fullWidth -= p.getWidth() / 2;
			startY -= p.getHeight() / 2;
		}
		
		//set up enemies
		fullWidth = BORDER;
		fullHeight = 0;
		size = mEnemies.size();
		
		for(BattleActor p: mEnemies) {
			fullWidth += p.getWidth() / 2;
			fullHeight += p.getHeight() / 2;
		}
		
		fullWidth += mEnemies.get(size-1).getWidth() / 2;
		fullHeight += mEnemies.get(size-1).getHeight() / 2;
		startY = (height / 2) + (fullHeight / 2);
		
		for(BattleActor e: mEnemies) {
			e.setInBattle(true);
			e.setX(fullWidth);
			e.setY(startY);
			fullWidth -= e.getWidth() / 2;
			startY -= e.getHeight() / 2;
		}
	}
	
	private void getFullWidth() {
	}
	
	/*public boolean run()
	{
		
		while(true) {
			
			mBattleActorQueue.clear();
			mBattleActorQueue.addAll(mParty);
			mBattleActorQueue.addAll(mEnemies);
			
			Iterator<BattleActor> it = mBattleActorQueue.iterator();
			
			while(it.hasNext()) {
				BattleActor p = it.next();
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
		
		if (mBattleActorQueue.isEmpty()) {
			mBattleActorQueue.addAll(mParty);
			mBattleActorQueue.addAll(mEnemies);
		}
		
		BattleActor p = mBattleActorQueue.poll();
		
		mCurrentBattleActor = p;
	}
	
	public void executeAction(String action, BattleActor BattleActor) {
	}
	
	public void executeActionAt(int pos, BattleActor BattleActor) {
		//mCurrentBattleActor.executeAction(pos, BattleActor);
	}
	
	public boolean isFinished() {
		Iterator<BattleActor> it = mEnemies.iterator();
		while(it.hasNext()) {
			BattleActor p = it.next();
			if (! p.isAlive())
				it.remove();
		}
		
		if (mEnemies.isEmpty())
			return true;
		
		it = mParty.iterator();
		while(it.hasNext()) {
			BattleActor p = it.next();
			if (! p.isAlive())
				it.remove();
		}
		
		if (mParty.isEmpty())
			return true;
		
		return false;
	}
	
	public ArrayList<BattleActor> getParty() {
		return mParty;
	}
	
	public ArrayList<BattleActor> getEnemies() {
		return mEnemies;
	}
	
	public static void setScreenSize(int w, int h) {
		width = w;
		height = h;
	}
}
