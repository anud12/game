package game.experimental;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import game.geom.classes.PointF;
import game.geom.classes.Triangle;
import game.library.Entity;
import game.library.attribute.AttributeSelector;
import game.library.world.IWorld;
import game.library.world.sector.Sector;
import game.library.world.sector.cell.TriangleCell;

public class GLView implements Runnable{
	
	protected IWorld world;
	protected Point2D.Float position;
	
	protected List<Sector> sectors;
	
	boolean clickedFirstRun = true;
	boolean afterClickFirstRun = true;
	boolean keyPlusClicked = false;
	boolean plusAfterClick = true;
	
	int holdPointX = 0;
	int holdPointY = 0;
	
	boolean keyMinusClicked = false;
	boolean minusAfterClick = true;
	float zoom = 1;
	
	Point2D.Float previousPosition;
	private boolean untilClickFirstRun = true;
	
	public GLView(IWorld world, List<Sector> grid)
	{
		position = new Point2D.Float(0, 0);
		this.world = world;
		this.sectors = grid;
		
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
	        synchronized(sectors)
	        {
	        	Iterator<Sector> sectorsListIterator = sectors.iterator();
		        
		        while(sectorsListIterator.hasNext())
		        {
		        	Sector sector = sectorsListIterator.next();
		        	Iterator<TriangleCell> sectorTriangles = sector.getList().iterator();
			        while(sectorTriangles.hasNext())
			        {
			        	Triangle triangle = sectorTriangles.next();
			        	Iterator<PointF> points = triangle.getPoints().iterator();
			        	
			        	Color color = sector.getColor();
			        	
			        	float red = color.getRed() / 255f;
			        	float blue = color.getBlue() / 255f;
			        	float green = color.getGreen() / 255f;
			        	
			        	red = red / 4f;
			        	green = green / 4f;
			        	blue = blue / 4f;
			        	
			        	GL11.glColor3d(red, green, blue);
			        	
			        	//GL11.glBegin(GL11.GL_TRIANGLES);
			        	GL11.glBegin(GL11.GL_LINE_LOOP);
			        	while(points.hasNext())
			        	{
			        		PointF point = points.next();
			        		
			        		GL11.glVertex2f(
				        			(point.x + position.x) * zoom ,
				        			(point.y + position.y) * zoom);
			        		
			        	}
			        	GL11.glEnd();
			        }
		        }
	        }
	        
	        
	        Iterator<Entity> iterator = world.getIterator();
	        while(iterator.hasNext())
	        {
	        	
	        	Entity ent = iterator.next();
	        	float red = ((Color) ent.get(AttributeSelector.color())).getRed() / 255f;
	        	float blue = ((Color) ent.get(AttributeSelector.color())).getBlue() / 255f;
	        	float green = ((Color) ent.get(AttributeSelector.color())).getGreen() / 255f;
	        	
	        	red = red / 0.5f;
	        	green = green / 0.5f;
	        	blue = blue / 0.5f;
	        	
	        	GL11.glColor3d(red, green, blue);
	        	
	        	GL11.glBegin(GL11.GL_POINTS);
        		GL11.glVertex2f((ent.getCenter().x  + position.x) * zoom, (ent.getCenter().y  + position.y)  * zoom);
        		GL11.glEnd();
	        	
	        	//GL11.glBegin(GL11.GL_QUADS);
	        	GL11.glBegin(GL11.GL_LINE_LOOP);
	        	Iterator<PointF> points = ent.getRectangle().getPoints().iterator();
	        	
	        	while(points.hasNext())
	        	{
	        		PointF point = points.next();
	        		GL11.glVertex2f(
		        			(point.x + position.x) * zoom ,
		        			(point.y + position.y) * zoom);
	        		
	        	}
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
		
		if(Mouse.isButtonDown(0) && untilClickFirstRun)
		{
			untilClickFirstRun = false;
			holdPointX = (int) (Mouse.getX() - position.x);
			holdPointY = (int) (Mouse.getY() - position.y);
		}
		
		if(Mouse.isButtonDown(0))
		{
			x = (int) (Mouse.getX() - (holdPointX) );
			y = (int) (Mouse.getY() - (holdPointY) );
			
			this.position.x = x;
			this.position.y = y;
			
			afterClickFirstRun = true;
			clickedFirstRun = false;
		}
		if(clickedFirstRun && afterClickFirstRun )
		{
			previousPosition = new Point2D.Float(position.x, position.y);
			afterClickFirstRun = false;
			untilClickFirstRun = true;
			
			
			
			System.out.println("GLView position : " + position.x + " " + position.y);
		}
		
		plusAfterClick = true;
		if(Keyboard.isKeyDown(Keyboard.KEY_ADD))
		{
			this.plusAfterClick = false;
			keyPlusClicked = true;
		}
		if(plusAfterClick && keyPlusClicked)
		{
			System.out.println("++" + zoom);
			
			keyPlusClicked = false;
			zoom = zoom *  2;
			
		}
		
		minusAfterClick = true;
		if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT))
		{
			this.minusAfterClick = false;
			keyMinusClicked = true;
		}
		if(minusAfterClick && keyMinusClicked)
		{
			System.out.println("++" + zoom);
			
			keyMinusClicked = false;
			zoom = zoom / 2.0f;
		}
		
	}
	
}
	