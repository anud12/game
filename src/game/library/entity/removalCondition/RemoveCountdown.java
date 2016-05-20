package game.library.entity.removalCondition;

import game.library.entity.Entity;

public class RemoveCountdown extends RemovalCondition
{
	float count;
	float time;
	public RemoveCountdown(float time)
	{
		this.time = time;
		count = 0;
	}
	@Override
	public boolean removeCheck(Entity entity, Float deltaTime)
	{
		count = count + deltaTime;
		if(time < count)
		{
			return true;
		}
		return false;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RemoveCountdown");
		builder.append(" count = ");
		builder.append(count);
		builder.append(" time = ");
		builder.append(time);
		return builder.toString();
	}

}
