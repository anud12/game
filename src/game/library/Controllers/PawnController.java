package game.library.Controllers;

import java.util.ArrayList;

import game.gameLoop.IGameLoopAction;
import game.library.Pawn;
import game.library.interfaces.IPawnOrder;

public class PawnController implements IGameLoopAction
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
	
	
	//IGameLoop implementation
	@Override
	public void execute(double deltaTime) {
		if(orders.isEmpty())
			return;
		orders.get(0).execute(deltaTime);
		
	}

	@Override
	public boolean isCompleted(IGameLoopAction action) {
		if(orders.isEmpty())
			return true;
		return orders.get(0).isCompleted(action);
	}

	@Override
	public boolean isRemovable(IGameLoopAction action) {
		return false;
	}

	@Override
	public void onComplete(IGameLoopAction action) {
		if(orders.isEmpty())
			return;
		orders.get(0).onComplete(action);
		orders.remove(0);
	}
}
