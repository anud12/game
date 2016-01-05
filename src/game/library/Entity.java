package game.library;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.bcel.internal.classfile.Attribute;

import game.geom.classes.PointF;
import game.geom.classes.Rectangle;
import game.library.attribute.AttributeSelector;
import game.library.attribute.Attributes;
import game.library.player.Player;
import game.library.world.IWorld;
import game.util.ISerializableXML;

public class Entity implements IHasName
{

    //Attributes:
    private Rectangle rectangle;
    private PointF center;
    private List<String> keywords;
    private IWorld world;
    protected Color color;
    protected Player player;
    protected String name;
    
    protected Attributes attributes;
    
    //constructor Entity
    
    public Entity(int width, int heigth, PointF origin, IWorld world)
    {
    	constructor(width, heigth, origin, world);
    }
    public Entity(int width, int heigth, PointF origin, IWorld world, Player player)
    {
    	constructor(width, heigth, origin, world);
    	this.player = player;
    	player.addEntity(this);
    }
    
    private void constructor(int width, int heigth, PointF origin, IWorld world)
    {
    	attributes = new Attributes();
    	
    	this.world = world;
    	
    	//Geometry Initialization
    	try {
			rectangle = new Rectangle(heigth, width, origin.x , origin.y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	rectangle.x = origin.x;
    	rectangle.y = origin.y;
    	
    	this.center = new PointF(origin.x,origin.y);
    	
    	rectangle.width = width;
    	rectangle.height = heigth;
    	this.keywords = new ArrayList<String>();
    	
    	color = Color.white;
    	
    	//ID initialization
    	Random random = new Random();
    	
    	int i = 0;
    	do
    	{
    		i++;
    	}
    	while(!EntityIDs.isFree(i));
    	EntityIDs.addID(i);
    	
    	attributes.set(AttributeSelector.ID(), i);
    	
    	//World dependency initialization
    	world.addEntity(this);
    }
    
    //Methods:
    public void setName(String name)
    {
    	this.name = name;
    }
	@Override
	public String getName()
	{
		return name;
	}
    public void set(AttributeSelector selector, Object value)
	{    	
		attributes.set(selector, value);
	}
	public Object get(AttributeSelector selector)
	{
		return attributes.get(selector);
	}
	public Player getPlayer()
	{
		return player;
	}
    //get/set rectangle 
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    public void setRectangle(Rectangle rectangle)
    {
        this.rectangle = rectangle;
    }
    
    //gets the location of our entity
    public PointF getCenter()
    {
    	return rectangle.getCenter();
    }
    public void setCenter(float x , float y)
    {
    	this.center.x = x;
    	this.center.y = y;
    }
    
    //set/get Keyword
    public void addKeyword(String keyword)
    {
    	this.keywords.add(keyword);
    }
    public boolean containsKeyword(String keyword)
    {
    	return this.keywords.contains(keyword);
    }
    //get IWorld
    public IWorld getWorld(){
    	return world;
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
