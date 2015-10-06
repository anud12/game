package game.library.pawnOrder;

import java.awt.geom.Point2D;

import game.gameLoop.IGameLoopAction;
import game.gameLoop.MoveAction;
import game.library.Entity;
import game.library.Pawn;
import game.library.interfaces.IPawnOrder;

public class Harvest implements IPawnOrder 
{
	protected Pawn pawn;
	
	protected Entity resourceEntity;
	protected Entity dropOffEntity;
	
	protected Point2D.Float returnPoint;
	
	protected MoveAction move;
	
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
		
		System.out.println(resourceEntity.getCenter());
		System.out.println(hasDropOff + " " + hasResource);
		move = new MoveAction(pawn, resourceEntity.getCenter());
		returnPoint = new Point2D.Float(pawn.getCenter().x, pawn.getCenter().y);
	}
	@Override
	public void execute(double deltaTime) {
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
		move.execute(deltaTime);
	}

	@Override
	public boolean isCompleted(IGameLoopAction action) {
		return move.isCompleted(action);
		
	}

	@Override
	public boolean isRemovable(IGameLoopAction action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onComplete(IGameLoopAction action) {
		if(isLoaded == false)
		{
			if(hasDropOff)
			{
				move.setDestination(dropOffEntity.getCenter());
				isLoaded = true;
				System.out.println("Loaded at :" + pawn.getCenter().x + " " + pawn.getCenter().y);
			}
		}
		else
		{
			if(hasResource)
			{
				move.setDestination(resourceEntity.getCenter());
				isLoaded = false;
				System.out.println("Unloaded at :" + pawn.getCenter().x + " " + pawn.getCenter().y);
			}
		}
	}

}
