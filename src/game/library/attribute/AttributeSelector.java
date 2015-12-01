package game.library.attribute;

import java.util.HashMap;

public abstract class AttributeSelector 
{
	public abstract void set(HashMap<String, Object> attributes, Object value);
	protected abstract String getKey();
	protected abstract Object getDefault();
	
	private static HashMap<String, AttributeSelector> attributesSelectors = new HashMap<String, AttributeSelector>();
	protected Object standard;
	
	public Object select(HashMap<String, Object> attributes)
	{
		String key = getKey();
		
		if(!attributes.containsKey(key))
		{
			return getDefault();
		}
		else return attributes.get(key);
	}
	
	
	
	public static AttributeSelector movementSpeed()
	{
		String key = "movementSpeed";
		if(!attributesSelectors.containsKey(key))
		{
			attributesSelectors.put(key, new MovementSpeedAttribute());
		}
		
		return attributesSelectors.get(key);
	}
	public static AttributeSelector color()
	{
		String key = "color";
		if(!attributesSelectors.containsKey(key))
		{
			attributesSelectors.put(key, new ColorAttribute());
		}
		
		return attributesSelectors.get(key);
	}
	public static AttributeSelector ID()
	{
		String key = "ID";
		if(!attributesSelectors.containsKey(key))
		{
			attributesSelectors.put(key, new IDAttribute());
		}
		
		return attributesSelectors.get(key);
	}
}
