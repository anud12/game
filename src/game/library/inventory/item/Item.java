package game.library.inventory.item;

import game.library.IHasName;

public class Item implements IHasName
{
	protected String name;
	
	protected float number;
	
	@Override
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
