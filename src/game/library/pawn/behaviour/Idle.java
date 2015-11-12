package game.library.pawn.behaviour;

import game.engine.IEngineAction;

public class Idle extends PawnBehaviour{

	@Override
	public IEngineAction execute(double deltaTime) 
	{
		return this;
	}

	@Override
	public boolean isCompleted(IEngineAction action) 
	{
		return false;
	}

	@Override
	public void onComplete(IEngineAction action) 
	{
		
	}

}
