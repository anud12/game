package game.library.inventory;

import game.library.inventory.item.Item;

public class NoSpaceInventory implements Inventory
{

	@Override
	public boolean addItem(Item item, float quantity)
	{
		return false;
	}

	@Override
	public boolean removeItem(Item item, float quantity)
	{
		return false;
	}

	@Override
	public float getQuantity(Item item)
	{
		return 0;
	}

	@Override
	public float getMaxSize(Item item)
	{
		return 0;
	}

	@Override
	public float getFreeSize(Item item)
	{
		return 0;
	}

}
