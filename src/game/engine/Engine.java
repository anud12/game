package game.engine;

public class Engine implements Runnable
{	    
    //Time measurements
    private long time;
    private float deltaTime;
    int minimumDeltaTime;
    
    //   Managers used to process the engine phases   //
    
    // Planning manager used to calculate what the actions will do
    private ActionLoopManager<PlanActionLoop, IEngineAction> planningManager;
    // Execution manager used to do the action
    private ActionLoopManager<ExecuteActionLoop, IEngineAction> executionManager;
    // Update manager used to update the variables
	private ActionLoopManager<UpdateActionLoop, IEngineVariable> variableManager;
    
	//Options
	private boolean isPause;
	 
    //Initializations
    public Engine(int actionsToSplit, int maxThreads)
    {
    	minimumDeltaTime = 1;
    	                     
    	isPause = false;
    	
        planningManager = new ActionLoopManager<PlanActionLoop, IEngineAction>(PlanActionLoop.class, IEngineAction.class, actionsToSplit, maxThreads);
        executionManager = new ActionLoopManager<ExecuteActionLoop, IEngineAction>(ExecuteActionLoop.class, IEngineAction.class, actionsToSplit, maxThreads, planningManager);
        variableManager = new ActionLoopManager<UpdateActionLoop, IEngineVariable>(UpdateActionLoop.class, IEngineVariable.class, actionsToSplit, maxThreads);
    }
    
    //   Getters   //
    public float getDeltaTime()
    {
    	return deltaTime;
    }
    public int getActionsSizeExecute()
    {    	
    	return executionManager.getActionsSize();
    }
    public int getAddBufferSizeExecute()
    {
    	return executionManager.getAddBufferSize();
    }
    public int getRemoveBufferSizeExecute()
    {
    	return executionManager.getRemoveBufferSize();
    }
    public int getCurrentThreadNumberExecute()
    {
    	return executionManager.getCurrentThreadNumberPlan();
    }
    public int getActionsSizePlan()
    {    	
    	return planningManager.getActionsSize();
    }
    public int getAddBufferSizePlan()
    {
    	return planningManager.getAddBufferSize();
    }
    public int getRemoveBufferSizePlan()
    {
    	return planningManager.getRemoveBufferSize();
    }
    public int getCurrentThreadNumberPlan()
    {
    	return planningManager.getCurrentThreadNumberPlan();
    }
    //Add to stack
    public void addAction(IEngineAction action)
    {
    	planningManager.add(action);
    	executionManager.add(action);
    }
    public void addVariable(IEngineVariable variable)
    {
    	variableManager.add(variable);
    }
    public void pause(boolean pause)
    {
    	isPause = pause;
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
    	
    	if(isPause)
    	{
    		deltaTime = 0;
    	}
    	
    	planningManager.run(deltaTime);
    	executionManager.run(deltaTime);
    	variableManager.run(deltaTime);
    }
}