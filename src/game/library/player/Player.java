package game.library.player;


import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import game.library.IHasName;
import game.library.entity.Entity;
import game.network.worldControl.User;

public class Player implements IHasName
{
	protected String name;
	
	protected HashSet<Entity> ownedEntities;
	
	protected HashSet<User> networkUsers;
	
	protected Vision vision;
		
	// Constructor //
	public Player()
	{
		initialize();
	}
	
	private void initialize()
	{
		ownedEntities = new HashSet<Entity>();
		networkUsers = new HashSet<>();
		vision = new Vision();
	}
	
	// Getters //
	public Vision getVision()
	{
		return vision;
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
	public boolean addUser(User user)
	{
		return networkUsers.add(user);
	}
	public boolean addUser(Collection<User> user)
	{
		return networkUsers.addAll(user);
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
		return networkUsers.remove(stream);
	}
	public boolean removeOutPutStream(Collection<OutputStream> stream)
	{
		return networkUsers.removeAll(stream);
	}
	
	// Functions //
	public void write(byte[] message)
	{
		byte[] endMessage = new byte[message.length + 1];
		for(int i = 0 ; i < message.length ; i++)
		{
			endMessage[i] = message[i];
		}
		endMessage[message.length] = 0;
		
		Iterator<User> iterator = networkUsers.iterator();
		while(iterator.hasNext())
		{
			User user = iterator.next();
			
			user.write(endMessage);
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
