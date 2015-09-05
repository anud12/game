package game.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ISerializableXML 
{
	public void appendObjectToXML(Document doc);
	public void appendDataToXML(Document doc, Element rootElement);
}