package game.library;

import java.awt.geom.Point2D.Float;

public class Pawn extends Entity {

	protected float movementSpeed;
	
	public Pawn(int width, int height, Float origin) {
		super(width, height, origin);
		// TODO Auto-generated constructor stub
	}
	
	
	public float getMovementSpeed()
	{
		return movementSpeed;
	}
	
	public void setMovementSpeed(float movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}
}
