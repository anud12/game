package game.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ISerializableXML 
{
	public void appendAllXML(Document doc);
	public void appendChildXML(Document doc, Element rootElement);
}