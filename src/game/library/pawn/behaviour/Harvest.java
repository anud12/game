package game.library.pawn.behaviour;

import game.engine.IEngineAction;
import game.library.Entity;
import game.library.pawn.Pawn;
import game.library.pawn.order.Move;

public class Harvest extends PawnBehaviour 
{
	protected Pawn pawn;
	
	protected Entity resourceEntity;
	protected Entity dropOffEntity;
	
	protected Move move;
	
	protected boolean isLoaded;
	protected boolean hasDropOff;
	protected boolean hasResource;
	
	public  Harvest (Pawn pawn)
	{
		this.pawn = pawn;
		isLoaded = false;
		
		resourceEntity = pawn.getWorld().getClosest("resource");
		dropOffEntity = pawn.getWorld().getClosest("dropOff");
		
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
		
		move = new Move(pawn, resourceEntity.getCenter());
		
	}
	
	@Override
	public void plan(double deltaTime) {
		
		if(isLoaded == false)
		{
			if(hasDropOff)
			{
				move.setDestination(dropOffEntity.getCenter());
			}
		}
		else
		{
			if(hasResource)
			{
				move.setDestination(resourceEntity.getCenter());
			}
		}

		move.plan(deltaTime);
	}
	
	@Override
	public IEngineAction execute() 
	{
		return move.execute();
	}

	@Override
	public boolean isCompleted(IEngineAction returnedAction) 
	{
		return move.isCompleted(returnedAction);
	}

	@Override
	public void onComplete(IEngineAction action) 
	{
		if(isLoaded == false)
		{
			if(hasDropOff)
			{
				move.setDestination(dropOffEntity.getCenter());
			}
		}
		else
		{
			if(hasResource)
			{
				move.setDestination(resourceEntity.getCenter());
			}
		}
		isLoaded = !isLoaded;	
	}
	

}
