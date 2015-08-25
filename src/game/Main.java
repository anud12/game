package game;

import game.gameLoop.GameLoop;
import game.gameLoop.TestAction;
import game.library.Entity;
import game.util.WindowLog;

import java.awt.Point;

public class Main
{
    public static void main(String[] args)
    {
    	WindowLog log = new WindowLog();
    	
    	log.setTitle("Example");
    	log.println("Hello");
    	log.println("World");
    	
    	Point point = new Point();
    	
    	Entity entity = new Entity(10, 40, point);
    	
    	point = new Point();
    	
    	GameLoop gl = new GameLoop();
    	
    	
    	log = new WindowLog();
    	TestAction move = new TestAction(entity, log);
    	gl.addAction(move);
    	
    	
    	log = new WindowLog();
    	move = new TestAction(entity, log);
    	gl.addAction(move);
    	

    	log = new WindowLog();
    	move = new TestAction(entity, log);
    	gl.addAction(move);
    	
    	gl.start();
    }
    
}
