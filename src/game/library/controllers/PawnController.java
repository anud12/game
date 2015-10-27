package game.library.controllers;

import java.util.ArrayList;

import game.engine.IEngineAction;
import game.library.Pawn;
import game.library.interfaces.IPawnOrder;

public class PawnController implements IEngineAction
{
	ArrayList<IPawnOrder> orders;
	Pawn pawn;
	
	public PawnController(Pawn pawn)
	{
		this.pawn = pawn;
		orders = new ArrayList<IPawnOrder>();
	}
	//Setter Getters
	
	
	public void queueOrder(IPawnOrder order)
	{
		orders.add(order);
	}
	
	public void setOrder (IPawnOrder order)
	{
		orders.clear();
		orders.add(order);
	}
	public IPawnOrder getFirst()
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
