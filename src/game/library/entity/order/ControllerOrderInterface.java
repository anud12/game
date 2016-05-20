package game.library.entity.order;

import game.geom.classes.PointF;
import game.library.attribute.AttributeSelector;
import game.library.entity.engineBridges.EntityController;

public class ControllerOrderInterface 
{
	protected Move move;
	protected None none;
	
	protected EntityController pawnController;
	
	public ControllerOrderInterface(EntityController pawn)
	{
		this.pawnController = pawn;
		
		move = new Move(pawn.getEntity(), new PointF(0,0));
		none = new None();
	}
	
	public void move(PointF destination)
	{
		StringBuilder message = new StringBuilder();
		
		message.append(this.pawnController.getEntity().get(AttributeSelector.ID()));
		message.append(" moving to");
		message.append(" X :");
		message.append(destination.x);
		message.append(" Y :");
		message.append(destination.x);
		message.append("\n");
		message.append((char) 0);
		this.pawnController.writeToPlayers(message.toString().getBytes());
		
		move.setDestination(destination);
		pawnController.setOrder(move);
	}
	public void stop()
	{
		StringBuilder message = new StringBuilder();
		
		message.append(this.pawnController.getEntity().get(AttributeSelector.ID()));
		message.append(" stopped at");
		message.append(" X :");
		message.append(pawnController.getEntity().getCenter().x);
		message.append(" Y :");
		message.append(pawnController.getEntity().getCenter().y);
		message.append("\n");
		message.append((char) 0);
		this.pawnController.writeToPlayers(message.toString().getBytes());
		pawnController.setOrder(none);
	}
}
