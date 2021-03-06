package game.engine.actionLoop;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.engine.ActionLoopManager;
import game.engine.actions.IEngineEntityExecution;

public abstract class ActionLoop<AL> implements Runnable
{
	//Action list
	private List<AL> actions;
	
	//Iterator used for navigating the list
	private Iterator<AL> iterator;
	//Time passed from the last loop
	private float deltaTime;
	//Returning list of deleted actions
	private ActionLoopManager<?,?> manager;
	//Object used to sleep and awake the loop
	public Object monitor;
	
	public ActionLoop()
	{
		monitor = new Object();
	}
	
	public void setActions(List<AL> actions)
	{
		this.actions = actions;
		iterator =  actions.iterator();
	}
	public void setManager(ActionLoopManager<?,?> manager)
	{
		this.manager = manager;
	}
	public void setDeltaTime(float deltaTime)
	{
		this.deltaTime = deltaTime;
	}
	public void run() 
	{
		//Single access on monitor
		synchronized(monitor)
		{
			//Lock the access for the action object
			synchronized(actions)
			{
				try
				{
					//Get iterator
					iterator = actions.iterator();
					while(iterator.hasNext())
					{					
						//Get iterator
						AL action = iterator.next();
						
						execute(action, deltaTime);
					}
					
				}
				catch(ConcurrentModificationException e)
				{
					
				}
				
			}
		}
	}
	
	
	public void remove(AL action)
	{
		manager.remove(action);
	}
	
	protected abstract void execute(AL action, float delatTime);

}
