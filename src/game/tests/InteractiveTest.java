package game.tests;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.engine.Engine;
import game.experimental.ExperimentalWorld;
import game.experimental.GLView;
import game.experimental.TextInterface;
import game.library.Entity;
import game.library.Pawn;
import game.library.pawnOrder.Harvest;
import game.library.pawnOrder.Move;
import game.library.world.IWorld;
import game.library.world.SectorGrid;

import java.awt.Color;

public class InteractiveTest 
{
	public static Engine gl;
	public static void main(String args[])
	{
		gl = new Engine(250000, 8);
		IWorld world = new ExperimentalWorld();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Point2D.Float point;
    	Pawn pawn;
    	TextInterface inter;
		
		point = new Point2D.Float(70,100);
    	
    	pawn = new Pawn(20, 20, point, world);
    	pawn.setMovementSpeed(0.005f);
    	pawn.addKeyword("dropOff");
    	pawn.setColor(Color.gray);
    	
		inter = new TextInterface(pawn, executor);
		
		executor.execute(inter);
		
		gl.addAction(pawn.getController());
		
		Entity ent = new Entity(2, 2, new Point2D.Float(50,20), world);
		ent.setColor(new Color(139,69,19));
		ent.addKeyword("resource");
		
		point = new Point2D.Float(10,30);
    	
    	pawn = new Pawn(10, 10, point, world);
    	pawn.setMovementSpeed(0.010f);
    	pawn.getController().setOrder(new Harvest(pawn));
    	pawn.setColor(Color.white);
		
		inter = new TextInterface(pawn, executor);
		
		executor.execute(inter);
		
		gl.addAction(pawn.getController());
		
		for(int i = 0 ; i < 0 ; i++)
		{
			IWorld world2 = new ExperimentalWorld();
			point = new Point2D.Float(-10,-10);
	    	
	    	pawn = new Pawn(10, 40, point, world2);
	    	pawn.setMovementSpeed(0.00001f);
	    	point = new Point2D.Float(Float.MAX_VALUE , Float.MAX_VALUE);
	    	pawn.getController().setOrder(new Move(pawn, point));
	    	
			gl.addAction(pawn.getController());
		}
		
		executor.execute(gl);
		
		SectorGrid grid = new SectorGrid(2,2, 12000);
		
		
		executor.execute(new GLView(world, grid));
	}
}
