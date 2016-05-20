package game.library.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.geom.classes.PointF;
import game.geom.classes.Rectangle;
import game.library.IHasName;
import game.library.attribute.AttributeSelector;
import game.library.attribute.Attributes;
import game.library.entity.engineBridges.EntityController;
import game.library.entity.engineBridges.EntityRelatedUpdate;
import game.library.entity.engineBridges.EntityRemovalChecker;
import game.library.entity.update.Vision;
import game.library.inventory.IHasInventory;
import game.library.inventory.Inventory;
import game.library.player.Player;
import game.library.world.IWorld;

public class Entity implements IHasName, IHasInventory
{

    //Attributes:
    private Rectangle rectangle;
    private PointF center;
    private List<String> keywords;
    private IWorld world;
    protected Color color;
    protected Player player;
    protected String name;

    boolean isAlive;

    protected Inventory inventory;
    protected EntityController controller;
    protected Vision vision;
	protected EntityRelatedUpdate updateBridge;
	protected EntityRemovalChecker removalChecker;
	
    protected Attributes attributes;
    
    //constructor Entity
    
    public Entity(int width, int heigth, PointF origin, IWorld world, Inventory inventory, Vision vision, Player player)
    {
    	attributes = new Attributes();
    	this.center = new PointF(origin.x,origin.y);
    	this.keywords = new ArrayList<String>();
    	
    	isAlive = true;
    	
    	this.world = world;
    	this.vision = vision;
    	this.inventory = inventory;
    	
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
    	
    	//World dependency initialization
    	world.addEntity(this);
    	
    	//Entity dependency initialization
    	this.player = player;
    	player.addEntity(this);
    	
    	//
    	
    	controller = new EntityController(this);
    	updateBridge = new EntityRelatedUpdate(this);
    	removalChecker = new EntityRemovalChecker(this);
    	
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
    public float getSightRadius()
    {
    	return vision.getRadius();
    }
    public EntityController getController()
	{
		return controller;
	}
    public EntityRelatedUpdate getUpdate()
    {
    	return updateBridge;
    }
    public Vision getVision()
    {
    	return vision;
    }
    public EntityRemovalChecker getRemovalChecker()
    {
    	return removalChecker;
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
    //get IWorld
    public IWorld getWorld(){
    	return world;
    }
	@Override
	public Inventory getInventory()
	{
		return inventory;
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
