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
	protected boolean isStopped;
	
	public  Harvest (Entity entity)
	{
		this.entity = entity;
		isLoaded = false;
		
		resourceEntity = entity.getWorld().getClosest(entity, "resource");
		dropOffEntity = entity.getWorld().getClosest(entity, "dropOff");
		
		if(resourceEntity == null)
		{
			isStopped = true;
		}
		if(resourceEntity == null)
		{
			isStopped = true;
		}
		
		move = new Move(entity, resourceEntity.getData().getCenter());
		
	}
	
	@Override
	public void plan(double deltaTime) 
	{
		if(isStopped)
		{
			searchEntity();
		}
		if(isLoaded)
		{
			move.setDestination(dropOffEntity.getData().getCenter());
		}
		else
		{
			move.setDestination(resourceEntity.getData().getCenter());
		}

		move.plan(deltaTime);
	}
	
	@Override
	public void execute() 
	{
		if(isStopped)
		{
			return;
		}
		move.execute();
	}

	@Override
	public boolean isCompleted() 
	{
		if(isStopped)
		{
			return true;
		}
		return move.isCompleted();
	}

	@Override
	public void onComplete() 
	{
		searchEntity();
		
		if(isStopped)
		{
			return;
		}
			
		StringBuilder message = new StringBuilder();
		
		if(isLoaded)
		{
			message.append(this.entity.getData().get(AttributeSelector.ID()));
			message.append(" unloaded at ");
			message.append(this.dropOffEntity.getData().get(AttributeSelector.ID()));
			message.append(" moving at ");
			message.append((this.resourceEntity.getData().get(AttributeSelector.ID())));
			message.append("\n");
		}
		else
		{
			Item item = resourceEntity.getWorld().getItemType("RESOURCE 1");
			message.append(this.entity.getData().get(AttributeSelector.ID()));
			message.append(" loaded at ");
			message.append((this.resourceEntity.getData().get(AttributeSelector.ID())));
			message.append(" has ");
			message.append((this.resourceEntity.getInventory().getQuantity(item)));
			message.append(" ");
			message.append(item.getName());
			message.append(" remaining, now moving at ");
			message.append(this.dropOffEntity.getData().get(AttributeSelector.ID()));
			message.append("\n");
			
			resourceEntity.getInventory().removeItem(item, 1);
		}
		if(entity.getData().getPlayer() != null)
			entity.getController().writeToPlayers(message.toString().getBytes());
		isLoaded = !isLoaded;
		
		
	}
	
	protected void searchEntity()
	{
		resourceEntity = entity.getWorld().getClosest(entity, "resource");
		dropOffEntity = entity.getWorld().getClosest(entity, "dropOff");
		isStopped = false;
		if(resourceEntity == null)
		{
			isStopped = true;
		}
		if(resourceEntity == null)
		{
			isStopped = true;
		}
		if(isStopped)
		{
			return;
		}
	}
	

}
