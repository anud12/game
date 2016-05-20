package game.network.services;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import game.geom.classes.PointF;
import game.library.NameCollection;
import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.entity.order.ControllerOrderInterface;
import game.library.player.Player;
import game.library.world.EntityPositionContainer;
import game.library.world.IWorld;
import game.network.component.Session;
import game.network.worldControl.User;

public class WorldControlService extends Service{

	protected IWorld world;
	protected HashMap<Session, User> users;
	protected NameCollection<Player> players;
	
	public WorldControlService(IWorld world, NameCollection<Player> players)
	{
		super();
		this.world = world;
		this.players = players;
		
		users = new HashMap<Session, User>();
	}
	@Override
	protected void process(byte[] array, Session session) 
	{
		if(users.containsKey(session))
		{
			processLoggedIn(array, session);
		}
		
		processNotLoggedIn(array, session);
	}
	protected void processNotLoggedIn(byte[] array, Session session)
	{
		try
		{
			String[] words = new String(array, "ASCII").trim().split(" ");
	
			switch(words[0])
			{
				case "LIST_ALL_PLAYERS":
				{
					StringBuilder response = new StringBuilder();
					
					Iterator<Player> iterator = players.iterator();
					response.append(players.size());
					response.append(" players:\n");
					while(iterator.hasNext())
					{
						Player player = iterator.next();
						response.append(player.getName());
						response.append("\n");
					}
					session.write(response.toString());
					break;
				}
				case"LOGIN":
				{
					if(words.length > 1)
					{
						String name = words[1];
						if(players.contains(name))
						{
							Player player = players.get(name);
							StringBuilder response = new StringBuilder();
							
							User user = new User(session);
							
							users.put(session, user);
							
							player.addUser(user);
							
							response.append("Welcome to ");
							response.append(world.toString());
							response.append("\n");
							response.append("You are now logged in as ");
							response.append(name);
							response.append("\n");
							
							session.write(response.toString());
						}
						else
						{
							session.write("Player not found\n");
						}
						
					}
					break;
				}
			}
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	protected void processLoggedIn(byte[] array, Session session)
	{
		try 
		{
			String[] words = new String(array, "ASCII").trim().split(" ");
			
			switch(words[0])
			{
				case"MOVE":
				{
					
					String id = words[1];
					
					Entity pawn = world.getEntityByID(Integer.parseInt(id));
					if(pawn == null)
					{
						return;
					}
					
					int x = Integer.parseInt(words[2]);
					int y = Integer.parseInt(words[3]);
					PointF destination = new PointF(x , y);
					//pawn.getController().setOrder(new Move(pawn, destination));
					ControllerOrderInterface inter =  pawn.getController().getOrderInterface();
					
					inter.move(destination);
					
					break;
				}
				case"STOP":
				{
					String id = words[1];
					
					Entity pawn = world.getEntityByID(Integer.parseInt(id));
					if(pawn == null)
					{
						return;
					}
					
					pawn.getController().getOrderInterface().stop();
					break;
				}
				case"LOGOUT":
				{
					logoutSession(session);
					
					break;
				}
				case "LIST_ALL_X":
				{
					EntityPositionContainer<Entity> container = world.getEntities();
					
					synchronized(container)
					{
						Iterator<Entity> iterator = container.iterator();
						StringBuilder response = new StringBuilder();
						while(iterator.hasNext())
						{
							Entity ent = iterator.next();
							
							response.append("ID :");
							response.append(ent.get(AttributeSelector.ID()).toString());
							response.append("\n");
							
							response.append(ent.getCenter().toString());
							response.append("\n");
						}
						session.write(response.toString());
					}
					
					break;
				}
				case "LIST_ALL_Y":
				{
					EntityPositionContainer<Entity> container = world.getEntities();
					
					synchronized(container)
					{
						Iterator<Entity> iterator = container.iteratorY();
						
						while(iterator.hasNext())
						{
							Entity ent = iterator.next();
							
							session.write("ID :");
							session.write(ent.get(AttributeSelector.ID()).toString());
							session.write("\n");
							
							session.write(ent.getCenter().toString());
							session.write("\n");
						}
					}
				}
			}
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logoutSession(Session session)
	{
		users.get(session).getPlayer().removeOutputStream(session.getStream());
		users.values().remove(session);
		session.write("Logged out\n");
	}
	
	@Override
	public void onDisconnect(Session session) {
		logoutSession(session);
	}


	@Override
	protected byte[] setHead() {
		// TODO Auto-generated method stub
		return "WORLD".getBytes();
	}

}
