package game.network.services;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import game.geom.classes.PointF;
import game.library.Entity;
import game.library.attribute.AttributeSelector;
import game.library.pawn.Pawn;
import game.library.pawn.order.PawnOrderInterface;
import game.library.world.EntityPositionContainer;
import game.library.world.IWorld;
import game.network.component.Session;

public class WorldControlService extends Service{

	protected IWorld world;
	
	public WorldControlService(IWorld world)
	{
		super();
		this.world = world;
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

	@Override
	public void onDisconnect(Session session) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected byte[] setHead() {
		// TODO Auto-generated method stub
		return "WORLD".getBytes();
	}

}
