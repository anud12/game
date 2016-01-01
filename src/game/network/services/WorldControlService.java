package game.network.services;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

import game.geom.classes.PointF;
import game.library.Entity;
import game.library.NameCollection;
import game.library.attribute.AttributeSelector;
import game.library.pawn.Pawn;
import game.library.pawn.order.PawnOrderInterface;
import game.library.player.Player;
import game.library.world.EntityPositionContainer;
import game.library.world.IWorld;
import game.network.component.Session;

public class WorldControlService extends Service{

	protected IWorld world;
	protected HashMap<String, Session> users;
	protected NameCollection<Player> players;
	
	public WorldControlService(IWorld world, NameCollection players)
	{
		super();
		this.world = world;
		this.players = players;
		users = new HashMap<String, Session>();
	}
	@Override
	protected void process(byte[] array, Session session) 
	{
		try 
		{
			String[] words = new String(array, "ASCII").trim().split(" ");
			
			switch(words[0])
			{
				case"MOVE":
				{
					
					String id = words[1];
					
					Pawn pawn = (Pawn)world.getEntityByID(Integer.parseInt(id));
					if(pawn == null)
					{
						return;
					}
					
					int x = Integer.parseInt(words[2]);
					int y = Integer.parseInt(words[3]);
					PointF destination = new PointF(x , y);
					//pawn.getController().setOrder(new Move(pawn, destination));
					PawnOrderInterface inter =  pawn.getController().getOrderInterface();
					
					inter.move(destination);
					
					break;
				}
				case"STOP":
				{
					String id = words[1];
					
					Pawn pawn = (Pawn)world.getEntityByID(Integer.parseInt(id));
					if(pawn == null)
					{
						return;
					}
					
					pawn.getController().getOrderInterface().stop();
					break;
				}
				case"LOGIN":
				{
					if(words.length > 1)
					{
						String name = words[1];
						if(players.contains(name))
						{
							users.put(session.getAddress().toString(), session);
							
							StringBuilder response = new StringBuilder();
							
							response.append("Welcome to ");
							response.append(world.toString());
							response.append("\nYou are now logged in as ");
							response.append(name);
							response.append("\n");
							
							session.write(response.toString());
							
							players.get(name).addOutputStream(session.getStream());
							
							session.getAttributes().set("bWorldLoggedIn", true);
						}
						else
						{
							session.write("Player not found\n");
						}
						
					}
					break;
				}
				case"LOGOUT":
				{
					session.write("Logging out\n");
					logoutSession(session);
					
					break;
				}
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
				case "LIST_ALL_X":
				{
					EntityPositionContainer<Entity> container = world.getEntities();
					
					synchronized(container)
					{
						Iterator<Entity> iterator = container.iterator();
						
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
		session.getAttributes().set("bWorldLoggedIn", false);
		users.values().remove(session);
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
