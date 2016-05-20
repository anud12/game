/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.actions;

/**
 *
 * @author Laptop
 */
public interface IEngineEntityExecution extends IEngineRemoval
{
	//To do each frame and return the action which
	//has executed
    public void execute();
            
    //Check if the action that returned from executed is completed
    public boolean isCompleted();
    
    //Actions to do when its completed
    public void onComplete();
}
