package game.tests;

import game.engine.IEngineAction;

public class oneRunAction implements IEngineAction
{
	int i;
	int target;
	
	public oneRunAction(int steps)
	{
		this.target = steps;		
	}
	@Override
	public void execute(double deltaTime) {
		i++;
	}

	@Override
	public boolean isCompleted(IEngineAction action) {
		if(i > target)
			return true;
		return false;
	}

	@Override
	public boolean isRemovable(IEngineAction action) {
		return true;
	}

	@Override
	public void onComplete(IEngineAction action) {
		
	}
	
}