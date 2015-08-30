package game.gameLoop;
import game.geom.IVector;
import game.geom.classes.Vector;
import game.library.*;
import game.util.WindowLog;

import java.awt.geom.Point2D;

public class MoveAction implements IGameLoopAction
{
	//Variables
	Pawn pawn;
	Point2D.Float destination;
	
	IVector step;
	IVector direction;
	
	WindowLog log;
	
	//Constructors
	public MoveAction(Pawn entity)
	{
		this.pawn = entity;
		direction = new Vector();
		step = new Vector();
	}
	
	public MoveAction(Pawn entity, Point2D.Float destination, WindowLog log)
	{
		this.pawn = entity;
		
		this.log = log;
		log.setTitle(this.toString());
		
		this.destination = destination;
		
		direction = new Vector();
		step = new Vector();
	}
	
	public MoveAction(Pawn entity, Point2D.Float destination)
	{
		this.pawn = entity;
		this.destination = destination;
	}
	//Setters
	public void setLocation(Point2D.Float location)
	{
		this.destination = location;
	}
	
	
	//Functions from IGameLoop
	@Override
	public boolean execute(double deltaTime) 
	{
		//Check if the destination is reached
		if(pawn.getCenter().distance(this.destination) > 0)
		{
			//Calculate the direction
			direction.setX(destination.getX() - pawn.getRectangle().getX());
			direction.setY(destination.getY() - pawn.getRectangle().getY());
			direction.normalize();
			
			//Calculate the next step to go towards  the destination
			step.equal(direction);
			step.multiplyByScalar(pawn.getMovementSpeed() * deltaTime);
			
			//Check if the step is over the destination
			if(pawn.getCenter().distance(this.destination) < step.getLength())
			{
				//Move directly on top
				pawn.getRectangle().x = destination.x;
				pawn.getRectangle().y = destination.y;
			}
			else
			{
				//Make the step
				pawn.getRectangle().x += step.getX();
				pawn.getRectangle().y += step.getY();
			}
			//Artifact needed to be implemented in Entity
			pawn.setCenter(pawn.getRectangle().x , pawn.getRectangle().y);
			
			//Print message
			if(log != null)
			{
				log.clear();
				log.println("Delta time: " + deltaTime + " Step:"+ step.getLength() + " X: " + pawn.getRectangle().x + " Y: " + pawn.getRectangle().y + " Direction X: " + direction.getX() + " Y:" + direction.getY());
			}
			return false;
		}
		return true;
	}
}
