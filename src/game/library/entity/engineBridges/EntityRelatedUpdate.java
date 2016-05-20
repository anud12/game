package game.library.entity.engineBridges;

import game.engine.actions.IEngineRelatedUpdate;
import game.engine.actions.IEngineRemoval;
import game.library.entity.Entity;
import game.library.entity.IUseEntity;

public class EntityRelatedUpdate implements IEngineRelatedUpdate, IEngineRemoval, IUseEntity
{
	protected Entity entity;
	protected boolean finished;
	
	public EntityRelatedUpdate(Entity entity)
	{
		this.entity = entity;
		finished = !entity.isAlive();
	}
	@Override
	public void relatedUpdate()
	{
		if(!finished)
		{
			finished = !entity.isAlive();
		}
		if(finished)
		{
			entity = null;			
			return;
		}
		
		entity.getVision().relatedUpdate(entity);
	}
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
