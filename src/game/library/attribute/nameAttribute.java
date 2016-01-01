package game.library.attribute;

import java.util.HashMap;

class nameAttribute extends AttributeSelector
{

	@Override
	public void set(HashMap<String, Object> attributes, Object value)
	{
		attributes.remove(getKey());
		String name = (String) value;
		attributes.put(getKey(), name);
		
	}
	
	@Override
	protected String getKey()
	{
		return "name";
	}

	@Override
	protected Object getDefault()
	{
		return 0;
	}
}
