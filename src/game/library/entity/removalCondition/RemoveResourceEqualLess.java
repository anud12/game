package game.library.entity.removalCondition;

import game.library.entity.Entity;
import game.library.inventory.item.Item;

public class RemoveResourceEqualLess extends RemovalCondition
{
	protected Item item;
	protected float quantity;
	
	public RemoveResourceEqualLess(Item item, float quantity)
	{
		this.item = item;
		this.quantity = quantity;
	}
	@Override
	public boolean removeCheck(Entity entity, Float deltaTime)
	{
		if(entity.getInventory().getQuantity(item) <= quantity)
		{
			return true;
		}
		return false;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RemoveResourceEqualLess");
		builder.append(" on ");
		builder.append(item.getName());
		builder.append(" at ");
		builder.append(quantity);
		return builder.toString();
	}

}
