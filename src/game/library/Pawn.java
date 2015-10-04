package game.library;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.gameLoop.IGameLoopAction;
import game.library.interfaces.IGameLoopEntity;
import game.library.interfaces.IPawnBehaviour;
import game.library.interfaces.IWorld;
import game.util.ISerializableXML;

public class Pawn extends Entity implements ISerializableXML, IGameLoopEntity{

	protected float movementSpeed;
	protected IPawnBehaviour behaviour;
	
	//Active Entity inteface
	LinkedList<IGameLoopAction>actions;
	
	public Pawn(int width, int height, Point2D.Float origin, IWorld world) {
		super(width, height, origin, world);
		actions = new LinkedList<IGameLoopAction>();
	}
	public Pawn(Element element, IWorld world) throws XMLParseException
	{
		super(element, world);
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
	
	public void setBehavior(IPawnBehaviour behaviour)
	{
		this.behaviour = behaviour;
	}
	public IPawnBehaviour getBehavior()
	{
		return behaviour;
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
		if(behaviour != null){
			behaviour.execute(deltaTime);
			return;
		}
		if(actions.isEmpty())
			return;
		actions.getFirst().execute(deltaTime);
	}
	@Override
	public boolean isCompleted(IGameLoopAction action) {
		if(behaviour != null){
			return behaviour.isCompleted(action);
		}
		
		if(actions.isEmpty())
			return true;
		return actions.getFirst().isCompleted(action);
	}
	@Override
	public void onComplete(IGameLoopAction action) {
		if(behaviour != null){
			behaviour.onComplete(action);
			return;
		}
		if(actions.isEmpty())
			return;
		actions.getFirst().onComplete(action);
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
	public boolean isRemovable(IGameLoopAction action) {
		return false;
	}
	public int getActionSize()
	{
		return actions.size();
	}
}
