package net.davexunit.rpg;

import java.util.ArrayList;

class AttackAction extends Action {

	public AttackAction(int damage, Player target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	
	public AttackAction(int damage, ArrayList<Player> targets) {
		super(targets);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void  execute() {
		
	}
	
}

class ItemAction extends Action {

	public ItemAction(int damage, Player target) {
		super(target);
		// TODO Auto-generated constructor stub
	}
	
	public ItemAction(int damage, ArrayList<Player> targets) {
		super(targets);
		// TODO Auto-generated constructor stub
	}
	
}

public class Action {
	
	private ArrayList<Player> mTargets;
	
	public Action (Player target) {
		mTargets = new ArrayList<Player>();
		mTargets.add(target);
	}
	
	public Action (ArrayList<Player> targets) {
		mTargets = targets;
	}
	
	//Should be reimplemented by derivative classes
	public void execute() {
		
	}
}
