package game.experimental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import game.library.NameCollection;
import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.inventory.item.Item;
import game.library.world.EntityPositionContainer;
import game.library.world.IWorld;
import game.library.world.sector.SectorGrid;

public class ExperimentalWorld implements IWorld
{
	protected List<Entity> list;
	
	protected SectorGrid grid;
	protected EntityPositionContainer<Entity> locationList;
	
	protected NameCollection<Item> item;
	
	public ExperimentalWorld()
	{
		list = new ArrayList<Entity>();
		locationList = new EntityPositionContainer<Entity>();
		
		item = new NameCollection<>();
	}
	
	@Override
	public void addEntity(Entity ent) 
	{
		list.add(ent);
		locationList.add(ent);
	}
	@Override
	public void removeEntity(Entity ent)
	{
		list.remove(ent);
		locationList.remove(ent);
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
	public Entity getEntityByID(int id) 
	{
		Iterator<Entity> iterator = list.iterator();
		
		while(iterator.hasNext())
		{
			Entity ent = iterator.next();
			if((ent.get(AttributeSelector.ID()).equals(id)))
			{
				return ent;
			}
		}
		return null;
	}

	@Override
	public void unrelatedUpdate()
	{
		locationList.sort();
	}

	@Override
	public EntityPositionContainer<Entity> getEntities()
	{
		return locationList;
	}

	@Override
	public void addItem(Item item)
	{
		this.item.add(item);
	}

	@Override
	public Item getItemType(String name)
	{
		return item.get(name);
	}

	@Override
	public boolean hasItem(String name)
	{
		return item.contains(name);
	}

	@Override
	public void clear()
	{
		item.clear();
		locationList.clear();
	}

	@Override
	public Collection<Item> getItemList()
	{
		return item;
	}
	
	

}