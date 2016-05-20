package game.engine.actionLoop;

import game.engine.actions.IEngineRelatedUpdate;

public class RelatedActionLoop extends ActionLoop<IEngineRelatedUpdate>
{
	@Override
	protected void execute(IEngineRelatedUpdate action, float delatTime)
	{
		action.relatedUpdate();
	}
}