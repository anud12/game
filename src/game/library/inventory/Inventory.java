package game.library.inventory;

import game.library.inventory.item.Item;

public interface Inventory
{
	public boolean addItem(Item item, float quantity);
	public boolean removeItem(Item item, float quantity);
	
	public float getQuantity(Item item);
	public float getMaxSize(Item item);
	public float getFreeSize(Item item);
}
