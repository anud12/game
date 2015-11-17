package game.library.pawn.behaviour;

import game.engine.IEngineAction;

public class Idle extends PawnBehaviour{

	@Override
	public void plan(double deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IEngineAction execute() 
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
