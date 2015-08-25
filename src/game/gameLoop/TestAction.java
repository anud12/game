package game.gameLoop;
import game.library.*;
import game.util.WindowLog;

import java.awt.Point;

public class TestAction implements IGameLoopAction
{
	Entity entity;
	Point location;
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
	
	public TestAction(Entity entity, Point location)
	{
		this.entity = entity;
		this.location = location;
	}
	
	public void setLocation(Point location)
	{
		this.location = location;
	}
	
	@Override
	public boolean execute(double deltaTime) 
	{
		if(entity.getPosition() != this.location)
		{
			
			entity.getRectangle().translatef(1*(int)deltaTime, 1*(int)deltaTime);
			if(log != null)
				log.println(deltaTime+ " " + "X: " + entity.getPosition().x + " Y: " + entity.getPosition().y);
			
		}
			
		return true;
	}

}
