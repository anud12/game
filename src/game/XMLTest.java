package game;

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

import game.*;
import game.library.*;

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
    	
    	
    	
    	//Create root element
    	Element root = doc.createElement("root");
    	doc.appendChild(root);
    	
    	Entity ent = new Entity (0 , 0 , new Point2D.Float(0, 0));
    	ent.setCharacter('E');
    	ent.appendAllXML(doc);
    	
    	Pawn pawn = new Pawn(0 , 0 , new Point2D.Float(0, 0));
    	pawn.setMovementSpeed(5);
    	pawn.setCharacter('P');
    	pawn.appendAllXML(doc);
    	
    	
    	//Set the output file
    	StreamResult result = new StreamResult(new File("entity.xml"));
    	
    	//Write to disk
    	transformer.transform(source, result);
	}

}
