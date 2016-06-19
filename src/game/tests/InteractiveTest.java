package game.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.engine.Engine;
import game.experimental.ExperimentalWorld;
import game.experimental.GLView;
import game.experimental.TextInterface;
import game.geom.classes.PointF;
import game.geom.classes.RightTriangle;
import game.graphicalUserInterface.MainFrame;
import game.graphicalUserInterface.panels.EngineJPanel;
import game.graphicalUserInterface.panels.PanelSelector;
import game.library.NameCollection;
import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.entity.behaviour.Harvest;
import game.library.entity.behaviour.Idle;
import game.library.entity.order.Move;
import game.library.entity.removalCondition.RemoveCountdown;
import game.library.entity.removalCondition.RemoveResourceEqualLess;
import game.library.entity.update.VisionCircle;
import game.library.inventory.BottomlessInventory;
import game.library.inventory.NoSpaceInventory;
import game.library.inventory.item.Item;
import game.library.player.Player;
import game.library.world.IWorld;
import game.library.world.sector.Sector;
import game.library.world.sector.SectorGrid;
import game.library.world.sector.generator.DemoSectorGenerator;
import game.network.Server;
import game.network.services.ChatService;
import game.network.services.EngineControlService;
import game.network.services.TextDisplayService;
import game.network.services.WorldControlService;

import java.awt.Color;

