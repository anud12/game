package game.library.player;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import game.library.Entity;
import game.library.attribute.AttributeSelector;
import game.library.attribute.Attributes;

public class Player implements IHasName
{
	protected String name;
	
	protected HashSet<Entity> ownedEntities;
	
	protected HashSet<OutputStream> outputStreams;
		
	// Constructor //
	public Player()
	{
		initialize();
	}
	
	private void initialize()
	{
		ownedEntities = new HashSet<Entity>();
		outputStreams = new HashSet<>();
	}
	
	// Adders //
	public boolean addEntity(Entity entity)
	{
		return ownedEntities.add(entity);
	}
	public boolean addEntity(Collection<Entity> entityCollection)
	{
		return ownedEntities.addAll(entityCollection);
	}
	public boolean addOutputStream(OutputStream stream)
	{
		return outputStreams.add(stream);
	}
	public boolean addOutputStream(Collection<OutputStream> streamCollection)
	{
		return outputStreams.addAll(streamCollection);
	}
	
	// Removers //
	public boolean removeEntity(Entity entity)
	{
		return ownedEntities.remove(entity);
	}
	public boolean removeEntity(Collection<Entity> entityCollection)
	{
		return ownedEntities.removeAll(entityCollection);
	}
	public boolean removeOutputStream(OutputStream stream)
	{
		return outputStreams.remove(stream);
	}
	public boolean removeOutPutStream(Collection<OutputStream> stream)
	{
		return outputStreams.removeAll(stream);
	}
	
	// Functions //
	public void write(byte[] message)
	{
		Iterator<OutputStream> iterator = outputStreams.iterator();
		while(iterator.hasNext())
		{
			OutputStream stream = iterator.next();
			
			try
			{
				stream.write(message);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
}
