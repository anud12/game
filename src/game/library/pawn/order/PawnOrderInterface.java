package game.library.pawn.order;

import game.geom.classes.PointF;
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
		move.setDestination(destination);
		pawnController.setOrder(move);
	}
	public void stop()
	{
		pawnController.setOrder(none);
	}
}
