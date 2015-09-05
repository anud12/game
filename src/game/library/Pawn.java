package game.library;

import java.awt.geom.Point2D;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.util.ISerializableXML;

public class Pawn extends Entity implements ISerializableXML{

	protected float movementSpeed;
	
	public Pawn(int width, int height, Point2D.Float origin) {
		super(width, height, origin);
		// TODO Auto-generated constructor stub
	}
	public Pawn(Element element) throws XMLParseException
	{
		super(element);
		readFromXML(element);
	}
	
	public float getMovementSpeed()
	{
		return movementSpeed;
	}
	
	public void setMovementSpeed(float movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}
	
	@Override
	public void appendObjectToXML(Document doc) 
	{
		Element docRoot = doc.getDocumentElement();
		Element rootElement = doc.createElement("pawn");
		docRoot.appendChild(rootElement);
		
		super.appendDataToXML(doc, rootElement);
		appendDataToXML(doc, rootElement);
		
		
	}
	
	@Override
	public void appendDataToXML(Document doc, Element rootElement)
	{
		Element attribute = doc.createElement("movementSpeed");
    	attribute.setTextContent(this.getRectangle().width + "");
    	rootElement.appendChild(attribute);
	}
	
	public void readFromXML(Element element) throws XMLParseException
	{
		super.readFromXML(element);
		this.movementSpeed = java.lang.Float.parseFloat( element.getElementsByTagName("movementSpeed").item(0).getTextContent());
	}
}