public class InteractiveTest 
{
	public static Engine engine;
	public static void main(String args[])
	{
		engine = new Engine(1000, 8);
		
		IWorld world = new ExperimentalWorld();
		
		Item resource1 = new Item();
		resource1.setName("RESOURCE 1");
		
		world.addItem(resource1);
		
		NameCollection<Player> players = new NameCollection<Player>();
		
		Player playerHarvester = new Player();
		playerHarvester.setName("HARVESTER");
		players.add(playerHarvester);

		Player playerCollector = new Player();
		playerCollector.setName("COLLECTOR");
		players.add(playerCollector);
		
		Player neutral = new Player();
		playerHarvester.setName("NEUTRAL");
		
		players.add(playerHarvester);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		PointF point;
    	Entity entity;
    	TextInterface inter;
    	
    	MainFrame mainWindow = new MainFrame();
    	PanelSelector selector = new PanelSelector();
    	mainWindow.add(selector);
		
    	EngineJPanel enginePanel = new EngineJPanel(engine);
    	selector.addPanel("Engine", enginePanel);
    	executor.execute(enginePanel);
    	
		point = new PointF(150,200);
		entity = new Entity(20, 20, point, world, new NoSpaceInventory(), new VisionCircle(250,10), playerCollector);
    	entity.getData().set(AttributeSelector.movementSpeed(), 0.005f);
    	entity.getData().addKeyword("dropOff");
    	entity.getData().set(AttributeSelector.color(), Color.gray);
    	
		inter = new TextInterface(entity, executor);
		selector.addPanel(entity.getData().get(AttributeSelector.ID()).toString(), inter);
		
		executor.execute(inter);
		
		engine.addAction(entity.getController());
		engine.addRelated(entity.getUpdate());
		
		entity= new Entity(2, 2, new PointF(50,20), world, new BottomlessInventory(), new VisionCircle(0,0), neutral);
		entity.getData().set(AttributeSelector.color(), new Color(139,69,19));
		entity.getData().addKeyword("resource");
		
		entity.getInventory().addItem(resource1, 5);
		entity.getRemovalChecker().addCondition(new RemoveResourceEqualLess(resource1, 0));
		engine.addAliveCheckAction(entity.getRemovalChecker());

		entity= new Entity(2, 2, new PointF(100,40), world, new BottomlessInventory(), new VisionCircle(0,0), neutral);
		entity.getData().set(AttributeSelector.color(), new Color(139,69,19));
		entity.getData().addKeyword("resource");
		
		entity.getInventory().addItem(resource1, 5);
		entity.getRemovalChecker().addCondition(new RemoveResourceEqualLess(resource1, 0));
		engine.addAliveCheckAction(entity.getRemovalChecker());
		
		
		inter = new TextInterface(entity, executor);
		selector.addPanel(entity.getData().get(AttributeSelector.ID()).toString(), inter);
		
		executor.execute(inter);
		
		engine.addAction(entity.getController());
		engine.addRelated(entity.getUpdate());
		
		
		point = new PointF(10,10);
    	
    	entity = new Entity(10, 10, point, world, new BottomlessInventory(), new VisionCircle(50,10), playerCollector);
    	entity.getData().set(AttributeSelector.movementSpeed(), 0.05f);
    	entity.getController().setBehaviour(new Harvest(entity));
    	entity.getData().set(AttributeSelector.color(), Color.gray);
    	
    	
		inter = new TextInterface(entity, executor);
		selector.addPanel(entity.getData().get(AttributeSelector.ID()).toString(), inter);
		
		executor.execute(inter);
		
		engine.addAction(entity.getController());
		engine.addRelated(entity.getUpdate());
		engine.addAliveCheckAction(entity.getRemovalChecker());
		/*
		point = new PointF(120,130);
    	
    	entity = new Entity(10, 10, point, world, new BottomlessInventory(), new VisionCircle(50,10), neutral);
    	entity.set(AttributeSelector.movementSpeed(), 0.05f);
    	entity.getController().setBehaviour(new Idle());
    	entity.set(AttributeSelector.color(), Color.gray);
		
		inter = new TextInterface(entity, executor);
		selector.addPanel(entity.get(AttributeSelector.ID()).toString(), inter);
		
		executor.execute(inter);
		
		engine.addAction(entity.getController());
		engine.addRelated(entity.getUpdate());
		*/
		IWorld world2 = new ExperimentalWorld();
		for(int i = 0 ; i < 0 ; i++)
		{
			point = new PointF(-10,-10);
	    	
	    	entity = new Entity(10, 40, point, world2, new NoSpaceInventory(), new VisionCircle(100,100), neutral);
	    	entity.getData().set(AttributeSelector.movementSpeed(), 0.00001f);
	    	point = new PointF(Float.MAX_VALUE , Float.MAX_VALUE);
	    	entity.getController().setOrder(new Move(entity, point));
	    	
			engine.addAction(entity.getController());
			engine.addRelated(entity.getUpdate());
		}
		
		
		//engine.addVariable(world);
		engine.addUnrelated(world);
		engine.addRelated(entity.getUpdate());
		
		SectorGrid grid = new SectorGrid(100, new PointF (0,0));
		DemoSectorGenerator generator = new DemoSectorGenerator();
		
		ArrayList<Sector> sectors = new ArrayList<Sector>();
		
		executor.execute(engine);
		
		//engine.pause(true);
		
		
		Server server = new Server();
		server.addService(new ChatService());
		server.addService(new TextDisplayService());
		server.addService(new WorldControlService(world, players));
		server.addService(new EngineControlService(engine));
		
		executor.execute(server);
		
		Random rand = new Random();
		for(int i = 0 ; i < 100; i++)
		{
			synchronized(sectors)
			{
				
				switch(rand.nextInt(3) + 0)
				{
					case 0:
					{
						sectors.add(generator.generate(grid));
						break;
					}
					case 1:
					{
						sectors.add(generator.generate2(grid));
						break;
					}
					case 2:
					{
						sectors.add(generator.generate3(grid));
						//System.out.println("SECTOR TRIANGLES");
						Iterator<RightTriangle> iterator = sectors.get(0).getList().iterator();
						while(iterator.hasNext())
						{
							iterator.next().print();
							//System.out.println();
						}
						break;
					}
				}
			}
			
			
			
			try {
				Thread.sleep(0,1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		executor.execute(new GLView(world, sectors));
		System.out.println("Done creating sectors");
		
		//EngineQueueStressTest.main(engine);
	}
}
