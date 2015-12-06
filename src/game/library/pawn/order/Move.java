package game.library.pawn.order;

import game.engine.IEngineAction;
import game.geom.IVector;
import game.geom.classes.PointF;
import game.geom.classes.Vector;
import game.library.attribute.AttributeSelector;
import game.library.pawn.Pawn;

public class Move extends PawnOrder
{

	//Variables
	protected Pawn pawn;
	protected PointF destination;
	
	protected IVector step;
	protected IVector direction;
	
	protected boolean completedPremature;
	
	protected boolean noLenght;
	protected boolean overshoot;
	
	//Constructors
	
	public Move(Pawn pawn, PointF destination)
	{
		this.pawn = pawn;
		
		direction = new Vector();
		step = new Vector();
		
		setDestination(destination);
	}
	//Setters
	public void setDestination(PointF destination)
	{		
		noLenght = false;
		if(pawn.getCenter() != destination)
		{
			this.destination = destination;
			//Calculate the direction
			direction.setX(destination.getX() - pawn.getCenter().getX());
			direction.setY(destination.getY() - pawn.getCenter().getY());
			direction.normalize();
		}
		else
		{
			noLenght = true;
			direction.setX(0);
			direction.setY(0);
		}
		
	}
	
	//Functions from IGameLoop
	
	@Override
	public synchronized void plan(double deltaTime) 
	{
		overshoot = false;
			
		//Calculate the next step to go towards  the destination
		step.equal(direction);
		step.multiplyByScalar((float)pawn.get(AttributeSelector.movementSpeed()) * deltaTime);
				
		//Calculate the overshoot distance
		float extraDistance = (float) (step.getLength() - pawn.getCenter().distance(this.destination));
		
		
		if(extraDistance > 0)
		{
			overshoot = true;
			//Calculate the unused delta time
			this.unusedDeltaTime = (float) ((extraDistance * deltaTime) / step.getLength());
			System.out.println("Step " + step.getLength() + " Extra Distance " + extraDistance + "\nextraTime : " + unusedDeltaTime + " full: " + deltaTime);
			System.out.print("");
		}
	}
	
	@Override
	public synchronized IEngineAction execute() 
	{
		if(noLenght)
			return this;
		
		if(overshoot)
		{
			pawn.getRectangle().setLocation(destination);
			
		}
		else
		{
			//Make the step
			pawn.getRectangle().setLocation(pawn.getCenter().x + step.getX(), pawn.getCenter().y + step.getY());
		}
		
		return this;
	}
	
	@Override
	public boolean isCompleted(IEngineAction action) 
	{
		if(pawn.getCenter().distance(this.destination) == 0 || direction.getLength() == 0)
			return true;
		return false;
	}
	
	@Override
	public void onComplete(IEngineAction action) {
		
	}
	
		


}
