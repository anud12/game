package game.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ActionLoopManager<ALoop extends ActionLoop>
{
	//Class of the ALoop
	private Class<ALoop> classOfLoop;
	//Utility to launch, cache
    //and synchronize the threads
    private ExecutorService executor;
	//Action lists to do for every cycle
	private LinkedList<IEngineAction> actions;
	
	//Configurations
	
	 //Number of actions when a new loop will be created
    private int actionsToSplit; 
    //Number of maximum running parallel loops
    private int maxThreads;
    
    private int currentThreadNumber;
    //Size of the sublist to be sent
    private int subListSize;
    //Scheduling to read from buffers
    private boolean readFromBuffers;
    //Containers
    
    //List of loops running in parallel 
    private LinkedList<ALoop> loops;
    //Buffer list used in modifying  actions
    //in between the loop iterations
    private LinkedList<IEngineAction> addBuffer;
    private LinkedList<IEngineAction> removeBuffer;
    //List to keep the future states of 
    //all the loops used for synchronization
    private LinkedList<Future<ALoop>> futureList;
    
	public ActionLoopManager(Class<ALoop> classOfLoop, int actionsToSplit, int maxThreads)
	{
		this.classOfLoop = classOfLoop;
		this.actionsToSplit = actionsToSplit;
		this.maxThreads = maxThreads;
		
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
    	
    	actions = new LinkedList<>();
    	addBuffer = new LinkedList<>();
    	removeBuffer = new LinkedList<>();
    	
		loops = new LinkedList<ALoop>();
		
		executor = Executors.newCachedThreadPool();
		
		futureList = new LinkedList<>();
	}
	private void readFromBuffers()
	{
		//Check if the buffer lists aren't empty
    	if(removeBuffer.size() ==  0  && addBuffer.size() == 0)
    	{
    		return;
    	}
    	
    	synchronized(removeBuffer)
    	{
    		//Check if the remove Buffer is empty
        	if(removeBuffer.size() !=  0)
        	{
        		
    			actions.removeAll(removeBuffer);
    			//Assign new buffer
        		removeBuffer =  new LinkedList<>();
        	}
    	}
    	
    	synchronized(addBuffer)
    	{
    		//Check if the add Buffer is empty
        	if(addBuffer.size() != 0)
        	{
        		//System.out.println(addBuffer.size());
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
    		ALoop loop;
			try
			{
				loop = classOfLoop.newInstance();
				loops.add(loop);
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	//Calculate the size of the sublists
    	subListSize = actions.size() / loops.size();
    	
    	//Configure the engineLoops
    	Iterator<ALoop> loopIterator = loops.iterator();
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
	public void add(IEngineAction action)
	{
		synchronized(addBuffer)
		{
			addBuffer.add(action);
			readFromBuffers = true;
		}
		
	}
	public void add(LinkedList<IEngineAction> buffer) throws InterruptedException
	{
		synchronized(addBuffer)
		{
			addBuffer.addAll(buffer);
			readFromBuffers = true;
		}
	}
	public void remove(IEngineAction action)
	{
		synchronized(removeBuffer)
		{
			removeBuffer.remove(action);
			readFromBuffers = true;
		}
	}
	public void remove(LinkedList<IEngineAction> buffer)
	{
		synchronized(removeBuffer)
		{
			removeBuffer.addAll(buffer);
			readFromBuffers = true;
		}
	}
	
	public int getAddBufferSize()
    {
    	return addBuffer.size();
    }
    public int getRemoveBufferSize()
    {
    	return removeBuffer.size();
    }
    public int getActionsSize()
    {    	
    	return actions.size();
    }
	@SuppressWarnings("unchecked")
	public void run(float deltaTime) throws InterruptedException
	{
		readFromBuffers();
		
		//System.out.println(actions.size());
		System.out.println(loops.size());
		
		//Grab iterator for the loops list
		Iterator<ALoop> loopIterator = loops.iterator();
    	while(loopIterator.hasNext())
    	{
    		ALoop loop = loopIterator.next();
    		//Set the deltaTime for the loop
    		loop.setDeltaTime(deltaTime);
    		//Set the loop to plan the actions
    		loop.setToPlanning();
    		//Start the loop and get 
    		//the object in the finished state
    		futureList.add((Future<ALoop>) executor.submit(loop));
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
    		futureList.add((Future<ALoop>) executor.submit(loop));
    	}
    	waitForLoops();
	}
	public void waitForLoops() throws InterruptedException
    {
    	Iterator<Future<ALoop>> iterator = futureList.iterator();
    	while(iterator.hasNext())
    	{
    		Future<ALoop> loop = iterator.next();
    		
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

}
