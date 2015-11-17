package game.engine;

import java.util.Iterator;
import java.util.List;

class ActionLoop implements Runnable{
	//Action list
	private List<IEngineAction> actions;
	//Returning list of deleted actions
	private List<IEngineAction> removeBuffer;
	//Iterator used for navigating the list
	private Iterator<IEngineAction>iterator;
	//Time passed from the last loop
	private float deltaTime;
	//
	private boolean isPlanning;
	//Object used to sleep and awake the loop
	public Object monitor;
	
	public ActionLoop(List<IEngineAction> removeBuffer)
	{
		this.removeBuffer = removeBuffer;
		monitor = new Object();
	}
	
	public void setActions(List<IEngineAction> actions)
	{
		this.actions = actions;
		iterator =  actions.iterator();
	}
	public void setRemoveBuffer(List<IEngineAction> removeBuffer)
	{
		this.removeBuffer = removeBuffer;
	}
	
	public void setDeltaTime(float deltaTime)
	{
		this.deltaTime = deltaTime;
	}
	
	public void setToPlanning()
	{
		isPlanning = true;
	}
	public void setToExecution()
	{
		isPlanning = false;
	}
	public void run() 
	{
		//Check if the loop will do a 
		//planning run or an execution run
		if(isPlanning)
		{
			plan();
		}
		else
		{
			execute();
		}
	}
	
	private void plan()
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
					
					//Execute current action and grab the returned executed action
					action.plan(deltaTime);
					
				}
			}
		}
	}
	
	private void execute()
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
					
					//Execute current action and grab the returned executed action
					IEngineAction returnedAction = action.execute();
					
					if(action.isCompleted(returnedAction))
					{
		            	action.onComplete(returnedAction);
		            	
		            	if(action.isRemovable(returnedAction))
		            	{
		            		//Single access on removeBuffer
		            		synchronized(removeBuffer)
			            	{
			            		removeBuffer.add(action);
			            	}
		            	}
                    }
				}
			}
		}
	}
	
}
