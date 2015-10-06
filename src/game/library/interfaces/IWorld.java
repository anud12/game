package game.library.interfaces;

import java.awt.geom.Point2D;
import java.util.Iterator;

import game.library.Entity;

public interface IWorld 
{
	public void addEntity(Entity ent);
	public Entity getClosest(String type);
	public Iterator<Entity> getIterator();
	public int getSize();
}
