package game.library.world;

import java.util.Collection;
import java.util.Iterator;

import game.engine.actions.IEngineUnrelatedUpdate;
import game.library.NameCollection;
import game.library.entity.Entity;
import game.library.inventory.item.Item;

public interface IWorld extends IEngineUnrelatedUpdate
{
	public void addEntity(Entity ent);
	public void removeEntity(Entity ent);
	public Entity getClosest(Entity ent, String type);
	public Iterator<Entity> getIterator();
	public int getSize();
	public Entity getEntityByID(int id);
	public EntityPositionContainer<Entity> getEntities();
	public void clear();
	
	public void addItem (Item item);
	public Item getItemType(String name);
	public boolean hasItem(String name);
	public Collection<Item> getItemList();
}
