package game.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Engine implements Runnable
{
	//Action lists to do for every cycle
	CopyOnWriteArrayList<IEngineAction> actions;
	
    Iterator<IEngineAction> actionIterator;
    
    //Total time elapsed
    long time;
    float deltaTime;
    // MultiThread //
    
    //Number of actions when a new loop will be created
    int actionsToSplit; 
    //Number of maximum running parallel loops
    int maxThreads;
    //Size of the sublist to be sent
    int subListSize;
    //List of loops running in parallel 
    LinkedList<EngineLoop> loops;
    
    //Buffer list used in modifying  actions
    //in between the loop iterations
    LinkedList<IEngineAction> addBuffer;
    LinkedList<IEngineAction> removeBuffer;
    
    
    //Initializations
    public Engine(int actionsToSplit, int maxThreads)
    {
    	if(actionsToSplit == 0)
    		this.actionsToSplit = Integer.MAX_VALUE;
    	else
    		this.actionsToSplit = actionsToSplit;
    	
    	if(maxThreads == 0)
        	this.maxThreads = Integer.MAX_VALUE;
    	else
    		this.maxThreads = maxThreads; 	
    	
        actions = new CopyOnWriteArrayList<IEngineAction>();
        addBuffer = new LinkedList<>();
        removeBuffer = new LinkedList<>();
        
        loops = new LinkedList<EngineLoop>();    
    }
    
    //Add to stack
    public void addAction(IEngineAction action)
    {
    	//Add action to buffer
    	addBuffer.add(action);
    }
    
    protected void modifyActionList()
    {
    	//Check if the buffer lists aren't empty
    	if(removeBuffer.size() ==  0  && addBuffer.size() == 0)
    	{
    		return;
    	}
    	//Add & remove actions from the buffer
    	actions.removeAll(removeBuffer);
    	actions.addAll(addBuffer);
    	
    	//Clear the buffer
    	removeBuffer =  new LinkedList<>();
    	addBuffer = new LinkedList<>();
    	
    	//Check if it it needs to split the actions &
    	//if the maximum number of loops have been reached
    	while((actions.size() / actionsToSplit + 1 > loops.size()) && (loops.size() < maxThreads))
		{
    		EngineLoop loop = new EngineLoop(removeBuffer);
    		loops.add(loop);
    		
    		loop.setName("Loop " + (loops.size() -1) );
    		loop.start();
		}
    	//Calculate the size of the sublists
    	subListSize = actions.size() / loops.size();
    }
    
    //Start engine loop
    @Override
	public void run()  
	{
    	time = System.nanoTime();
    	
    	while(true)
    	{
			try {
				update();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    //Update the game
    void update() throws InterruptedException
    {
    	//Get the time between frames
    	deltaTime = ( System.nanoTime() - time ) / 1000000.0f;
    	
    	//Update time to current
    	time = System.nanoTime();
    	
    	//Grab iterator for the action lists
    	actionIterator = actions.iterator();
    	
    	
    	//Check the frequency of the calls
    	if(deltaTime < 0.9)
    	{
    		try
    		{
    			//Wait until the minimum time
    			Thread.sleep(0 ,(int) (900000 - deltaTime * 1000000) );
    			deltaTime = 0.9f;
    		}
    		catch(Exception e)
    		{
    		}
    	}
    	
    	//Configure the engineLoops
    	Iterator<EngineLoop> loopIterator = loops.iterator();
    	
    	int loopNumber = 0;
    	while(loopIterator.hasNext())
    	{
    		EngineLoop loop = loopIterator.next();
    		
    		//Slice the list of actions in smaller lists
    		//equal in size for the loops
    		if(loopNumber == 0)
    		{
    			loop.setActions( actions.subList( subListSize * loopNumber, subListSize * (loopNumber + 1)) );
    		}
    		else
    		{
    			loop.setActions( actions.subList( subListSize * loopNumber + 1, subListSize * (loopNumber + 1)) );
    		}
    		loopNumber++;
    		
    		//Set the deltaTime for the loop
    		loop.setDeltaTime(deltaTime);
    	}
    	
    	loopIterator = loops.iterator();
    	
    	//Run the loops
    	while(loopIterator.hasNext())
    	{
    		EngineLoop loop = loopIterator.next();
    		synchronized(loop.monitor)
    		{
    			loop.monitor.notify();
    		}
    	}
    	
    	
    	// Asking if the loops are finished
    	
    	Iterator<EngineLoop> iterator = loops.iterator();
    	while(iterator.hasNext())
    	{
    		EngineLoop loop = iterator.next();
    		while(!loop.isWaiting())
    		{
    			Thread.sleep(1);
    		}
    		Thread.sleep(1);
    	}
    	//Modify the actions list with the queued changes
    	modifyActionList();
    }
}