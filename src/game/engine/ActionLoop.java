package game.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

abstract class ActionLoop implements Runnable{
	//Action list
	private List<IEngineAction> actions;
	
	//Iterator used for navigating the list
	private Iterator<IEngineAction>iterator;
	//Time passed from the last loop
	private float deltaTime;
	//Returning list of deleted actions
	private ActionLoopManager<?> manager;
	//Object used to sleep and awake the loop
	public Object monitor;
	
	public ActionLoop()
	{
		monitor = new Object();
	}
	
	public void setActions(List<IEngineAction> actions)
	{
		this.actions = actions;
		iterator =  actions.iterator();
	}
	public void setManager(ActionLoopManager<?> manager)
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
				//Get iterator
				iterator = actions.iterator();
				while(iterator.hasNext())
				{					
					//Get iterator
					IEngineAction action = iterator.next();
					
					execute(action, deltaTime);
				}
			}
		}
	}
	
	
	public void remove(IEngineAction action)
	{
		manager.remove(action);
	}
	
	protected abstract void execute(IEngineAction action, float delatTime);

}
