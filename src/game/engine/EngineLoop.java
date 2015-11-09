package game.engine;

import java.util.Iterator;
import java.util.List;

class EngineLoop implements Runnable{
	//Action list
	private List<IEngineAction> actions;
	//Returning list of deleted actions
	private List<IEngineAction> removeBuffer;
	//Iterator used for navigating the list
	private Iterator<IEngineAction>iterator;
	//Time passed from the last loop
	private float deltaTime;
	//Endless loop check
	private boolean exitLoop;
	//If loop is completed
	private boolean waiting;
	//Object used to sleep and awake the loop
	public Object monitor;
	
	public EngineLoop(List<IEngineAction> removeBuffer)
	{
		this.removeBuffer = removeBuffer;
		exitLoop = false;
		monitor = new Object();
	}
	
	public void setActions(List<IEngineAction> actions)
	{
		this.actions = actions;
		waiting = false;
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
	public void setExitLoop(boolean exitLoop)
	{
		this.exitLoop = exitLoop;
	}
	public synchronized boolean isWaiting()
	{
		return waiting;
	}
	public void run() 
	{
		//Single access on monitor
		synchronized(monitor)
		{
			//Liveness of the thread is maintained by
			//this endless loop that can be killed by
			//making it stop
			{
				//Wait until notified by the engine
				synchronized(actions)
				{
					//Get iterator
					iterator = actions.iterator();
					while(iterator.hasNext())
					{
						//Set that the loop is running
						waiting = false;
						
						//Get iterator
						IEngineAction action = iterator.next();
						
						//Execute current action
						action.execute(deltaTime);
						
						if(action.isCompleted(action))
						{
			            	action.onComplete(action);
			            	
			            	if(action.isRemovable(action))
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
	
}
