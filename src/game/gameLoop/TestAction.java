package game.gameLoop;
import game.library.*;
import java.awt.Point;

public class TestAction implements IGameLoopAction
{
	Entity entity;
	Point location;
	
	public TestAction(Entity entity)
	{
		this.entity = entity;
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
		System.out.print(deltaTime+ " ");
		
		if(entity.getPosition() != this.location)
		{
			
			entity.getRectangle().translatef(1*(int)deltaTime, 1*(int)deltaTime);
			System.out.println("X: " + entity.getPosition().x + " Y: " + entity.getPosition().y);
			
		}
			
		return true;
	}

}
