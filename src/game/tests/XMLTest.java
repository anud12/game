package game.tests;

import java.awt.geom.Point2D;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.experimental.ExperimentalWorld;
import game.library.*;
import game.library.interfaces.IWorld;
import game.tests.*;

public class XMLTest {

	public static void main(String[] args) throws ParserConfigurationException, TransformerException 
	{
		//Initialize the XML document
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    	Document doc = dBuilder.newDocument();
    	
    	//Initialize the XML writer
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	Transformer transformer = transformerFactory.newTransformer();
    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    	
    	//??
    	DOMSource source = new DOMSource(doc);
    	
    	//Initializations
    	IWorld world = new ExperimentalWorld();
    	
    	Entity ent = new Entity (0 , 0 , new Point2D.Float(0, 0), world);
    	Pawn pawn = new Pawn(0 , 0 , new Point2D.Float(0, 0), world);
    	pawn.setMovementSpeed(5);
    	
    	//Create root element
    	Element root = doc.createElement("root");
    	doc.appendChild(root);
    	
    	//Transform to xml
    	ent.appendObjectToXML(doc);
    	pawn.appendObjectToXML(doc);
    	
    	
    	//Set the output file
    	StreamResult result = new StreamResult(new File("test.xml"));
    	
    	//Write to disk
    	transformer.transform(source, result);
	}

}
