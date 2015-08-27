package game.gameLoop;

import java.util.Iterator;
import java.util.LinkedList;

public class GameLoop
{
	//Action lists to do for every cicle
    LinkedList<IGameLoopAction> actions;
    
    //Total time elapsed
    long time;
    int deltaTime;
    //Initializations
    public GameLoop()
    {
        actions = new LinkedList<IGameLoopAction>();
        time = System.nanoTime();
    }
    
    
    //Start engine loop
    public void start()
    {
    	while(true)
    	{
    		update();
    	}
    }
    
    
    //Add to stack
    public void addAction(IGameLoopAction action)
    {
    	actions.add(action);
    }
    
    
    //Update the game
    void update() 
    {
    	deltaTime = ( int )( System.nanoTime() - time );
    	
    	
    	if(deltaTime < 5000)
    	{
    		
    		try
    		{
    			Thread.sleep(0, 5000 - deltaTime);
    			deltaTime = 5000;
    		}
    		catch(Exception e)
    		{
    			
    		}
    	}
    	
    	//Grab iterator for the action lists
        Iterator<IGameLoopAction> actionIterator = actions.iterator();
        
        //For each action call .execute(time passed in milliseconds)
        while(actionIterator.hasNext())
        {
        	
            IGameLoopAction action = actionIterator.next();
            
            //Execute action with time difference & convert from nanoseconds to milliseconds
            
            action.execute( deltaTime / 1000000.0 );
        }
        
        //Update time to current
        time = System.nanoTime();
    }
}
