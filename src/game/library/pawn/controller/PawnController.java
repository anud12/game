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
	
	public int size()
	{
		return orders.size();
	}
	//IGameLoop implementation
	@Override
	public IEngineAction execute(double deltaTime) {
		if(orders.isEmpty())
		{
			return behaviour.execute(deltaTime);
		}
		return orders.get(0).execute(deltaTime);
	}

	@Override
	public boolean isCompleted(IEngineAction returnedAction) {
		return returnedAction.isCompleted(returnedAction);
	}

	@Override
	public boolean isRemovable(IEngineAction returnedAction) {
		return false;
	}

	@Override
	public void onComplete(IEngineAction returnedAction) {
		if(orders.isEmpty())
			return;
		orders.get(0).onComplete(returnedAction);
		if(orders.get(0).isRemovable(returnedAction))
		{
			orders.remove(0);
		}
	}
}
