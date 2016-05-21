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
import game.geom.classes.RightTriangle;
import game.library.attribute.AttributeSelector;
import game.library.entity.Entity;
import game.library.world.IWorld;
import game.library.world.sector.Sector;

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
	protected float zoom = 1;
	
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
	        Display.setDisplayMode(new DisplayMode(800,400));
	        Display.create();
	    } catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
	    }
	  
	    // init OpenGL
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, 800, 0, 400, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glClearColor(0, 0, 0, 1f);
	    while (!Display.isCloseRequested()) 
	    {    
	    	clear();
	    	draw();
	        input();
	        Display.update();
	    }
	  
	    Display.destroy();
	}
	
	protected void clear()
	{
		// Clear the screen and depth buffer
    	
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);  
        
        // set the color of the quad (R,G,B,A)
	}
	
	protected void draw()
	{
		//drawSectors();
        drawEntity();
	}
	protected void drawEntity()
	{
		Iterator<Entity> iterator = world.getIterator();
        while(iterator.hasNext())
        {
        	
        	Entity ent = iterator.next();
        	float red = ((Color) ent.getData().get(AttributeSelector.color())).getRed() / 255f;
        	float blue = ((Color) ent.getData().get(AttributeSelector.color())).getBlue() / 255f;
        	float green = ((Color) ent.getData().get(AttributeSelector.color())).getGreen() / 255f;
        	
        	//red = red ;// 0.5f;
        	//green = green ;// 0.5f;
        	//blue = blue ;// 0.5f;
        	
        	Iterator<PointF> points = ent.getData().getRectangle().getPoints().iterator();
    		float red2 = red / 2.5f;
        	float green2 = green / 2.5f;
        	float blue2 = blue / 2.5f;
        	
        	GL11.glColor3d(red2, green2, blue2);
        	
        	GL11.glBegin(GL11.GL_QUADS);
        	//GL11.glBegin(GL11.GL_LINE_LOOP);
        	
        	while(points.hasNext())
        	{
        		PointF point = points.next();
        		
        		GL11.glVertex2f(
	        			(point.x + position.x) * zoom ,
	        			(point.y + position.y) * zoom);
        		
        	}
        	GL11.glEnd();
        	
        	GL11.glColor3d(red, green, blue);
        	
        	GL11.glBegin(GL11.GL_POINTS);
    		GL11.glVertex2f((ent.getData().getCenter().x  + position.x) * zoom, (ent.getData().getCenter().y  + position.y)  * zoom);
    		GL11.glEnd();
        	
        	//GL11.glBegin(GL11.GL_QUADS);
        	GL11.glBegin(GL11.GL_LINE_LOOP);
        	GL11.glColor3d(red, green, blue);
        	points = ent.getData().getRectangle().getPoints().iterator();
        	
        	while(points.hasNext())
        	{
        		PointF point = points.next();
        		GL11.glVertex2f(
	        			(point.x + position.x) * zoom ,
	        			(point.y + position.y) * zoom);
        		
        	}
        	GL11.glEnd();
        	
        	
        	GL11.glBegin(GL11.GL_LINE_LOOP);
        	float radius = ent.getSightRadius();
        	
        	float twicePi = (float)Math.PI;
        	
        	int lines = (int)radius;
    		for(int i = 0; i <= lines;i++) 
    		{ 
    			GL11.glVertex2f(
    			    (((ent.getData().getCenter().x + position.x) * zoom) + (float)(radius * zoom * Math.cos(i * twicePi / (lines / 2)) )), 
    			    (((ent.getData().getCenter().y + position.y) * zoom) + (float)(radius * zoom * Math.sin(i * twicePi / (lines / 2)) ))
    			);
    		}
    		GL11.glEnd();
    		
        }
	}
	protected void drawSectors()
	{
		synchronized(sectors)
        {
        	Iterator<Sector> sectorsListIterator = sectors.iterator();
	        
	        while(sectorsListIterator.hasNext())
	        {
	        	Sector sector = sectorsListIterator.next();
	        	List<RightTriangle> triangleList = sector.getList();
	        	synchronized(triangleList)
	        	{
	        		Iterator<RightTriangle> sectorTriangles = triangleList.iterator();
			        while(sectorTriangles.hasNext())
			        {
			        	RightTriangle triangle = sectorTriangles.next();
			        	Iterator<PointF> points = triangle.getPoints().iterator();
			        	
			        	Color color = sector.getColor();
			        	
			        	float red = color.getRed() / 255f;
			        	float blue = color.getBlue() / 255f;
			        	float green = color.getGreen() / 255f;
			        	
			        	
			        	float red2 = red / 8f;
			        	float green2 = green / 8f;
			        	float blue2 = blue / 8f;
			        	
			        	GL11.glColor3d(red2, green2, blue2);
			        	
			        	GL11.glBegin(GL11.GL_TRIANGLES);
			        	//GL11.glBegin(GL11.GL_LINE_LOOP);
			        	
			        	while(points.hasNext())
			        	{
			        		PointF point = points.next();
			        		
			        		GL11.glVertex2f(
				        			(point.x + position.x) * zoom ,
				        			(point.y + position.y) * zoom);
			        		
			        	}
			        	GL11.glEnd();
			        	
			        	points = triangle.getPoints().iterator();
			        	float red1 = red2 * 8f;
			        	float green1 = green2 * 8f;
			        	float blue1 = blue2 * 8f;
			        	
			        	GL11.glColor3d(red1, green1, blue1);
			        	
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
        }
	}
	
	protected void input()
	{
		int x = 0;
		int y = 0;
		
		clickedFirstRun = true;
		
		if(Mouse.isButtonDown(0) && untilClickFirstRun)
		{
			untilClickFirstRun = false;
			holdPointX = (int) (Mouse.getX() - position.x * zoom);
			holdPointY = (int) (Mouse.getY() - position.y * zoom);
		}
		
		if(Mouse.isButtonDown(0))
		{
			x = (int) (Mouse.getX() - (holdPointX));
			y = (int) (Mouse.getY() - (holdPointY));
			
			this.position.x =  x / zoom;
			this.position.y =  y / zoom;
			
			afterClickFirstRun = true;
			clickedFirstRun = false;
		}
		if(clickedFirstRun && afterClickFirstRun )
		{
			previousPosition = new Point2D.Float(position.x, position.y);
			afterClickFirstRun = false;
			untilClickFirstRun = true;
			
			//System.out.println("GLView position : " + position.x + " " + position.y);
		}
		
		plusAfterClick = true;
		if(Keyboard.isKeyDown(Keyboard.KEY_ADD))
		{
			this.plusAfterClick = false;
			keyPlusClicked = true;
		}
		if(plusAfterClick && keyPlusClicked)
		{
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
			keyMinusClicked = false;
			zoom = zoom / 2.0f;
		}
		
	}
	
}
	