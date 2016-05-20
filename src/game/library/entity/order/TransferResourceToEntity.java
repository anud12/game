package game.library.entity.order;

import game.engine.actions.IEngineEntityExecution;
import game.engine.actions.IEngineEntityPlan;
import game.library.entity.Entity;
import game.library.inventory.item.Item;

public class TransferResourceToEntity extends ControllerOrder
{
	protected Entity from;
	protected Entity to;
	
	protected double minimumDistance;
	protected double maximumDistance;
	
	protected Item resourceToTransfer;
	protected double rateOfTransfer;
	
	protected float currentQuantityToTransfer;
	
	protected float quantity;
	protected float currentQuantity;
	
	protected boolean ok;
	
	public TransferResourceToEntity(Entity from, Entity to, double minimmumDistance, double maximumDistance, Item itemTransfer, double rateOfTransfer, float quantity)
	{
		this.from = from;
		this.to = to;
		
		this.maximumDistance = maximumDistance;
		this.minimumDistance = minimmumDistance;
		
		this.resourceToTransfer = itemTransfer;
		this.rateOfTransfer = rateOfTransfer;
		
		this.quantity = quantity;
	}
	
	@Override
	public void plan(double deltaTime)
	{
		ok = true;
		
		double distance = from.getCenter().distance(to.getCenter());
		if( distance > minimumDistance && distance < maximumDistance)
		{
			ok = false;
		}
		
		currentQuantityToTransfer = (float) (rateOfTransfer / deltaTime);
		
		float remainingResources = from.getInventory().getFreeSize(resourceToTransfer);
		
		if(remainingResources < currentQuantityToTransfer)
		{
			currentQuantityToTransfer = remainingResources;
		}
		
		currentQuantity += currentQuantityToTransfer;
	}

	@Override
	public void execute()
	{
		if(!ok)	return;
		
		synchronized(from)
		{
			synchronized(to)
			{
				from.getInventory().removeItem(resourceToTransfer, currentQuantity);
				to.getInventory().addItem(resourceToTransfer, currentQuantity);
			}
		}
		
	}

	@Override
	public boolean isCompleted()
	{
		if(!ok)	return true;
		if(currentQuantity >= quantity) return true;
		if(currentQuantityToTransfer == 0) return true;
		
		return false;
	}

}
