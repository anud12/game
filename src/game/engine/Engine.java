package game.engine;

import java.util.Iterator;
import java.util.LinkedList;

public class Engine implements Runnable
{
	//Action lists to do for every cycle
	LinkedList<IEngineAction> actions;
	
    Iterator<IEngineAction> actionIterator;
    
    //Total time elapsed
    long time;
    float deltaTime;
    // MultiThread //
    
    //Number of actions when a new loop will be created
    int actionsToSplit; 
    //Number of maximum running parallel loops
    int maxThreads;
    
    int currentThreadNumber;
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
    	//If actions is 0 make it infinite
    	if(actionsToSplit == 0)
    		this.actionsToSplit = Integer.MAX_VALUE;
    	else
    		this.actionsToSplit = actionsToSplit;
    	
    	//If the number of threads is 0
    	//query the OS with the number
    	//of virtual processors available
    	if(maxThreads == 0)
        	this.maxThreads = Runtime.getRuntime().availableProcessors();
    	else
    		this.maxThreads = maxThreads; 	
    	
    	//Initialize the lists
        actions = new LinkedList<IEngineAction>();
        loops = new LinkedList<EngineLoop>();   
        //Buffer lists
        addBuffer = new LinkedList<IEngineAction>();
        removeBuffer = new LinkedList<IEngineAction>();
         
    }
    
    //   Getters   //
    public int getActionsSize()
    {    	
    	return actions.size();
    }
    public float getDeltaTime()
    {
    	return deltaTime;
    }
    public int getAddBufferSize()
    {
    	return addBuffer.size();
    }
    public int getRemoveBufferSize()
    {
    	return removeBuffer.size();
    }
    public int getCurrentThreadNumber()
    {
    	return currentThreadNumber;
    }
    //Add to stack
    public void addAction(IEngineAction action)
    {
    	//Add action to buffer
    	synchronized(addBuffer)
    	{
    		addBuffer.add(action);
    	}
    }
    
    protected void readFromBuffers()
    {
    	//Check if the buffer lists aren't empty
    	if(removeBuffer.size() ==  0  && addBuffer.size() == 0)
    	{
    		return;
    	}
    	
    	//Check if the remove Buffer is empty
    	if(removeBuffer.size() !=  0)
    	{
    		synchronized(removeBuffer)
    		{
    			actions.removeAll(removeBuffer);
    			//Assign new buffer
        		removeBuffer =  new LinkedList<>();
    		}
    	}
    	
    	//Check if the add Buffer is empty
    	if(addBuffer.size() != 0)
    	{	
    		synchronized(addBuffer)
    		{
    			actions.addAll(addBuffer);
    			//Assign new buffer
            	addBuffer = new LinkedList<>();
    		}	
    	}
    	//Calculate the required number of threads
    	//regardless of the limit
    	currentThreadNumber = actions.size() / actionsToSplit + 1;
    	
    	//Trim the number
    	if(currentThreadNumber > maxThreads)
    	{
    		currentThreadNumber = maxThreads;
    	}
    	
    	//Check if it it needs to split the actions &
    	//if the maximum number of loops have been reached
    	while((currentThreadNumber > loops.size()) && (loops.size() < maxThreads))
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
    	while(loopIterator.hasNext() && loopNumber < currentThreadNumber && !actions.isEmpty())
    	{
    		EngineLoop loop = loopIterator.next();
    		//Slice the list of actions in smaller lists
    		//equal in size for the loops and set the removeBuffer
    		if(loopNumber == 0)
    		{
    			loop.setActions( actions.subList( subListSize * loopNumber, subListSize * (loopNumber + 1) ) ) ;
    			loop.setRemoveBuffer(removeBuffer);
    		}
    		else
    		{
    			loop.setActions( actions.subList( subListSize * loopNumber + 1, subListSize * (loopNumber + 1) ) );
    			loop.setRemoveBuffer(removeBuffer);
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
    	
    	//Wait until the loops are finished
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
    	readFromBuffers();
    }
}