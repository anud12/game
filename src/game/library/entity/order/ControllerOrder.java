package game.library.entity.order;

import game.engine.actions.IEngineEntityExecution;
import game.engine.actions.IEngineEntityPlan;

public abstract class ControllerOrder implements IEngineEntityExecution, IEngineEntityPlan
{
	protected float unusedDeltaTime;
	
	public abstract boolean isCompleted();
	
	@Override
	public final boolean isRemovable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public float getUnusedDeltaTime()
	{
		return unusedDeltaTime;
	}
	
	@Override
	public void onComplete() 
	{
	}
}
