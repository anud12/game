package game.engine;

class UpdateActionLoop extends ActionLoop<IEngineVariable>
{

	@Override
	protected void execute(IEngineVariable action, float delatTime)
	{
		action.update();
	}


}
