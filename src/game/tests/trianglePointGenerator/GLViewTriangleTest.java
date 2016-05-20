package game.tests.trianglePointGenerator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import game.experimental.GLView;
import game.geom.classes.PointF;
import game.geom.classes.RightTriangle;
import game.library.world.IWorld;
import game.library.world.sector.Sector;

public class GLViewTriangleTest extends GLView
{
	
	protected RightTriangle rightTriangle;

	protected LinkedList<PointF> points;
	protected LinkedList<PointF> pointsInside;
	protected LinkedList<PointF> pointsOutside; 
	
	public GLViewTriangleTest(IWorld world, List<Sector> grid)
	{
		super(world, grid);
		// TODO Auto-generated constructor stub
		pointsInside = new LinkedList<>();
		pointsOutside = new LinkedList<>();
		points = new LinkedList<>();
	}
	
	public void setRightTriangle (RightTriangle rightTriangle) 
	{
		this.rightTriangle = rightTriangle;
	}
	
	public void addPoint(PointF point)
	{
		synchronized(points)
		{
			points.add(point);			
		}
	}
	
	public void addPointCheck(PointF point)
	{
		if(rightTriangle.isInsideTriangle(point))
		{
			synchronized(pointsInside)
			{
				pointsInside.add(point);
			}
		}
		
		else
		{
			synchronized (pointsOutside)
			{
				pointsOutside.add(point);
			}
		}
		
	}
	
	protected void draw()
	{
		//GL11.glPointSize(zoom);
    	GL11.glColor3d(0.5, 1, 0.5);
    	GL11.glBegin(GL11.GL_POINTS);
    	
		synchronized(pointsInside)
		{
			Iterator<PointF> pointIterator = pointsInside.iterator();
			
			while(pointIterator.hasNext())
			{
				PointF point = pointIterator.next();
				
				GL11.glVertex2f(
	        			(point.x + position.x) * zoom ,
	        			(point.y + position.y) * zoom);
			}
		}
		GL11.glEnd();
		
		GL11.glColor3d(1, 0.5, 0.5);
    	GL11.glBegin(GL11.GL_POINTS);
    	
		synchronized(pointsOutside)
		{
			Iterator<PointF> pointIterator = pointsOutside.iterator();
			
			while(pointIterator.hasNext())
			{
				PointF point = pointIterator.next();
				
				GL11.glVertex2f(
	        			(point.x + position.x) * zoom ,
	        			(point.y + position.y) * zoom);
			}
		}
		GL11.glEnd();
		
		
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
		GL11.glColor3d(0.2, 0.2, 0.2);
		Iterator<PointF> pointIterator = rightTriangle.getPoints().iterator();
		
		while(pointIterator.hasNext())
		{
			PointF point = pointIterator.next();
			
			GL11.glVertex2f(
        			(point.x + position.x) * zoom ,
        			(point.y + position.y) * zoom);
		}
		GL11.glEnd();
		
		GL11.glColor3d(0.5, 0.5, 1);
    	GL11.glBegin(GL11.GL_POINTS);
    	
		synchronized(points)
		{
			pointIterator = points.iterator();
			
			while(pointIterator.hasNext())
			{
				PointF point = pointIterator.next();
				
				GL11.glVertex2f(
	        			(point.x + position.x) * zoom ,
	        			(point.y + position.y) * zoom);
			}
		}
		GL11.glEnd();
	}
	
}
