package game.engine.actionLoop;

import game.engine.actions.IEngineEntityPlan;

public class EntityPlanActionLoop extends ActionLoop<IEngineEntityPlan>
{

	@Override
	protected void execute(IEngineEntityPlan action, float deltaTime)
	{
		//Execute current action and grab the returned executed action
		
		action.plan(deltaTime);
		
		if(action.isRemovable())
    	{
    		//Single access on removeBuffer
    		this.remove(action);
    	}
	}
	
}
