package game.library.attribute;

import java.util.HashMap;

class MovementSpeedAttribute extends AttributeSelector
{	
	@Override
	public void set(HashMap<String, Object> attributes, Object value)
	{
		attributes.remove(getKey());
		Float number = (Float) value;
		attributes.put(getKey(), number);
		
	}
	
	@Override
	protected String getKey()
	{
		return "movementSpeed";
	}

	@Override
	protected Object getDefault()
	{
		return 0;
	}

}
