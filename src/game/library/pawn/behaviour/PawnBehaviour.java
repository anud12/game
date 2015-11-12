package game.library.pawn.behaviour;

import game.engine.IEngineAction;

public abstract class PawnBehaviour implements IEngineAction{

	@Override
	public boolean isRemovable(IEngineAction action) {
		return false;
	}


}
