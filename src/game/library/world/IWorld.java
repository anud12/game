package game.library.world;

import java.util.Iterator;

import game.engine.IEngineVariable;
import game.library.Entity;

public interface IWorld extends IEngineVariable
{
	public void addEntity(Entity ent);
	public Entity getClosest(String type);
	public Iterator<Entity> getIterator();
	public int getSize();
	public Entity getEntityByID(int id);
	public EntityPositionContainer<Entity> getEntities();
}
