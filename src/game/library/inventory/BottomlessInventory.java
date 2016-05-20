package game.library.inventory;

import java.util.HashMap;

import game.library.inventory.item.Item;

public class BottomlessInventory implements Inventory
{
	
	protected HashMap<Item, Float> items;
	public BottomlessInventory()
	{
		items = new HashMap<>();
	}
	@Override
	public boolean addItem(Item item, float quantity)
	{
		if(!items.containsKey(item))
		{
			items.put(item, quantity);
			return true;
		}
		
		float value = items.get(item) + quantity;
		items.put(item, value);
		
		return true;
	}

	@Override
	public boolean removeItem(Item item, float quantity)
	{
		if(!items.containsKey(item))
		{
			items.put(item, -quantity);
			return true;
		}
		
		float value = items.get(item) - quantity;
		items.put(item, value);
		
		return true;
	}

	@Override
	public float getQuantity(Item item)
	{
		if(items.containsKey(item))
		{
			return items.get(item);
		}
		return 0;
	}

	@Override
	public float getMaxSize(Item item)
	{
		return Float.MAX_VALUE;
	}

	@Override
	public float getFreeSize(Item item)
	{
		return getMaxSize(item) - items.get(item);
	}

}
