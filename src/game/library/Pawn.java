package game.library;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.gameLoop.IGameLoopAction;
import game.library.interfaces.IGameLoopEntity;
import game.util.ISerializableXML;

public class Pawn extends Entity implements ISerializableXML, IGameLoopEntity{

	protected float movementSpeed;
	
	//Active Entity inteface
	LinkedList<IGameLoopAction>actions;
	
	public Pawn(int width, int height, Point2D.Float origin) {
		super(width, height, origin);
		actions = new LinkedList<IGameLoopAction>();
	}
	public Pawn(Element element) throws XMLParseException
	{
		super(element);
		actions = new LinkedList<IGameLoopAction>();
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
	
	//GameLoop interaction
	
	@Override
	public void execute(double deltaTime) {
		if(actions.isEmpty())
			return;
		actions.getFirst().execute(deltaTime);
	}
	@Override
	public boolean isCompleted() {
		if(actions.isEmpty())
			return true;
		return actions.getFirst().isCompleted();
	}
	@Override
	public void onComplete() {
		if(actions.isEmpty())
			return;
		actions.getFirst().onComplete();
		actions.removeFirst();
	}
	@Override
	public void addAction(IGameLoopAction newAction) {
		actions.add(newAction);
		
	}
	@Override
	public void removeCurrentAction() {
		actions.remove(0);
	}
	@Override
	public synchronized void clearActionList() {
		actions.clear();
	}
	@Override
	public boolean isRemovable() {
		return false;
	}
	public int getActionSize()
	{
		return actions.size();
	}
}
