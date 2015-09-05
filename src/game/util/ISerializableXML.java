package game.util;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface ISerializableXML 
{
	public void appendObjectToXML(Document doc);
	public void appendDataToXML(Document doc, Element rootElement);
	
	public void readFromXML(Element element) throws XMLParseException;
}