package game.tests;

import java.awt.geom.Point2D;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.glass.ui.Window;

import game.experimental.ExperimentalWorld;
import game.experimental.GLView;
import game.experimental.TextInterface;
import game.gameLoop.GameLoop;
import game.gameLoop.MoveAction;
import game.library.Entity;
import game.library.Pawn;
import game.library.interfaces.IWorld;
import game.util.WindowLog;

public class InteractiveTest 
{
	public static GameLoop gl;
	public static void main(String args[])
	{
		gl = new GameLoop();
		IWorld world = new ExperimentalWorld();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		
		for(int i = 0 ; i < 1 ; i++)
		{
			Point2D.Float point = new Point2D.Float(0,0);
	    	
	    	Pawn pawn = new Pawn(10, 10, point, world);
	    	pawn.setMovementSpeed(0.010f);
	    	
			TextInterface inter = new TextInterface(pawn);
			
			executor.execute(inter);
			
			gl.addAction(pawn);
		}
		
		Point2D.Float point = new Point2D.Float(0,0);
    	
    	Pawn pawn = new Pawn(20, 20, point, world);
    	pawn.setMovementSpeed(0.005f);
    	pawn.addKeyword("dropOff");
    	
		TextInterface inter = new TextInterface(pawn);
		
		executor.execute(inter);
		
		gl.addAction(pawn);
		
		Entity ent = new Entity(2, 2, new Point2D.Float(50,20), world);
		
		ent.addKeyword("resource");
		
		System.out.println("Find " + world.getClosest("resource"));
		
		for(int i = 0 ; i < 0 ; i++)
		{
			point = new Point2D.Float(0,0);
	    	
	    	pawn = new Pawn(10, 40, point, world);
	    	pawn.setMovementSpeed(0.00001f);
	    	point = new Point2D.Float(Float.MAX_VALUE , Float.MAX_VALUE);
	    	MoveAction action = new MoveAction(pawn, point);
	    	
			gl.addAction(action);
		}
		
		executor.execute(gl);
		executor.execute(new GLView(world));
	}
}
