package game.tests;

import game.experimental.ExperimentalWorld;
import game.gameLoop.GameLoop;
import game.gameLoop.MoveAction;
import game.library.*;
import game.library.interfaces.IWorld;
import game.util.WindowLog;

import java.awt.geom.Point2D;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MovementTest
{
    public static void main(String[] args)
    {
    	IWorld world = new ExperimentalWorld();
    	GameLoop gl = new GameLoop();
    	
    	WindowLog log = new WindowLog();
    	log.setTitle("Example");
    	log.println("Hello");
    	log.println("World");
    	
    	for(int i = 0 ; i < 0; i++)
    	{
    		Point2D.Float point = new Point2D.Float(0,0);
        	
        	Pawn pawn = new Pawn(10, 40, point, world);
        	pawn.setMovementSpeed(0.1f);
        	
        	point = new Point2D.Float(300,300);
        	
        	MoveAction move = new MoveAction(pawn, point);
        	
        	gl.addAction(move);
    	}    	
    	
    	/*
    	Point2D.Float point = new Point2D.Float(0,0);
    	
    	Pawn pawn = new Pawn(10, 40, point);
    	pawn.setMovementSpeed(0.001f);
    	
    	point = new Point2D.Float(300,300);
    	
    	log = new WindowLog();
    	
    	MoveAction move = new MoveAction(pawn, point, log);
    	
    	gl.addAction(move);	
    	  
    	*/
    	
    	Point2D.Float point = new Point2D.Float(0,0);
    	
    	Pawn pawn = new Pawn(10, 40, point, world);
    	pawn.setMovementSpeed(0.1f);
    	
    	point = new Point2D.Float(200,0);
    	
    	log = new WindowLog();
    	
    	MoveAction move = new MoveAction(pawn, point, log);
    	
    	pawn.addAction(move);
    	
    	point = new Point2D.Float(0,200);
    	move = new MoveAction(pawn, point, log);
    	pawn.addAction(move);
    	
    	gl.addAction(pawn);
    	ExecutorService executor = Executors.newSingleThreadExecutor();
    	
    	executor.execute(gl);
    }
    
}
