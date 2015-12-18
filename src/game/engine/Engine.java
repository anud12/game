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
    
    //
    private ActionLoopManager<ActionLoop> manager;
    
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
        
        manager = new ActionLoopManager<ActionLoop>(ActionLoop.class, actionsToSplit, maxThreads);
    }
    
    //   Getters   //
    public int getActionsSize()
    {    	
    	return manager.getActionsSize();
    }
    public float getDeltaTime()
    {
    	return deltaTime;
    }
    public int getAddBufferSize()
    {
    	return manager.getAddBufferSize();
    }
    public int getRemoveBufferSize()
    {
    	return manager.getRemoveBufferSize();
    }
    public int getCurrentThreadNumber()
    {
    	return currentThreadNumber;
    }
    //Add to stack
    public void addAction(IEngineAction action)
    {
    	manager.add(action);
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
    	
    	manager.run(deltaTime);
    }
}