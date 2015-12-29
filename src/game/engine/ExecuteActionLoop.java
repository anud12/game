package game.engine;

class ExecuteActionLoop extends ActionLoop<IEngineAction>
{

	@Override
	protected void execute(IEngineAction action, float delatTime)
	{
		//Execute current action and grab the returned executed action
		IEngineAction returnedAction = action.execute();
		
		if(action.isCompleted(returnedAction))
		{
        	action.onComplete(returnedAction);
        	
        	if(action.isRemovable(returnedAction))
        	{
        		//Single access on removeBuffer
        		this.remove(returnedAction);
        	}
        }
		
	}

}
