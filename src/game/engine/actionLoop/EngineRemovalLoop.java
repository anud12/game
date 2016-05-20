package game.engine.actionLoop;

import game.engine.actions.IEngineRemoval;

public class EngineRemovalLoop  extends ActionLoop<IEngineRemoval>
{

	@Override
	protected void execute(IEngineRemoval action, float delatTime)
	{
		if(action.isRemovable())
    	{
    		//Single access on removeBuffer
    		this.remove(action);
    	}
	}

}
