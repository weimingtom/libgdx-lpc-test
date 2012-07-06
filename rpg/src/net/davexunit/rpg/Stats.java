package net.davexunit.rpg;

import java.util.HashMap;

public class Stats {
	
	HashMap<String, Integer> mMaxStats;
	HashMap<String, Integer> mStats;
	
	public static void loadStats(String filePath) {
		
	}
	
	public int getMaxStat(String name) {
		if (! mMaxStats.containsKey(name))
			return 0;
		return mMaxStats.get(name);
	}
	
	public int getStat(String name) {
		if (! mStats.containsKey(name))
			return 0;
		return mStats.get(name);
	}

}
