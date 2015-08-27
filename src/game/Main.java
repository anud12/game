package game;

import game.gameLoop.GameLoop;
import game.gameLoop.TestAction;
import game.library.Entity;
import game.util.WindowLog;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Main
{
    public static void main(String[] args)
    {
    	
    	
    	Point2D.Float point = new Point2D.Float(0,0);
    	
    	Entity entity = new Entity(10, 40, point);
    	
    	point = new Point2D.Float(1,1000);
    	
    	GameLoop gl = new GameLoop();
    	
    	WindowLog log = new WindowLog();
    	log.setTitle("Example");
    	log.println("Hello");
    	log.println("World");
    	
    	log = new WindowLog();
    	TestAction move = new TestAction(entity, log);
    	gl.addAction(move);
    	gl.start();
    }
    
}
