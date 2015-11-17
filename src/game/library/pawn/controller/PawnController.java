package game.library.pawn.controller;

import java.util.ArrayList;

import game.engine.IEngineAction;
import game.library.pawn.Pawn;
import game.library.pawn.behaviour.Idle;
import game.library.pawn.behaviour.PawnBehaviour;
import game.library.pawn.order.PawnOrder;

public class PawnController implements IEngineAction
{
	ArrayList<PawnOrder> orders;
	Pawn pawn;
	PawnBehaviour behaviour;
	
	public PawnController(Pawn pawn)
	{
		this.pawn = pawn;
		orders = new ArrayList<PawnOrder>();
		
		//TODO:Delete
		behaviour = new Idle();
	}
	//Setter Getters
	
	
	public void queueOrder(PawnOrder order)
	{
		orders.add(order);
	}
	
	public void setOrder (PawnOrder order)
	{
		orders.clear();
		orders.add(order);
	}
	
	public PawnOrder getFirst()
	{
		if(orders.isEmpty())
			return null;
		return orders.get(0);
	}
	
	public PawnBehaviour getBehaviour()
	{
		return  behaviour;
	}
	public void setBehaviour(PawnBehaviour behaviour)
	{
		this.behaviour = behaviour;
	}
	
	public int size()
	{
		return orders.size();
	}
	//IGameLoop implementation
	@Override
	public void plan(double deltaTime) {
		if(orders.isEmpty())
		{
			behaviour.plan(deltaTime);
			return;
		}
		orders.get(0).plan(deltaTime);
	}
	@Override
	public IEngineAction execute() 
	{
		if(orders.isEmpty())
		{
			behaviour.execute();
			return behaviour;
		}
		return orders.get(0).execute();
	}

	@Override
	public boolean isCompleted(IEngineAction returnedAction) 
	{
		if(returnedAction.equals(behaviour))
		{
			return behaviour.isCompleted(returnedAction);
		}
			
		return returnedAction.isCompleted(returnedAction);
	}

	@Override
	public boolean isRemovable(IEngineAction returnedAction) 
	{
		return false;
	}

	@Override
	public void onComplete(IEngineAction returnedAction) 
	{
		if(returnedAction.equals(behaviour))
		{
			returnedAction.onComplete(returnedAction);
			return;
		}
	
		orders.get(0).onComplete(returnedAction);
		if(orders.get(0).isRemovable(returnedAction))
		{
			orders.remove(0);
		}
	}


	
}
