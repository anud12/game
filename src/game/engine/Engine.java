package game.engine;

import game.engine.actionLoop.EntityAliveCheckActionLoop;
import game.engine.actionLoop.EntityExecuteActionLoop;
import game.engine.actionLoop.EntityPlanActionLoop;
import game.engine.actionLoop.EngineRemovalLoop;
import game.engine.actionLoop.RelatedActionLoop;
import game.engine.actionLoop.UnrelatedActionLoop;
import game.engine.actions.IEngineEntityAliveCheck;
import game.engine.actions.IEngineEntityExecution;
import game.engine.actions.IEngineRelatedUpdate;
import game.engine.actions.IEngineRemoval;
import game.engine.actions.IEngineUnrelatedUpdate;

public class Engine implements Runnable
{	    
    //Time measurements
    private long time;
    private float deltaTime;
    int minimumDeltaTime;
    
    //   Managers used to process the engine phases   //
    
    // Planning manager used to calculate what the actions will do
    private ActionLoopManager<EntityPlanActionLoop, IEngineEntityExecution> entityPlanningManager;
    // Execution manager used to do the action
    private ActionLoopManager<EntityExecuteActionLoop, IEngineEntityExecution> entityExecutionManager;
    // Update manager used to update the game containers
	private ActionLoopManager<UnrelatedActionLoop, IEngineUnrelatedUpdate> unrelatedUpdateManager;
    // Manager used to resolve the results of the actions
	private ActionLoopManager<RelatedActionLoop, IEngineRelatedUpdate> relatedUpdateManager;
	private ActionLoopManager<EntityAliveCheckActionLoop, IEngineEntityAliveCheck> entityAliveCheckManager;
	private ActionLoopManager<EngineRemovalLoop, IEngineRemoval> engineRemovalManager;
	//Options
	private boolean isPause;
	 
    //Initializations
    public Engine(int actionsToSplit, int maxThreads)
    {
    	minimumDeltaTime = 15;
    	                     
    	isPause = false;
    	
        entityPlanningManager = new ActionLoopManager<EntityPlanActionLoop, IEngineEntityExecution>(EntityPlanActionLoop.class, IEngineEntityExecution.class, actionsToSplit, maxThreads);
        entityExecutionManager = new ActionLoopManager<EntityExecuteActionLoop, IEngineEntityExecution>(EntityExecuteActionLoop.class, IEngineEntityExecution.class, actionsToSplit, maxThreads);
        entityAliveCheckManager = new ActionLoopManager<EntityAliveCheckActionLoop, IEngineEntityAliveCheck>(EntityAliveCheckActionLoop.class, IEngineEntityAliveCheck.class, actionsToSplit, maxThreads);
        unrelatedUpdateManager = new ActionLoopManager<UnrelatedActionLoop, IEngineUnrelatedUpdate>(UnrelatedActionLoop.class, IEngineUnrelatedUpdate.class, actionsToSplit, maxThreads);
        relatedUpdateManager = new ActionLoopManager<RelatedActionLoop, IEngineRelatedUpdate>(RelatedActionLoop.class, IEngineRelatedUpdate.class, actionsToSplit, maxThreads);
        engineRemovalManager = new ActionLoopManager<>(EngineRemovalLoop.class, IEngineRemoval.class, actionsToSplit, maxThreads);
    }
    
    //   Getters   //
    public float getDeltaTime()
    {
    	return deltaTime;
    }
    @SuppressWarnings("rawtypes")
	public ActionLoopManager getEntityPlanningManager()
    {
    	return entityPlanningManager;
    }
    @SuppressWarnings("rawtypes")
	public ActionLoopManager getEntityExecutionManager()
    {
    	return entityExecutionManager;
    }
    @SuppressWarnings("rawtypes")
	public ActionLoopManager getUnrelatedUpdateManager()
    {
    	return unrelatedUpdateManager;
    }
    @SuppressWarnings("rawtypes")
	public ActionLoopManager getRelatedUpdateManager()
    {
    	return relatedUpdateManager;
    }
    @SuppressWarnings("rawtypes")
	public ActionLoopManager getEntityAliveCheckManager()
    {
    	return entityAliveCheckManager;
    }
    //Add to stack
    public void addAction(IEngineEntityExecution action)
    {
    	entityPlanningManager.add(action);
    	entityExecutionManager.add(action);
    }
    public void addAliveCheckAction(IEngineEntityAliveCheck action)
    {
    	entityAliveCheckManager.add(action);
    }
    public void removeAction(IEngineEntityExecution action)
    {
    	entityPlanningManager.remove(action);
    	entityExecutionManager.remove(action);
    }
    public void addUnrelated(IEngineUnrelatedUpdate variable)
    {
    	unrelatedUpdateManager.add(variable);
    }
    public void removeUnrelated(IEngineUnrelatedUpdate variable)
    {
    	unrelatedUpdateManager.remove(variable);
    }
    public void addRelated(IEngineRelatedUpdate variable)
    {
    	relatedUpdateManager.add(variable);
    }
    public void removeRelated(IEngineRelatedUpdate variable)
    {
    	relatedUpdateManager.remove(variable);
    }
    public void addEngineRemoval(IEngineRemoval variable)
    {
    	engineRemovalManager.add(variable);
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
    	//Run the loops
    	entityPlanningManager.run(deltaTime);
    	entityExecutionManager.run(deltaTime);
    	entityAliveCheckManager.run(deltaTime);
    	
    	unrelatedUpdateManager.run(deltaTime);
    	relatedUpdateManager.run(deltaTime);
    	
    	engineRemovalManager.run(deltaTime);
    }
}