package game.gameLoop;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Date;

public class GameLoop
{
	//Action lists to do for every cicle
    LinkedList<IGameLoopAction> actions;
    
    //Total time elapsed
    long time;
    
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
    	//Grab iterator for the action lists
        Iterator<IGameLoopAction> actionIterator = actions.iterator();
        
        //For each action call .execute(time passed in milliseconds)
        while(actionIterator.hasNext())
        {
        	
            IGameLoopAction action = actionIterator.next();
            
            //Execute action with time difference & convert from nanoseconds to milliseconds
            
            action.execute( ( System.nanoTime() - time ) / 1000000.0 );
        }
        
        //Update time to current
        time = System.nanoTime();
    }
}
