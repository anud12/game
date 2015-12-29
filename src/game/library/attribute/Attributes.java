package game.library.attribute;

import java.util.HashMap;

public class Attributes 
{
	protected HashMap<String, Object> attributes;
	
	public Attributes()
	{		
		attributes = new HashMap<String, Object>();
	}
	
	public void set(AttributeSelector selector, Object value)
	{
		selector.set(attributes, value);
	}
	public Object get(AttributeSelector selector)
	{
		return selector.select(attributes);
	}
}
