package game.engine;

class PlanActionLoop extends ActionLoop<IEngineAction>
{

	@Override
	protected void execute(IEngineAction action, float deltaTime)
	{
		//Execute current action and grab the returned executed action
		action.plan(deltaTime);
		
	}
}
