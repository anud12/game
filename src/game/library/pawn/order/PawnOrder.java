package game.library.pawn.order;

import game.engine.IEngineAction;

public abstract class PawnOrder implements IEngineAction
{
	protected float unusedDeltaTime;
	
	@Override
	public final boolean isRemovable(IEngineAction action) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public float getUnusedDeltaTime()
	{
		return unusedDeltaTime;
	}
}
