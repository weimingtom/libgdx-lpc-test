package net.davexunit.rpg;

import java.util.ArrayList;

class AttackAction extends Action {

	public AttackAction(int damage, BattleActor target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	
	public AttackAction(int damage, ArrayList<BattleActor> targets) {
		super(targets);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void  execute() {
		
	}
	
}

class ItemAction extends Action {

	public ItemAction(int damage, BattleActor target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	
	public ItemAction(int damage, ArrayList<BattleActor> targets) {
		super(targets);
		// TODO Auto-generated constructor stub
	}
	
}

public class Action {
	
	private ArrayList<BattleActor> mTargets;
	
	public Action (BattleActor target) {
		mTargets = new ArrayList<BattleActor>();
		mTargets.add(target);
	}
	
	public Action (ArrayList<BattleActor> targets) {
		mTargets = targets;
	}
	
	//Should be reimplemented by derivative classes
	public void execute() {
		
	}
}
