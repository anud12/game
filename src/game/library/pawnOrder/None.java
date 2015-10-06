package game.library.pawnOrder;

import game.gameLoop.IGameLoopAction;
import game.library.interfaces.IPawnOrder;

public class None implements IPawnOrder
{

	@Override
	public void execute(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCompleted(IGameLoopAction action) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRemovable(IGameLoopAction action) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onComplete(IGameLoopAction action) {
		// TODO Auto-generated method stub
		
	}

}
