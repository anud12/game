package game.library.entity.order;

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
	public Move(Entity entity)
	{
		this.entity = entity;
		
		direction = new Vector();
		step = new Vector();
	}
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
		if(entity.getData().getCenter() != destination)
		{
			this.destination = destination;
			//Calculate the direction
			direction.setX(destination.getX() - entity.getData().getCenter().getX());
			direction.setY(destination.getY() - entity.getData().getCenter().getY());
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
		step.multiplyByScalar((float)entity.getData().get(AttributeSelector.movementSpeed()) * deltaTime);
				
		//Calculate the overshoot distance
		float extraDistance = (float) (step.getLength() - entity.getData().getCenter().distance(this.destination));
		
		
		if(extraDistance > 0)
		{
			overshoot = true;
			//Calculate the unused delta time
			this.unusedDeltaTime = (float) ((extraDistance * deltaTime) / step.getLength());
		}
	}
	
	@Override
	public synchronized void execute() 
	{
		if(noLenght)
			return;
		
		if(overshoot)
		{
			entity.getData().getRectangle().setLocation(destination);
			
		}
		else
		{
			//Make the step
			entity.getData().setCenter(entity.getData().getCenter().x + (float)step.getX(), entity.getData().getCenter().y + (float)step.getY());
		}
	}
	
	@Override
	public boolean isCompleted() 
	{
		if(entity.getData().getCenter().distance(this.destination) == 0 || direction.getLength() == 0)
			return true;
		return false;
	}
	
	
		


}
