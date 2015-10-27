package game.library;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.engine.IEngineAction;
import game.library.controllers.PawnController;
import game.library.world.IWorld;
import game.util.ISerializableXML;

public class Pawn extends Entity implements ISerializableXML{

	protected float movementSpeed;
	protected PawnController controller;
	
	//Active Entity inteface
	LinkedList<IEngineAction>actions;
	
	public Pawn(int width, int height, Point2D.Float origin, IWorld world) {
		super(width, height, origin, world);
		actions = new LinkedList<IEngineAction>();
		
		controller = new PawnController(this);
	}
	public Pawn(Element element, IWorld world) throws XMLParseException
	{
		super(element, world);
		actions = new LinkedList<IEngineAction>();
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

	public PawnController getController()
	{
		return controller;
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
	
	//GameLoop interaction
	
}
