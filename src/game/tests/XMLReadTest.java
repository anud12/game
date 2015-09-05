package game.tests;

import java.io.File;
import java.io.IOException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import game.library.Entity;
import game.library.Pawn;

public class XMLReadTest 
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLParseException, TransformerException 
	{
		File inputFile = new File("test.xml");
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(inputFile);
		
		Element rootElement = doc.getDocumentElement();
		
		NodeList list = rootElement.getElementsByTagName("entity");
		
		Entity entity[] = new Entity[list.getLength()];
		
		for(int i = 0 ; i < list.getLength() ; i++)
		{
			entity[i] = new Entity((Element) list.item(0));
		}
		
		NodeList list2 = rootElement.getElementsByTagName("pawn");
		
		Pawn pawn[] = new Pawn[list2.getLength()];
		
		for(int i = 0 ; i < list.getLength() ; i++)
		{
			pawn[i] = new Pawn((Element) list2.item(0));
		}
		
		
		//Write Results
		//Initialize the XML document
    	doc = dBuilder.newDocument();
    	
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
    	
    	for(int i = 0 ; i < list.getLength() ; i++)
		{
			entity[i].appendObjectToXML(doc);
		}
    	for(int i = 0 ; i < list.getLength() ; i++)
		{
			pawn[i].appendObjectToXML(doc);
		}
    	
    	//Set the output file
    	StreamResult result = new StreamResult(new File("readResult.xml"));
    	
    	//Write to disk
    	transformer.transform(source, result);
	}
}
