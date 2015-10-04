package game.library.interfaces;

import java.awt.geom.Point2D;

import game.library.Entity;

public interface IWorld 
{
	public void addEntity(Entity ent);
	public Entity getClosest(String type);
}
