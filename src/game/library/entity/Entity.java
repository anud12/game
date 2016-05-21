package game.library.entity;

import game.geom.classes.PointF;
import game.library.entity.engineBridges.EntityController;
import game.library.entity.engineBridges.EntityRelatedUpdate;
import game.library.entity.engineBridges.EntityRemovalChecker;
import game.library.entity.update.Vision;
import game.library.inventory.IHasInventory;
import game.library.inventory.Inventory;
import game.library.player.Player;
import game.library.world.IWorld;

public class Entity implements IHasInventory
{
	private IWorld world;
	
	protected Inventory inventory;
    protected EntityController controller;
    protected Vision vision;
	protected EntityRelatedUpdate updateBridge;
	protected EntityRemovalChecker removalChecker;
	
	protected EntityData data;
    
    
    //constructor Entity
    
    public Entity(int width, int heigth, PointF origin, IWorld world, Inventory inventory, Vision vision, Player player)
    {   	
    	this.world = world;
    	this.vision = vision;
    	this.inventory = inventory;
    	
    	data = new EntityData(width, heigth, origin, world, inventory, vision, player, this);
    	
    	//
    	controller = new EntityController(this);
    	updateBridge = new EntityRelatedUpdate(this);
    	removalChecker = new EntityRemovalChecker(this);
    	
    	//World dependency initialization
    	world.addEntity(this);
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
    public EntityData getData()
    {
    	return data;
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
	
    
}
