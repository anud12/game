package game.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Engine implements Runnable
{
	//Action lists to do for every cycle
	private LinkedList<IEngineAction> actions;
	    
    //Time measurements
    private long time;
    private float deltaTime;
    int minimumDeltaTime;
    // MultiThread //
    
    //Number of actions when a new loop will be created
    private int actionsToSplit; 
    //Number of maximum running parallel loops
    private int maxThreads;
    
    private int currentThreadNumber;
    //Size of the sublist to be sent
    private int subListSize;
    //List of loops running in parallel 
    private LinkedList<ActionLoop> loops;
    
    //Utility to launch, cache
    //and synchronize the threads
    private ExecutorService executor;
    
    //List to keep the future states of 
    //all the loops used for synchronization
    private LinkedList<Future<ActionLoop>> futureList;
    
    //Buffer list used in modifying  actions
    //in between the loop iterations
    private LinkedList<IEngineAction> addBuffer;
    private LinkedList<IEngineAction> removeBuffer;
    
    
    //Initializations
    public Engine(int actionsToSplit, int maxThreads)
    {
    	minimumDeltaTime = 1;
    	
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
        loops = new LinkedList<ActionLoop>();   
        //Buffer lists
        addBuffer = new LinkedList<IEngineAction>();
        removeBuffer = new LinkedList<IEngineAction>();
        
        //Utility to launch, cache
        //and synchronize the threads
        executor = Executors.newFixedThreadPool(maxThreads);
        //List to keep the future states of 
        //all the loops used for synchronization
        futureList = new LinkedList<Future<ActionLoop>>();
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
     
    //Start engine loop
    @Override
	public void run()  
	{    	
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
    
    @SuppressWarnings("unchecked")
	//Update the game
    void update() throws InterruptedException
    {
    	//Update time to current
    	time = System.nanoTime();
    	
    	//Get the time between frames
    	deltaTime = ( System.nanoTime() - time ) / 1000000.0f;
    	
    	//Check the frequency of the calls
    	if(deltaTime < minimumDeltaTime)
    	{
    		try
    		{
    			boolean useNano = false;
    			
    			int sleepTime =  minimumDeltaTime - (int)deltaTime;
    			deltaTime = minimumDeltaTime;
    			
    			if(sleepTime < 0)
    			{
    				useNano = true;
    				sleepTime = minimumDeltaTime * 1000000 - (int)deltaTime * 1000000;
    			}
    			
    			//Wait until the minimum time
    			if(useNano)
    				Thread.sleep(0, sleepTime);
    			Thread.sleep((long) (sleepTime));
    		}
    		catch(Exception e)
    		{
    		}
    	}
    	
    	//Grab iterator for the loops list
    	Iterator<ActionLoop> loopIterator = loops.iterator();
    	while(loopIterator.hasNext())
    	{
    		ActionLoop loop = loopIterator.next();
    		//Set the deltaTime for the loop
    		loop.setDeltaTime(deltaTime);
    		//Set the loop to plan the actions
    		loop.setToPlanning();
    		//Start the loop and get 
    		//the object in the finished state
    		futureList.add((Future<ActionLoop>) executor.submit(loop));
    	}
    	//Wait until the loops are finished
    	waitForLoops();
    	
    	//Grab iterator for the loops list
    	loopIterator = loops.iterator();
    	while(loopIterator.hasNext())
    	{
    		ActionLoop loop = loopIterator.next();
    		//Set the deltaTime for the loop
    		loop.setDeltaTime(deltaTime);
    		//Set the loop to execute the actions
    		loop.setToExecution();
    		//Start the loop and get 
    		//the object in the finished state
    		futureList.add((Future<ActionLoop>) executor.submit(loop));
    	}
    	
    	//Wait until the loops are finished
    	waitForLoops();
    	
    	//Modify the actions list with the queued changes
    	readFromBuffers();
    }
    
    protected void waitForLoops() throws InterruptedException
    {
    	Iterator<Future<ActionLoop>> iterator = futureList.iterator();
    	while(iterator.hasNext())
    	{
    		Future<ActionLoop> loop = iterator.next();
    		
    		try 
    		{
    			//Wait until the loop is finished
				loop.get();
			} catch (ExecutionException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	futureList.clear();
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
    		ActionLoop loop = new ActionLoop(removeBuffer);
    		loops.add(loop);
    		
		}
    	//Calculate the size of the sublists
    	subListSize = actions.size() / loops.size();
    	
    	//Configure the engineLoops
    	Iterator<ActionLoop> loopIterator = loops.iterator();
    	int loopNumber = 0;
    	while(loopIterator.hasNext() && loopNumber < currentThreadNumber && !actions.isEmpty())
    	{
    		ActionLoop loop = loopIterator.next();
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
    	}
    }
}