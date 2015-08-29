package game;

import game.gameLoop.GameLoop;
import game.gameLoop.TestAction;
import game.library.*;
import game.util.WindowLog;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Main
{
    public static void main(String[] args)
    {
    	Point2D.Float point = new Point2D.Float(0,0);
    	
    	Pawn pawn = new Pawn(10, 40, point);
    	pawn.setMovementSpeed(0.1f);
    	
    	point = new Point2D.Float(10,20);
    	
    	GameLoop gl = new GameLoop();
    	
    	WindowLog log = new WindowLog();
    	log.setTitle("Example");
    	log.println("Hello");
    	log.println("World");
    	
    	log = new WindowLog();
    	TestAction move = new TestAction(pawn, log);
    	move.setLocation(point);
    	gl.addAction(move);
    	gl.start();
    }
    
}
