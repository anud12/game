package game.library.entity.engineBridges;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import game.engine.actions.IEngineEntityExecution;
import game.engine.actions.IEngineEntityPlan;
import game.library.entity.Entity;
import game.library.entity.IUseEntity;
import game.library.entity.behaviour.EntityBehaviour;
import game.library.entity.behaviour.Idle;
import game.library.entity.order.ControllerOrder;
import game.library.entity.order.ControllerOrderInterface;
import game.library.player.Player;

public class EntityController implements IEngineEntityExecution, IEngineEntityPlan ,IUseEntity
{
	protected ArrayList<ControllerOrder> orders;
	protected Entity entity;
	protected EntityBehaviour behaviour;
	protected ControllerOrderInterface orderInterface;
	protected boolean finished;
	
	public EntityController(Entity entity)
	{
		this.entity = entity;
		orders = new ArrayList<ControllerOrder>();
		
		orderInterface = new ControllerOrderInterface(this);
		
		finished = false;
		
		//TODO:Delete
		behaviour = new Idle();
	}
	//Setter Getters
	
	public void queueOrder(ControllerOrder order)
	{
		orders.add(order);
	}
	
	public void setOrder (ControllerOrder order)
	{
		orders.clear();
		orders.add(order);
	}
	
	public ControllerOrder getFirst()
	{
		if(orders.isEmpty())
			return null;
		return orders.get(0);
	}
	
	public EntityBehaviour getBehaviour()
	{
		return  behaviour;
	}
	public void setBehaviour(EntityBehaviour behaviour)
	{
		this.behaviour = behaviour;
	}
	public Entity getEntity()
	{
		return entity;
	}
	
	public ControllerOrderInterface getOrderInterface()
	{
		return orderInterface;
	}
	
	public int size()
	{
		return orders.size();
	}
	//IGameLoop implementation
	@Override
	public void plan(double deltaTime) 
	{
		if(!finished)
		{
			finished = !entity.getData().isAlive();
		}
		if(finished)
		{
			entity = null;			
			return;
		}
		
		if(orders.isEmpty())
		{
			behaviour.plan(deltaTime);
			return;
		}
		orders.get(0).plan(deltaTime);
	}
	@Override
	public void execute() 
	{
		if(!finished)
		{
			finished = !entity.getData().isAlive();
		}
		if(finished)
		{
			entity = null;			
			return;
		}
		
		if(orders.isEmpty())
		{
			behaviour.execute();
			return;
		}
		orders.get(0).execute();
	}

	@Override
	public boolean isCompleted() 
	{
		if(orders.isEmpty())
		{
			return behaviour.isCompleted();
		}
			
		return orders.get(0).isCompleted();
	}
	@Override
	public void onComplete() 
	{
		if(orders.isEmpty())
		{
			behaviour.onComplete();
			return;
		}
	
		orders.get(0).onComplete();
		if(orders.get(0).isRemovable())
		{
			orders.remove(0);
		}
	}

	@Override
	public boolean isRemovable()
	{
		return finished;
	}
	public void writeToPlayers(byte[] message)
	{
		this.entity.getData().getPlayer().write(message);
		
		Iterator<Player> iter = entity.getVision().getPlayers().iterator();
		
		writeToConsole(message);
		
		while(iter.hasNext())
		{
			iter.next().write(message);
		}
	}
	protected void writeToConsole(byte[] message)
	{
		byte[] endMessage = new byte[message.length + 1];
		for(int i = 0 ; i < message.length ; i++)
		{
			endMessage[i] = message[i];
		}
		try
		{
			String stringMessage = new String(endMessage, "ASCII");
			System.out.println(stringMessage);
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
