package game.library.entity.behaviour;

import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.entity.order.Move;
import game.library.inventory.item.Item;

public class Harvest extends EntityBehaviour 
{
	protected Entity entity;
	
	protected Entity resourceEntity;
	protected Entity dropOffEntity;
	
	protected Move move;
	
	protected boolean isLoaded;
	protected boolean hasDropOff;
	protected boolean hasResource;
	
	public  Harvest (Entity entity)
	{
		this.entity = entity;
		isLoaded = false;
		
		resourceEntity = entity.getWorld().getClosest("resource");
		dropOffEntity = entity.getWorld().getClosest("dropOff");
		
		hasResource = true;
		hasDropOff = true;
		
		if(resourceEntity == null)
		{
			hasResource = false;
		}
		if(dropOffEntity == null)
		{
			hasDropOff = false;
		}
		
		move = new Move(entity, resourceEntity.getCenter());
		
	}
	
	@Override
	public void plan(double deltaTime) 
	{
		if(isLoaded)
		{
			if(hasDropOff)
			{
				if(!dropOffEntity.isAlive())
				{
					dropOffEntity = entity.getWorld().getClosest("dropOff");
				}
				move.setDestination(dropOffEntity.getCenter());
			}
		}
		else
		{
			if(hasResource)
			{
				if(!resourceEntity.isAlive())
				{
					resourceEntity = entity.getWorld().getClosest("resource");
				}
				move.setDestination(resourceEntity.getCenter());
			}
		}

		move.plan(deltaTime);
	}
	
	@Override
	public void execute() 
	{
		move.execute();
	}

	@Override
	public boolean isCompleted() 
	{
		return move.isCompleted();
	}

	@Override
	public void onComplete() 
	{
		StringBuilder message = new StringBuilder();
		
		if(isLoaded)
		{
			message.append(this.entity.get(AttributeSelector.ID()));
			message.append(" unloaded at ");
			message.append(this.dropOffEntity.get(AttributeSelector.ID()));
			message.append(" moving at ");
			message.append((this.resourceEntity.get(AttributeSelector.ID())));
			message.append("\n");
		}
		else
		{
			Item item = resourceEntity.getWorld().getItemType("RESOURCE 1");
			message.append(this.entity.get(AttributeSelector.ID()));
			message.append(" loaded at ");
			message.append((this.resourceEntity.get(AttributeSelector.ID())));
			message.append(" has ");
			message.append((this.resourceEntity.getInventory().getQuantity(item)));
			message.append(" ");
			message.append(item.getName());
			message.append(" remaining, now moving at ");
			message.append(this.dropOffEntity.get(AttributeSelector.ID()));
			message.append("\n");
			
			resourceEntity.getInventory().removeItem(item, 1);
		}
		if(entity.getPlayer() != null)
			entity.getController().writeToPlayers(message.toString().getBytes());
		isLoaded = !isLoaded;
	}
	

}
