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
	//To do each frame
    public void execute(double deltaTime);
    
    
    //Check if its action is completed
    public boolean isCompleted(IEngineAction action);
    
    //Check if the action is removable from the action list
    public boolean isRemovable(IEngineAction action);
    
    //Actions to do when its completed
    public void onComplete(IEngineAction action);
}
