/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine;

/**
 *
 * @author Laptop
 */
public interface IEngineAction
{
	//To do each frame and return the action which
	//has executed
    public IEngineAction execute(double deltaTime);
    
    
    //Check if the action that returned from executed is completed
    public boolean isCompleted(IEngineAction returnedAction);
    
    //Check if the action is removable from the action list
    public boolean isRemovable(IEngineAction returnedAction);
    
    //Actions to do when its completed
    public void onComplete(IEngineAction returnedAction);
}
