package game.library.entity.order;

import game.engine.actions.IEngineEntityExecution;
import game.engine.actions.IEngineEntityPlan;
import game.geom.IVector;
import game.geom.classes.PointF;
import game.geom.classes.Vector;
import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;

public class Move extends ControllerOrder
{

	//Variables
	protected Entity entity;
	protected PointF destination;
	
	protected IVector step;
	protected IVector direction;
	
	protected boolean completedPremature;
	
	protected boolean noLenght;
	protected boolean overshoot;
	
	//Constructors
	
	public Move(Entity entity, PointF destination)
	{
		this.entity = entity;
		
		direction = new Vector();
		step = new Vector();
		
		setDestination(destination);
	}
	//Setters
	public void setDestination(PointF destination)
	{		
		noLenght = false;
		if(entity.getCenter() != destination)
		{
			this.destination = destination;
			//Calculate the direction
			direction.setX(destination.getX() - entity.getCenter().getX());
			direction.setY(destination.getY() - entity.getCenter().getY());
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
		step.multiplyByScalar((float)entity.get(AttributeSelector.movementSpeed()) * deltaTime);
				
		//Calculate the overshoot distance
		float extraDistance = (float) (step.getLength() - entity.getCenter().distance(this.destination));
		
		
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
	public synchronized void execute() 
	{
		if(noLenght)
			return;
		
		if(overshoot)
		{
			entity.getRectangle().setLocation(destination);
			
		}
		else
		{
			//Make the step
			entity.setCenter(entity.getCenter().x + (float)step.getX(), entity.getCenter().y + (float)step.getY());
		}
	}
	
	@Override
	public boolean isCompleted() 
	{
		if(entity.getCenter().distance(this.destination) == 0 || direction.getLength() == 0)
			return true;
		return false;
	}
	
	@Override
	public void onComplete() 
	{
	}
	
		


}
