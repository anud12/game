package game.library.entity.order;

import java.util.ArrayList;

import game.geom.classes.PointF;
import game.library.attribute.AttributeSelector;
import game.library.entity.engineBridges.EntityController;

public class ControllerOrderInterface 
{
	protected Move move;
	protected None none;
	
	protected ArrayList<ControllerOrder> orders;
	
	protected EntityController pawnController;
	
	public ControllerOrderInterface(EntityController pawn)
	{
		this.pawnController = pawn;
		
		orders = new ArrayList<>();
		
		move = new Move(pawn.getEntity());
		none = new None();
	}
	
	public ArrayList<ControllerOrder> getOrders()
	{
		return orders;
	}
	
	public void move(PointF destination)
	{
		StringBuilder message = new StringBuilder();
		
		message.append(this.pawnController.getEntity().getData().get(AttributeSelector.ID()));
		message.append(" moving to");
		message.append(" X :");
		message.append(destination.x);
		message.append(" Y :");
		message.append(destination.x);
		message.append("\n");
		message.append((char) 0);
		this.pawnController.writeToPlayers(message.toString().getBytes());
		
		move.setDestination(destination);
		
		orders.clear();
		orders.add(move);
	}
	public void stop()
	{
		StringBuilder message = new StringBuilder();
		
		message.append(this.pawnController.getEntity().getData().get(AttributeSelector.ID()));
		message.append(" stopped at");
		message.append(" X :");
		message.append(pawnController.getEntity().getData().getCenter().x);
		message.append(" Y :");
		message.append(pawnController.getEntity().getData().getCenter().y);
		message.append("\n");
		message.append((char) 0);
		this.pawnController.writeToPlayers(message.toString().getBytes());
		
		orders.clear();
		orders.add(none);
	}
	public void queueOrder(ControllerOrder order)
	{
		orders.add(order);
	}
}
