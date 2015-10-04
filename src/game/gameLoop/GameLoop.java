package game.gameLoop;

import java.util.Iterator;
import java.util.LinkedList;

public class GameLoop implements Runnable
{
	//Action lists to do for every cicle
    LinkedList<IGameLoopAction> actions;
    Iterator<IGameLoopAction> actionIterator;
    
    //Total time elapsed
    long time;
    long deltaTime;
    //Initializations
    public GameLoop()
    {
        actions = new LinkedList<IGameLoopAction>();
    }
    
    
    //Start engine loop
    @Override
	public void run()  
	{
    	time = System.nanoTime();
    	
    	while(true)
    	{
    		update();
    	}
    }
    
    
    //Add to stack
    public synchronized void addAction(IGameLoopAction action)
    {
    	System.out.println("Added: " + action);
    	actions.add(action);
    	actionIterator = actions.iterator();
    }
    
    
    //Update the game
    void update() 
    {
    	//Get the time between frames
    	
    	deltaTime = ( System.nanoTime() - time );
    	time = System.nanoTime();
    	
    	//Grab iterator for the action lists
    	actionIterator = actions.iterator();
    	
    	//Check the frequency of the calls
    	if(deltaTime < 90000)
    	{
    		try
    		{
    			//Wait until the minimum amout of calls
    			Thread.sleep(0 ,(int) (90000 - deltaTime) );
    			deltaTime = 90000;
    		}
    		catch(Exception e)
    		{
    		}
    	}
        //For each action call .execute(time passed in milliseconds)
        while(actionIterator.hasNext())
        {	
            IGameLoopAction action = actionIterator.next();
            
            //Execute action with time difference & convert from nanoseconds to milliseconds
            
            action.execute( deltaTime / 1000000.0 );
            
            if(action.isCompleted(action))
            {
            	action.onComplete(action);
            	if(action.isRemovable(action))
            		actionIterator.remove();
            }
        }
        
        //Update time to current
        
    }


	
}
