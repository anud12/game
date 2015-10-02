package game.tests;

import java.awt.geom.Point2D;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.glass.ui.Window;

import game.experimental.TextInterface;
import game.gameLoop.GameLoop;
import game.gameLoop.MoveAction;
import game.library.Pawn;
import game.util.WindowLog;

public class InteractiveTest 
{
	public static GameLoop gl;
	public static void main(String args[])
	{
		gl = new GameLoop();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		
		for(int i = 0 ; i < 1 ; i++)
		{
			Point2D.Float point = new Point2D.Float(0,0);
	    	
	    	Pawn pawn = new Pawn(10, 40, point);
	    	pawn.setMovementSpeed(0.001f);
	    	
			TextInterface inter = new TextInterface(pawn);
			
			executor.execute(inter);
			
			gl.addAction(pawn);
		}
		
		for(int i = 0 ; i < 200000 ; i++)
		{
			Point2D.Float point = new Point2D.Float(0,0);
	    	
	    	Pawn pawn = new Pawn(10, 40, point);
	    	pawn.setMovementSpeed(0.00001f);
	    	point = new Point2D.Float(Float.MAX_VALUE , Float.MAX_VALUE);
	    	MoveAction action = new MoveAction(pawn, point);
	    	
			gl.addAction(action);
		}
		
		/*
		WindowLog log;
		Point2D.Float point = new Point2D.Float(0,0);
    	
    	Pawn pawn = new Pawn(10, 40, point);
    	pawn.setMovementSpeed(0.001f);
    	
    	point = new Point2D.Float(Float.MAX_VALUE , Float.MAX_VALUE);
    	
    	log = new WindowLog();
    	
    	MoveAction move = new MoveAction(pawn, point, log);
    	gl.addAction(move);
    	*/
		executor.execute(gl);
		
	}
}
