package game.library.entity.engineBridges;

import java.util.Iterator;
import java.util.LinkedList;

import game.engine.actions.IEngineEntityAliveCheck;
import game.library.entity.Entity;
import game.library.entity.IUseEntity;
import game.library.entity.removalCondition.RemovalCondition;

public class EntityRemovalChecker implements IEngineEntityAliveCheck, IUseEntity
{
	protected Entity entity;
	protected LinkedList<RemovalCondition> conditions;
	protected boolean finished;
	
	public EntityRemovalChecker(Entity entity)
	{
		this.entity = entity;
		conditions = new LinkedList<RemovalCondition>();
		finished = false;
	}
	
	protected void remove(Entity entity)
	{
		entity.setAlive(false);
		entity.getWorld().removeEntity(entity);
		entity.getPlayer().removeEntity(entity);
	}
	
	@Override
	public void aliveCheck(Float deltaTime)
	{
		if(finished)
		{
			return;
		}
		Iterator<RemovalCondition> iterator = conditions.iterator();
		
		while(iterator.hasNext())
		{
			if(iterator.next().removeCheck(entity, deltaTime))
			{
				remove(entity);
				entity = null;
				finished = true;
				return;
			}
		}
	}
	
	public LinkedList<RemovalCondition> getConditionList()
	{
		return conditions;
	}
	
	public void addCondition(RemovalCondition condition)
	{
		conditions.add(condition);
	}

	@Override
	public Entity getEntity()
	{
		return entity;
	}

	@Override
	public boolean isRemovable()
	{
		return finished;
	}	

	
}
