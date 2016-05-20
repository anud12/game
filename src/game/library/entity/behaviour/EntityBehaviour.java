package game.library.entity.behaviour;

import game.engine.actions.IEngineEntityExecution;
import game.engine.actions.IEngineEntityPlan;

public abstract class EntityBehaviour implements IEngineEntityExecution, IEngineEntityPlan
{

	@Override
	public boolean isRemovable() {
		return false;
	}


}
