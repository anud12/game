package game.library.player;

import java.util.HashMap;

import game.library.entity.Entity;

public class Vision
{
	protected HashMap<Entity, Integer> vision;
	
	public void add(Entity ent)
	{
		if(vision.containsKey(ent))
		{
			vision.put(ent, vision.get(ent) + 1);
		}
		else
		{
			vision.put(ent, 1);
		}
	}
	public void remove (Entity ent)
	{
		if(vision.containsKey(ent))
		{
			if(vision.get(ent) == 1)
			{
				vision.remove(ent);
			}
			else
			{
				vision.put(ent, vision.get(ent) - 1);
			}
		}
	}
	public boolean contains(Entity ent)
	{
		return vision.containsKey(ent);
	}
}
