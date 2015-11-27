package game.experimental;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import game.library.Entity;
import game.library.world.IWorld;
import game.library.world.sector.SectorGrid;

public class ExperimentalWorld implements IWorld 
{
	protected List<Entity> list;
	
	SectorGrid grid;
	
	public ExperimentalWorld()
	{
		list = new ArrayList<Entity>();
	}
	
	@Override
	public void addEntity(Entity ent) {
		// TODO Auto-generated method stub
		list.add(ent);
	}

	@Override
	public Entity getClosest(String type) {
		
		ListIterator<Entity> iterator = list.listIterator();
		while(iterator.hasNext())
		{
			Entity ent = iterator.next();
			if(ent.containsKeyword(type))
				return ent;
		}
		
		return null;
	}

	@Override
	public Iterator<Entity> getIterator() 
	{
		return list.iterator();
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public Entity getEntityByID(String id) 
	{
		Iterator<Entity> iterator = list.iterator();
		
		while(iterator.hasNext())
		{
			Entity ent = iterator.next();
			if((ent.getStringID() + ":" + ent.getIntID()).equals(id))
			{
				return ent;
			}
			{
				System.out.println("IWorld : " + id + " NOT " + ent.getStringID() + ":" + ent.getIntID()) ;
			}
		}
		return null;
	}

}