package game.library.attribute;

import java.awt.Color;
import java.util.HashMap;

class ColorAttribute extends AttributeSelector
{

	@Override
	public void set(HashMap<String, Object> attributes, Object value)
	{
		attributes.remove(getKey());
		Color color = (Color) value;
		attributes.put(getKey(), color);
		
	}

	@Override
	protected String getKey()
	{
		// TODO Auto-generated method stub
		return "color";
	}

	@Override
	protected Object getDefault()
	{
		// TODO Auto-generated method stub
		return Color.white;
	}


}
