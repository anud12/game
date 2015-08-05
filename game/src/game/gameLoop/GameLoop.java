package game.gameLoop;

import java.util.Iterator;
import java.util.LinkedList;

public class GameLoop
{
    LinkedList<IGameLoopAction> actions;
    public GameLoop()
    {
        actions = new LinkedList<IGameLoopAction>();
    }
    
    void start()
    {
        
    }
    void update()
    {
        Iterator<IGameLoopAction> actionIterator = actions.iterator();
        while(actionIterator.hasNext())
        {
            IGameLoopAction action = actionIterator.next();
            
            action.execute();
        }
    }
}
