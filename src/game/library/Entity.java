package game.library;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;

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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import game.util.ISerializableXML;

public class Entity implements ISerializableXML
{

    //Attributes:
	private char character;
    private Rectangle2D.Float rectangle;
    private Point2D.Float center;
    
    
    //Methods:
    //get/set character [?]
    public void setCharacter(char ch)
    {
        character = ch;
    }
    public char getCharacter()
    {
        return character;
    }
   
    //get/set rectangle 
    public Rectangle2D.Float getRectangle()
    {
        return rectangle;
    }
    public void setRectangle(Rectangle2D.Float rectangle)
    {
        this.rectangle = rectangle;
    }
    
    //gets the location of our entity
    public Point2D.Float getCenter()
    {
    	return this.center;
    }
    public void setCenter(float x , float y)
    {
    	this.center.x = x;
    	this.center.y = y;
    }
    //constructor Entity
    public Entity(int width,int height,Point2D.Float origin)
    {
    	rectangle = new Rectangle2D.Float();
    	
    	rectangle.x = origin.x;
    	rectangle.y = origin.y;
    	
    	this.center = new Point2D.Float(origin.x,origin.y);
    	
    	rectangle.width = width;
    	rectangle.height = height;
    }
    
    //ISerializableXML
    @Override
	public void appendAllXML(Document doc) 
	{
		Element rootElement = doc.createElement("entity");
    	
    	appendChildXML(doc, rootElement);
    	Element docRoot = doc.getDocumentElement();
    	docRoot.appendChild(rootElement);
    	
	}
	@Override
	public void appendChildXML(Document doc, Element rootElement) {
		Element attribute = doc.createElement("character");
    	attribute.setTextContent(this.character + "");
    			
    	rootElement.appendChild(attribute);
    	
    	attribute = doc.createElement("width");
    	attribute.setTextContent(this.getRectangle().width + "");
    			
    	rootElement.appendChild(attribute);
    	
    	attribute = doc.createElement("height");
    	attribute.setTextContent(this.getRectangle().height + "");
    			
    	rootElement.appendChild(attribute);
    	
    	Element position = doc.createElement("position");
    			
    	rootElement.appendChild(position);
    	
    	attribute = doc.createElement("x");
    	attribute.setTextContent(this.center.x + "");
    			
    	position.appendChild(attribute);
    	
    	attribute = doc.createElement("y");
    	attribute.setTextContent(this.center.y + "");
    			
    	position.appendChild(attribute);
	}
    
    
    /*
	@Override
    public int compareTo(Object o) {
	try{
		Entity e = (Entity)o;	
		if(this.getPosition().distance(new Point(0,0)) == e.getPosition().distance(new Point(0,0)))
		{
			return 0;
		}
		else if (this.getPosition().distance(new Point(0,0)) > e.getPosition().distance(new Point(0,0)))
		{
			return 1;
		}
		else return -1;
	}
	catch(Exception ex){
		return 0;
	}
	}
	*/
}
