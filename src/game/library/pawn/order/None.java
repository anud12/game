package game.library.pawn.order;

import game.engine.IEngineAction;

public class None implements IPawnOrder
{

	@Override
	public void execute(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCompleted(IEngineAction action) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRemovable(IEngineAction action) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onComplete(IEngineAction action) {
		// TODO Auto-generated method stub
		
	}

}