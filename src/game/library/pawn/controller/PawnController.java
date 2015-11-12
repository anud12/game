package game.library.pawn.controller;

import java.util.ArrayList;

import game.engine.IEngineAction;
import game.library.pawn.Pawn;
import game.library.pawn.order.PawnOrder;

public class PawnController implements IEngineAction
{
	ArrayList<PawnOrder> orders;
	Pawn pawn;
	
	public PawnController(Pawn pawn)
	{
		this.pawn = pawn;
		orders = new ArrayList<PawnOrder>();
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
	public void execute(double deltaTime) {
		if(orders.isEmpty())
			return;
		orders.get(0).execute(deltaTime);
	}

	@Override
	public boolean isCompleted(IEngineAction action) {
		if(orders.isEmpty())
			return true;
		return orders.get(0).isCompleted(action);
		//return false;
	}

	@Override
	public boolean isRemovable(IEngineAction action) {
		return false;
	}

	@Override
	public void onComplete(IEngineAction action) {
		if(orders.isEmpty())
			return;
		orders.get(0).onComplete(action);
		if(orders.get(0).isRemovable(action))
		{
			orders.remove(0);
		}
	}
}
