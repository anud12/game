package game.library.attribute;

import java.util.HashMap;

class IDAttribute extends AttributeSelector
{
	@Override
	public void set(HashMap<String, Object> attributes, Object value)
	{
		attributes.remove(getKey());
		Integer number = (Integer) value;
		attributes.put(getKey(), number);

	}

	@Override
	protected String getKey()
	{
		return "ID";
	}

	@Override
	protected Object getDefault()
	{
		return -1;
	}

}
