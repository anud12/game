package game.library.pawn;

import java.util.LinkedList;

import javax.management.modelmbean.XMLParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.engine.IEngineAction;
import game.geom.classes.PointF;
import game.library.Entity;
import game.library.attribute.AttributeSelector;
import game.library.attribute.Attributes;
import game.library.pawn.controller.PawnController;
import game.library.world.IWorld;
import game.util.ISerializableXML;

public class Pawn extends Entity {

	protected float movementSpeed;
	protected PawnController controller;
	
	
	//Active Entity inteface
	LinkedList<IEngineAction>actions;
	
	public Pawn(int width, int height, PointF origin, IWorld world) {
		super(width, height, origin, world);
		actions = new LinkedList<IEngineAction>();
		
		controller = new PawnController(this);
		
	}
	
	
	public PawnController getController()
	{
		return controller;
	}
	
}
