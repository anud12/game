package game.library.interfaces;

import game.gameLoop.IGameLoopAction;

public interface IGameLoopEntity extends IGameLoopAction
{
    public void addAction(IGameLoopAction newAction);
    public void removeCurrentAction();
    public void clearActionList();
}
