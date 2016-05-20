package game.engine.actionLoop;

import game.engine.actions.IEngineUnrelatedUpdate;

public class UnrelatedActionLoop extends ActionLoop<IEngineUnrelatedUpdate>
{

	@Override
	protected void execute(IEngineUnrelatedUpdate action, float delatTime)
	{
		action.unrelatedUpdate();
	}


}
