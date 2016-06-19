package game.engine.cachedActionLoop;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public abstract class CachedActionLoopManager<EngineAction> implements IActionLoopManager<EngineAction>
{
	protected ExecutorService pool;
	
	protected LinkedList<EngineAction> actionList;
	protected LinkedList<EngineAction> removeActionBuffer;
	
	protected abstract void execute(float deltaTime);
	
	public CachedActionLoopManager(int maxThreads)
	{
		pool = Executors.newFixedThreadPool(maxThreads);
		actionList = new LinkedList<>();
	}
	
	public void addAction(EngineAction action)
	{
		synchronized(actionList)
		{
			actionList.add(action);
		}
	}
	public void remove(EngineAction action)
	{
		synchronized(removeActionBuffer)
		{
			removeActionBuffer.add(action);
		}
	}
	
	public void run(float deltaTime)
	{
		synchronized(actionList)
		{
			execute(deltaTime);
			removeFromBuffer();
		}
	}
	
	protected void removeFromBuffer()
	{
		synchronized(removeActionBuffer)
		{
			Iterator<EngineAction> iterator = removeActionBuffer.iterator();
			while(iterator.hasNext())
			{
				EngineAction action = iterator.next();
				
				actionList.remove(action);
				removeActionBuffer.remove(action);
			}
		}
	}
	
}
