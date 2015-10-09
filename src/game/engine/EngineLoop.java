package game.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;

class EngineLoop extends Thread{
	
	private List<IEngineAction> actions;
	private List<IEngineAction> removeBuffer;
	private float deltaTime;
	private boolean exitLoop;
	private boolean waiting;
	
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
		synchronized(monitor)
		{
			do
			{
				if(this.getState() == Thread.State.RUNNABLE)
				{
					try {
						waiting = true;
						monitor.wait();
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//System.out.println(this + " " + deltaTime);
				Iterator<IEngineAction> iterator = actions.iterator();
				while(iterator.hasNext())
				{
					waiting = false;
					
					IEngineAction action = iterator.next();
					
					action.execute(deltaTime);
					
					if(action.isCompleted(action))
					{
		            	action.onComplete(action);
		            	
		            	if(action.isRemovable(action));
		            	{
		            		synchronized(removeBuffer)
			            	{
			            		removeBuffer.add(action);	
			            	}
		            	}
                    }
				}
				
			}
			while(!exitLoop);
		}
	}
	
}
