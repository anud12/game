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
import game.geom.classes.PointI;
import game.library.Entity;
import game.library.pawn.Pawn;
import game.library.pawn.behaviour.Harvest;
import game.library.pawn.order.Move;
import game.library.world.IWorld;
import game.library.world.sector.Sector;
import game.library.world.sector.SectorGrid;
import game.library.world.sector.cell.SquareCell;
import game.library.world.sector.generator.DemoSectorGenerator;
import game.library.world.sector.generator.SquareSectorGenerator;
import game.network.Server;
import game.network.protocol.ChatProtocol;
import game.network.protocol.EngineProtocol;
import game.network.protocol.TextDisplayProtocol;

import java.awt.Color;

public class InteractiveTest 
{
	public static Engine gl;
	public static void main(String args[])
	{
		gl = new Engine(10000, 8);
		IWorld world = new ExperimentalWorld();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		PointF point;
    	Pawn pawn;
    	TextInterface inter;
		
		point = new PointF(70,100);
    	
    	pawn = new Pawn(20, 20, point, world);
    	pawn.setMovementSpeed(0.005f);
    	pawn.addKeyword("dropOff");
    	pawn.setColor(Color.gray);
    	
		inter = new TextInterface(pawn, executor);
		
		executor.execute(inter);
		
		gl.addAction(pawn.getController());
		
		Entity ent = new Entity(2, 2, new PointF(50,20), world);
		ent.setColor(new Color(139,69,19));
		ent.addKeyword("resource");
		
		point = new PointF(0,0);
    	
    	pawn = new Pawn(10, 10, point, world);
    	pawn.setMovementSpeed(0.010f);
    	//pawn.getController().setOrder(new Move(pawn, new PointF(50,200)));
    	pawn.getController().setBehaviour(new Harvest(pawn));
    	pawn.setColor(Color.white);
		
		inter = new TextInterface(pawn, executor);
		
		executor.execute(inter);
		
		gl.addAction(pawn.getController());
		
		for(int i = 0 ; i < 1000 ; i++)
		{
			IWorld world2 = new ExperimentalWorld();
			point = new PointF(-10,-10);
	    	
	    	pawn = new Pawn(10, 40, point, world2);
	    	pawn.setMovementSpeed(0.00001f);
	    	point = new PointF(Float.MAX_VALUE , Float.MAX_VALUE);
	    	pawn.getController().setOrder(new Move(pawn, point));
	    	
			gl.addAction(pawn.getController());
		}
		
		
		SectorGrid grid = new SectorGrid(200, new PointF (0,0));
		DemoSectorGenerator generator = new DemoSectorGenerator();
		SquareSectorGenerator squareGenerator = new SquareSectorGenerator();
		
		ArrayList<Sector> sectors = new ArrayList<Sector>();
		
		executor.execute(gl);
		executor.execute(new GLView(world, sectors));
		Server server = new Server();
		server.addProtocol(new ChatProtocol());
		server.addProtocol(new TextDisplayProtocol());
		server.addProtocol(new EngineProtocol(world));
		
		executor.execute(server);
		
		Random rand = new Random();
		int j = 0;
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
		System.out.println("Done creating sectors");
		
		//EngineQueueStressTest.main(gl);
	}
}
