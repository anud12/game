package game;

import game.gameLoop.GameLoop;
import game.gameLoop.MoveAction;
import game.library.*;
import game.util.WindowLog;

import java.awt.Point;
import java.awt.geom.Point2D;

public class MovementTest
{
    public static void main(String[] args)
    {
    	GameLoop gl = new GameLoop();
    	
    	WindowLog log = new WindowLog();
    	log.setTitle("Example");
    	log.println("Hello");
    	log.println("World");
    	
    	Point2D.Float point = new Point2D.Float(0,0);
    	
    	Pawn pawn = new Pawn(10, 40, point);
    	pawn.setMovementSpeed(0.1f);
    	
    	point = new Point2D.Float(300,300);
    	
    	log = new WindowLog();
    	
    	MoveAction move = new MoveAction(pawn, point, log);
    	
    	gl.addAction(move);
    	


    	
    	point = new Point2D.Float(0,0);
    	
    	pawn = new Pawn(10, 40, point);
    	pawn.setMovementSpeed(0.1f);
    	
    	point = new Point2D.Float(400,0);
    	
    	log = new WindowLog();
    	
    	move = new MoveAction(pawn, point, log);
    	
    	gl.addAction(move);
    	
    	gl.start();
    }
    
}
