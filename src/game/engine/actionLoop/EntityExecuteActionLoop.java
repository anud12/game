package game.engine.actionLoop;

import game.engine.actions.IEngineEntityExecution;

public class EntityExecuteActionLoop extends ActionLoop<IEngineEntityExecution>
{

	@Override
	protected void execute(IEngineEntityExecution action, float delatTime)
	{
		//Execute current action and grab the returned executed action
		action.execute();
		
		if(action.isCompleted())
		{
        	action.onComplete();
        	
        }
		if(action.isRemovable())
    	{
    		//Single access on removeBuffer
    		this.remove(action);
    	}
	}

}
