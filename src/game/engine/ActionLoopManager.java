package game.engine;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class  ActionLoopManager<ALoop extends ActionLoop<?>, ActionType>
{
	//Variable used to instantiate a new ALoop object
	private Class<ALoop> classOfLoop;
	//Class of ActionType used to check the contents of the loops
	private Class<ActionType> classOfActionType;
	//Utility to launch, cache
    //and synchronize the threads
    private ExecutorService executor;
	//Action lists to do for every cycle
	private LinkedList<ActionType> actions;
	
	//Configurations
	
	 //Number of actions when a new loop will be created
    private int actionsToSplit; 
    //Number of maximum running parallel loops
    private int maxThreads;
    
    private int currentThreadNumber;
    //Size of the sublist to be sent
    private int subListSize;
    //Containers
    
    //List of loops running in parallel 
    private LinkedList<ALoop> loops;
    //Buffer list used in modifying  actions
    //in between the loop iterations
    private LinkedList<ActionType> addBuffer;
    private LinkedList<ActionType> removeBuffer;
    //List to keep the future states of 
    //all the loops used for synchronization
    private LinkedList<Future<ALoop>> futureList;
    private LinkedList<ActionLoopManager<?,?>> childs;
    
	public ActionLoopManager(Class<ALoop> classOfLoop, Class<ActionType> classOfAction, int actionsToSplit, int maxThreads)
	{
		constructor(classOfLoop, classOfAction, actionsToSplit, maxThreads);
	}
	
	public ActionLoopManager(Class<ALoop> classOfLoop, Class<ActionType> classOfAction, int actionsToSplit, int maxThreads, ActionLoopManager<?,?> child)
	{
		constructor(classOfLoop, classOfAction, actionsToSplit, maxThreads);
		childs.add(child);
	}
	
	public ActionLoopManager(Class<ALoop> classOfLoop, Class<ActionType> classOfAction, int actionsToSplit, int maxThreads, List<ActionLoopManager<?,?>> childs)
	{
		constructor(classOfLoop, classOfAction, actionsToSplit, maxThreads);
		this.childs.addAll(childs);
	}
	
	private void constructor(Class<ALoop> classOfLoop, Class<ActionType> classOfAction, int actionsToSplit, int maxThreads)
	{
		this.classOfLoop = classOfLoop;
		this.classOfActionType = classOfAction;
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
		childs = new LinkedList<>();
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
    	
		resizeLoops();
	}
	
	@SuppressWarnings("unchecked")
	protected void resizeLoops()
	{
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
				loop.setManager(this);
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
    		ActionLoop<ActionType> loop = (ActionLoop<ActionType>) loopIterator.next();
    		//Slice the list of actions in smaller lists
    		//equal in size for the loops and set the removeBuffer
    		if(loopNumber == 0)
    		{
    			loop.setActions( actions.subList( subListSize * loopNumber, subListSize * (loopNumber + 1) ) ) ;
    		}
    		else
    		{
    			loop.setActions( actions.subList( subListSize * loopNumber + 1, subListSize * (loopNumber + 1) ) );
    		}
    		loopNumber++;
    	}
	}
	
	public void add(ActionType action)
	{
		synchronized(addBuffer)
		{
			addBuffer.add(action);
		}
		
	}
	public void add(LinkedList<ActionType> buffer) throws InterruptedException
	{
		synchronized(addBuffer)
		{
			addBuffer.addAll(buffer);
		}
	}
	@SuppressWarnings("unchecked")
	public void remove(Object action)
	{
		synchronized(removeBuffer)
		{
			removeBuffer.add((ActionType) action);
		}
		Iterator<ActionLoopManager<?,?>> iterator = childs.iterator();
		while(iterator.hasNext())
		{
			ActionLoopManager<?,?> manager = iterator.next();
			if(manager.classOfActionType == this.classOfActionType)
				manager.remove(action);
		}
	}
	@SuppressWarnings("unchecked")
	public void removeAll(LinkedList<?> buffer)
	{
		synchronized(removeBuffer)
		{
			removeBuffer.addAll((Collection<? extends ActionType>) buffer);
		}
		Iterator<ActionLoopManager<?,?>> iterator = childs.iterator();
		while(iterator.hasNext())
		{
			ActionLoopManager<?,?> manager = iterator.next();
			if(manager.classOfActionType == this.classOfActionType)
				manager.removeAll(buffer);
		}
	}
	
	//  Getters  //
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
    public int getCurrentThreadNumberPlan()
    {
    	return currentThreadNumber;
    }
    
	@SuppressWarnings("unchecked")
	public void run(float deltaTime) throws InterruptedException
	{
		readFromBuffers();
				
		//Grab iterator for the loops list
		Iterator<ALoop> loopIterator = loops.iterator();
    	while(loopIterator.hasNext())
    	{
    		ALoop loop = loopIterator.next();
    		//Set the deltaTime for the loop
    		loop.setDeltaTime(deltaTime);
    		//Start the loop and get 
    		//the object in the finished state
    		futureList.add((Future<ALoop>) executor.submit(loop));
    	}
    	//Wait until the loops are finished
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
