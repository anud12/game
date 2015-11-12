package game.library.pawn.order;

import game.engine.IEngineAction;
import game.library.Entity;
import game.library.pawn.Pawn;

public class Harvest extends PawnOrder 
{
	protected Pawn pawn;
	
	protected Entity resourceEntity;
	protected Entity dropOffEntity;
	
	protected Move move;
	
	protected boolean isLoaded;
	protected boolean hasDropOff;
	protected boolean hasResource;
	
	public  Harvest (Pawn pawn){
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
	public void execute(double deltaTime) {
		if(isLoaded == false)
		{
			if(hasDropOff)
			{
				move.setDestination(resourceEntity.getCenter());
				
				move.execute(deltaTime);
			}
		}
		else
		{
			if(hasResource)
			{
				move.setDestination(dropOffEntity.getCenter());
				move.execute(deltaTime);
			}
		}
	}

	@Override
	public boolean isCompleted(IEngineAction action) {
		return move.isCompleted(action);
	}

	@Override
	public void onComplete(IEngineAction action) {
		
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
