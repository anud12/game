package game;

import game.gameLoop.GameLoop;
import game.gameLoop.TestAction;
import game.library.Entity;
import game.library.Rectangle;

import java.awt.AWTEvent;
import java.awt.Point;

public class Main
{
    public static void main(String[] args)
    {
    	Point point = new Point();
    	
    	Entity entity = new Entity(10, 40, point);
    	
    	point = new Point();
    	
    	GameLoop gl = new GameLoop();
    	
    	TestAction move = new TestAction(entity);
    	
    	gl.addAction(move);
    	
    	gl.start();
    }
    
}
