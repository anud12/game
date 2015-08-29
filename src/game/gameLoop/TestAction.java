package game.gameLoop;
import game.geom.IVector;
import game.geom.classes.Vector;
import game.library.*;
import game.util.WindowLog;

import java.awt.Point;
import java.awt.geom.Point2D;

public class TestAction implements IGameLoopAction
{
	Pawn pawn;
	Point2D.Float destination;
	IVector direction;
	WindowLog log;
	
	public TestAction(Pawn entity)
	{
		this.pawn = entity;
		direction = new Vector();
	}
	
	public TestAction(Pawn entity, WindowLog log)
	{
		this.pawn = entity;
		this.log = log;
		log.setTitle(this.toString());
		
		direction = new Vector();
	}
	
	public TestAction(Pawn entity, Point2D.Float location)
	{
		this.pawn = entity;
		this.destination = location;
	}
	
	public void setLocation(Point2D.Float location)
	{
		this.destination = location;
	}
	
	@Override
	public boolean execute(double deltaTime) 
	{
		
		if(pawn.getCenter().distance(this.destination) > 0)
		{
			direction.setX(destination.getX() - pawn.getRectangle().getX());
			direction.setY(destination.getY() - pawn.getRectangle().getY());
			direction.normalize();
			
			
			pawn.getRectangle().x += pawn.getMovementSpeed() * direction.getX() * deltaTime;
			pawn.getRectangle().y += pawn.getMovementSpeed() * direction.getY() * deltaTime;
			
			pawn.setCenter(pawn.getRectangle().x , pawn.getRectangle().y);
			
			if(log != null)
			{
				log.println("Delta time: " + deltaTime + " " + "X: " + pawn.getRectangle().x + " Y: " + pawn.getRectangle().y + " Direction X: " + direction.getX() + " Y:" + direction.getY());
			}
				
			
		}
			
		return true;
	}

}
