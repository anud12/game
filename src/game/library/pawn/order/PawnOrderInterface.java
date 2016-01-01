package game.library.pawn.order;

import game.geom.classes.PointF;
import game.library.attribute.AttributeSelector;
import game.library.pawn.controller.PawnController;

public class PawnOrderInterface 
{
	protected Move move;
	protected None none;
	
	protected PawnController pawnController;
	
	public PawnOrderInterface(PawnController pawn)
	{
		this.pawnController = pawn;
		
		move = new Move(pawn.getPawn(), new PointF(0,0));
		none = new None();
	}
	
	public void move(PointF destination)
	{
		StringBuilder message = new StringBuilder();
		
		message.append(this.pawnController.getPawn().get(AttributeSelector.ID()));
		message.append(" moving to");
		message.append(" X :");
		message.append(destination.x);
		message.append(" Y :");
		message.append(destination.x);
		message.append("\n");
		message.append((char) 0);
		this.pawnController.getPawn().getPlayer().write(message.toString().getBytes());
		
		move.setDestination(destination);
		pawnController.setOrder(move);
	}
	public void stop()
	{
		StringBuilder message = new StringBuilder();
		
		message.append(this.pawnController.getPawn().get(AttributeSelector.ID()));
		message.append(" stopped at");
		message.append(" X :");
		message.append(pawnController.getPawn().getCenter().x);
		message.append(" Y :");
		message.append(pawnController.getPawn().getCenter().y);
		message.append("\n");
		message.append((char) 0);
		this.pawnController.getPawn().getPlayer().write(message.toString().getBytes());
		pawnController.setOrder(none);
	}
}
