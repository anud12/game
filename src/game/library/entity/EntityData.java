package game.library.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.geom.classes.PointF;
import game.geom.classes.Rectangle;
import game.library.IHasName;
import game.library.attribute.AttributeSelector;
import game.library.attribute.Attributes;
import game.library.entity.update.Vision;
import game.library.inventory.Inventory;
import game.library.player.Player;
import game.library.world.IWorld;

public class EntityData implements IHasName
{
	private Rectangle rectangle;
    private PointF center;
    private List<String> keywords;
    
    protected Color color;
    protected Player player;
    protected String name;

    boolean isAlive;
    
    protected Attributes attributes;
    
    protected Entity entity;
    
    public EntityData(int width, int heigth, PointF origin, IWorld world, Inventory inventory, Vision vision, Player player, Entity entity)
    {
    	attributes = new Attributes();
    	this.center = new PointF(origin.x,origin.y);
    	this.keywords = new ArrayList<String>();
    	
    	isAlive = true;
    	
    	//Geometry Initialization
    	try {
			rectangle = new Rectangle(heigth, width, origin.x , origin.y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	rectangle.x = origin.x;
    	rectangle.y = origin.y;
    	
    	rectangle.width = width;
    	rectangle.height = heigth;
    	
    	
    	color = Color.white;
    	
    	//ID initialization
    	
    	int i = 0;
    	do
    	{	
    		i++;
    	}
    	while(!EntityIDs.isFree(i));
    	EntityIDs.addID(i);
    	
    	attributes.set(AttributeSelector.ID(), i);
    	
    	//Entity dependency initialization
    	this.player = player;
    	player.addEntity(entity);
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
    	this.rectangle.setLocation(x, y);
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
    public void setAlive(boolean status)
	{
		isAlive = status;
	}
    public boolean isAlive()
    {
    	return isAlive;
    }
}
