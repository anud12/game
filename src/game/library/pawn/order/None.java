package game.library.pawn.order;

import game.engine.IEngineAction;

public class None extends PawnOrder
{

	@Override
	public IEngineAction execute(double deltaTime) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean isCompleted(IEngineAction action) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onComplete(IEngineAction action) {
		// TODO Auto-generated method stub
		
	}

}
