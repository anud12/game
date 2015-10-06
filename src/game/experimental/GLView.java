package game.experimental;

import java.awt.geom.Point2D;
import java.util.Iterator;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import game.library.Entity;
import game.library.interfaces.IWorld;

public class GLView implements Runnable{
	
	protected IWorld world;
	protected Point2D.Float position;
	
	boolean clickedFirstRun = true;
	boolean afterClickFirstRun = true;
	
	Point2D.Float previousPosition;
	
	public GLView(IWorld world)
	{
		position = new Point2D.Float(0, 0);
		this.world = world;
		
		previousPosition = new Point2D.Float(position.x, position.y);
	}
	public void setPosition(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
	}

	@Override
	public void run() {
	 try {
	        Display.setDisplayMode(new DisplayMode(800,600));
	        Display.create();
	    } catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	  
	    // init OpenGL
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, 800, 0, 600, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glClearColor(0, 0, 0, 0.5f);
	    while (!Display.isCloseRequested()) {
	        // Clear the screen and depth buffer
	    	
	        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
	        
	        // set the color of the quad (R,G,B,A)
	        GL11.glColor3f(1f,1f,1f);
	        
	        Iterator<Entity> iterator = world.getIterator();
	        while(iterator.hasNext())
	        {
	        	Entity ent = iterator.next();
	        	GL11.glBegin(GL11.GL_QUADS);
	        	GL11.glVertex2f(
	        			ent.getRectangle().x + position.x,
	        			ent.getRectangle().y + position.y);
	        	GL11.glVertex2f(
	        			ent.getRectangle().x + ent.getRectangle().height + position.x,
	        			ent.getRectangle().y + position.y);
	        	GL11.glVertex2f(
	        			ent.getRectangle().x + ent.getRectangle().height + position.x,
	        			ent.getRectangle().y + ent.getRectangle().width + position.y);
	        	GL11.glVertex2f(
	        			ent.getRectangle().x + position.x,
	        			ent.getRectangle().y + ent.getRectangle().width + position.y);
	        	GL11.glEnd();
	        }
	        input();
	        Display.update();
	    }
	  
	    Display.destroy();
	}
	protected void input()
	{
		int x = 0;
		int y = 0;
		
		clickedFirstRun = true;
		
		if(Mouse.isButtonDown(0))
		{
			x = Mouse.getX() - (Display.getWidth() / 2);
			y = Mouse.getY() - (Display.getHeight() / 2);
			
			
			this.position.x = x + previousPosition.x;
			this.position.y = y + previousPosition.y;
			
			afterClickFirstRun = true;
			clickedFirstRun = false;
		}
		if(clickedFirstRun && afterClickFirstRun )
		{
			previousPosition = new Point2D.Float(position.x, position.y);
			afterClickFirstRun = false;
			System.out.println("GLView position : " + position.x + " " + position.y);
		}
		
		
	}
	
}
	