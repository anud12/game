package game.engine.actionLoop;

import game.engine.actions.IEngineEntityAliveCheck;

public class EntityAliveCheckActionLoop extends ActionLoop<IEngineEntityAliveCheck>
{

	@Override
	protected void execute(IEngineEntityAliveCheck action, float deltaTime)
	{ 
		action.aliveCheck(deltaTime);
		
		if(action.isRemovable())
    	{
    		//Single access on removeBuffer
    		this.remove(action);
    	}
	}
	

}
