package game.engine.actions;

public interface IEngineEntityPlan extends IEngineRemoval
{
	//To calculate the execution based on time between the frames
	public void plan(double deltaTime);
}
