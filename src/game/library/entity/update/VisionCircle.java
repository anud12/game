package game.library.entity.update;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.player.Player;
import game.library.world.EntityPositionContainer;
import game.library.world.IWorld;

public class VisionCircle extends Vision
{
	protected int maxTargets;
	protected Float radius;
	
	protected LinkedList<Entity> entities;
	protected HashMap<Player, Integer> players;
	protected int visionSize;
	
	protected boolean isAlive;
	
	public VisionCircle(float radius, int maxTargets)
	{
		this.radius = radius;
		
		this.entities = new LinkedList<Entity>();
		
		this.players = new HashMap<>();
		
		this.maxTargets = maxTargets;
	}
	
	@Override
	public void relatedUpdate(Entity entity)
	{		
		visionCheck(entity);
		visionAddSearch(entity);
	}
	
	public void visionCheck(Entity entity)
	{
		Iterator<Entity> iterator = entities.iterator();
		LinkedList <Entity> removeBuffer = new LinkedList<>();
		while(iterator.hasNext())
		{
			Entity target = iterator.next();
			double distance = entity.getData().getCenter().distance(target.getData().getCenter());
			if(distance > radius)
			{
				removeBuffer.add(target);
				
				StringBuilder string = new StringBuilder();
				string.append(entity.getData().get(AttributeSelector.ID()));
				string.append(" lost vision on ");
				string.append(target.getData().get(AttributeSelector.ID()));
				string.append("\n");
				entity.getData().getPlayer().write(string.toString().getBytes());
				
				Player player = target.getData().getPlayer();
				
				if(players.containsKey(player))
				{
					players.put(player, players.get(player) - 1);
				}
				else
				{
					players.put(player, 0);
				}
				
				visionSize--;
				
			}
		}
		Iterator<Player> playerIterator = players.keySet().iterator();
		while(playerIterator.hasNext())
		{
			Player player = playerIterator.next();
			if(players.get(player) <= 0)
			{
				players.remove(player);
			}
		}
		entities.removeAll(removeBuffer);
	}
	public void visionAdd(Entity entity ,Entity target, double distance)
	{
		if(distance < radius && !entities.contains(target) && target!=entity  && (visionSize < maxTargets))
		{
			StringBuilder string = new StringBuilder();
			string.append(entity.getData().get(AttributeSelector.ID()));
			string.append(" gained vision on ");
			string.append(target.getData().get(AttributeSelector.ID()));
			string.append("\n");
			entity.getData().getPlayer().write(string.toString().getBytes());
			
			entities.add(target);
			
			Player player = target.getData().getPlayer();
			
			if(players.containsKey(player))
			{
				players.put(player, players.get(player) + 1);
			}
			else
			{
				players.put(player, 1);
			}
			
			visionSize++;
		}
		
		
	}
	public void visionAddSearch(Entity entity)
	{
		EntityPositionContainer<Entity> container = entity.getWorld().getEntities();
		
		int positiveX = container.indexOf(entity);
		int negativeX = positiveX;
		
		int positiveY = container.indexOfY(entity);
		int negativeY = positiveY;
		
		
		double distance = 0;
		
		boolean positiveXEnd = true;
		boolean positiveYEnd = true;
		boolean negativeXEnd = true;
		boolean negativeYEnd = true;
		
		
		while(distance < radius && (visionSize < maxTargets) && (positiveXEnd && positiveYEnd && negativeXEnd && negativeYEnd))
		{
			positiveX++;
			negativeX--;
			
			positiveY++;
			negativeY--;
			
			if(positiveX < container.size())
			{
				
				Entity target = container.get(positiveX);
				distance = entity.getData().getCenter().distance(target.getData().getCenter());
				
				visionAdd(entity, target, distance);
			}
			else
			{
				positiveXEnd = false;
			}
			
			if(negativeX >= 0)
			{
				
				Entity target = container.get(negativeX);
				distance = entity.getData().getCenter().distance(target.getData().getCenter());
			
				visionAdd(entity, target, distance);
			}
			else
			{
				negativeXEnd = false;
			}
			
			if(positiveY < container.size())
			{
				
				Entity target = container.getY(positiveY);
				distance = entity.getData().getCenter().distance(target.getData().getCenter());
				
				visionAdd(entity, target, distance);
			}
			else
			{
				positiveYEnd = false;
			}
			
			if(negativeY >= 0)
			{
				
				Entity target = container.getY(negativeY);
				distance = entity.getData().getCenter().distance(target.getData().getCenter());
				
				visionAdd(entity, target, distance);
			}
			else
			{
				negativeYEnd = false;
			}
			
			System.out.print("");
		}
	}

	@Override
	public float getRadius()
	{
		return radius;
	}
	public Set<Player> getPlayers()
	{
		return players.keySet();
	}

}
