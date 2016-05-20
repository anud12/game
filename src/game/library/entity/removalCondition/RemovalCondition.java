package game.library.entity.removalCondition;

import game.library.entity.Entity;

public abstract class RemovalCondition
{
	public abstract boolean removeCheck(Entity entity, Float deltaTime);
	public abstract String toString();
}
