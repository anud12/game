package game.gameLoop;
import game.library.*;
import game.util.WindowLog;

import java.awt.Point;
import java.awt.geom.Point2D;

public class TestAction implements IGameLoopAction
{
	Entity entity;
	Point2D.Float location;
	WindowLog log;
	
	public TestAction(Entity entity)
	{
		this.entity = entity;
	}
	
	public TestAction(Entity entity, WindowLog log)
	{
		this.entity = entity;
		this.log = log;
		log.setTitle(this.toString());
	}
	
	public TestAction(Entity entity, Point2D.Float location)
	{
		this.entity = entity;
		this.location = location;
	}
	
	public void setLocation(Point2D.Float location)
	{
		this.location = location;
	}
	
	@Override
	public boolean execute(double deltaTime) 
	{
		if(entity.getPosition() != this.location)
		{
			
			entity.getRectangle().x += 1 * deltaTime;
			entity.getRectangle().y += 1 * deltaTime;
			
			if(log != null)
				log.println(deltaTime+ " " + "X: " + entity.getRectangle().x + " Y: " + entity.getRectangle().y);
			
		}
			
		return true;
	}

}
