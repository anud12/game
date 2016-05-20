package game.tests;

import game.engine.actions.IEngineEntityExecution;
import game.engine.actions.IEngineEntityPlan;

public class oneRunAction implements IEngineEntityExecution, IEngineEntityPlan
{
	int i;
	int target;
	
	public oneRunAction(int steps)
	{
		this.target = steps;		
	}
	
	@Override
	public void plan(double deltaTime) {
		if(i%2 == 0)
			return;
		return;
		
	}
	
	@Override
	public void execute() {
		i++;
	}

	@Override
	public boolean isCompleted() {
		if(i > target)
			return true;
		return false;
	}

	@Override
	public boolean isRemovable() {
		return isCompleted();
	}

	@Override
	public void onComplete() {
		
	}
	
	
}