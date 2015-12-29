package game.library.pawn;

import game.geom.classes.PointF;
import game.library.Entity;
import game.library.pawn.controller.PawnController;
import game.library.world.IWorld;

public class Pawn extends Entity {
	protected PawnController controller;
	
	
	public Pawn(int width, int height, PointF origin, IWorld world) {
		super(width, height, origin, world);
		
		controller = new PawnController(this);
		
	}
	
	
	public PawnController getController()
	{
		return controller;
	}
	
}
