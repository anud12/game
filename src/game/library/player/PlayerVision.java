package game.library.player;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import game.experimental.ExperimentalWorld;
import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.vision.Vision;
import game.library.world.IWorld;

public class PlayerVision extends Vision
{
	protected IWorld world;
	protected Player player;
	
	public PlayerVision(Player player)
	{
		this.player = player;
		world = new ExperimentalWorld();
	}
	@Override
	public Set<Player> getPlayers()
	{
		return null;
	}

	
	public synchronized void addEntity(Entity entity)
	{
		world.addEntity(entity);
		System.out.println(player.getName() + " ADDED " + entity.getData().get(AttributeSelector.ID()));
	}
	public synchronized void addEntity(Collection<Entity> entityCollection)
	{
		Iterator<Entity> iterator = entityCollection.iterator();
		while(iterator.hasNext())
		{
			world.addEntity(iterator.next());
		}
	}
	public synchronized void removeEntity(Entity entity)
	{
		world.removeEntity(entity);
		System.out.println(player.getName() + "REMOVED " + entity.getData().get(AttributeSelector.ID()));
	}
	public synchronized void removeEntity(Collection<Entity> entityCollection)
	{
		Iterator<Entity> iterator = entityCollection.iterator();
		while(iterator.hasNext())
		{
			world.removeEntity(iterator.next());
		}
	}
	public IWorld getWorld()
	{
		return world;
	}

}
