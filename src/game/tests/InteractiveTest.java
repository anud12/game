package game.tests;

import java.awt.geom.Point2D;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import game.experimental.ExperimentalWorld;
import game.experimental.GLView;
import game.experimental.TextInterface;
import game.gameLoop.GameLoop;
import game.gameLoop.MoveAction;
import game.library.Entity;
import game.library.Pawn;
import game.library.interfaces.IWorld;
import game.util.WindowLog;
import java.awt.Color;

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
			Point2D.Float point = new Point2D.Float(10,30);
	    	
	    	Pawn pawn = new Pawn(10, 10, point, world);
	    	pawn.setMovementSpeed(0.010f);
	    	pawn.setColor(Color.white);
			TextInterface inter = new TextInterface(pawn, executor);
			
			executor.execute(inter);
			
			gl.addAction(pawn.getController());
		}
		
		Point2D.Float point = new Point2D.Float(50,100);
    	
    	Pawn pawn = new Pawn(20, 20, point, world);
    	pawn.setMovementSpeed(0.005f);
    	pawn.addKeyword("dropOff");
    	pawn.setColor(Color.gray);
		TextInterface inter = new TextInterface(pawn, executor);
		
		executor.execute(inter);
		
		gl.addAction(pawn.getController());
		
		Entity ent = new Entity(2, 2, new Point2D.Float(50,20), world);
		ent.setColor(new Color(139,69,19));
		ent.addKeyword("resource");
		
		
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
