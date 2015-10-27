package game.library.world.sector.cell;

import java.util.ArrayList;
import java.util.List;

import game.geom.classes.PointF;
import game.geom.classes.Rectangle;
import game.geom.classes.Triangle;

public class SquareCell extends Rectangle implements Cell
{
	protected List<TriangleCell> innerTriangles;
	
	protected TriangleCell rightTriangle;
	protected TriangleCell leftTriangle;
	protected TriangleCell topTriangle;
	protected TriangleCell bottomTriangle;
	
	
	public SquareCell(float height, float x, float y)
	{
		super(height, height, x, y);
		calculateInnerTriangles();
	}
	
	public List<TriangleCell> getInnerTrianglesCell()
	{		
		return innerTriangles;
	}
	
	public TriangleCell getTopTriangle()
	{
		return topTriangle;
	}
	public TriangleCell getBottomTriangle()
	{
		return bottomTriangle;
	}
	public TriangleCell getLeftTriangle()
	{
		return leftTriangle;
	}
	public TriangleCell getRightTriangle()
	{
		return rightTriangle;
	}
	
	protected void calculateInnerTriangles()
	{
		innerTriangles = new ArrayList();
		//Initialize the array used to fill the rectangle
		ArrayList<PointF> trianglePoints = new ArrayList<>();
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(0));
		trianglePoints.add(points.get(1));
		trianglePoints.add(center);
		
		try 
		{
			rightTriangle = new TriangleCell(trianglePoints, this);
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			//Add a new triangle with the points in the list
			innerTriangles.add(rightTriangle);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		//Clear the list
		trianglePoints = new ArrayList<>();
		
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(1));
		trianglePoints.add(points.get(2));
		trianglePoints.add(center);
		
		//Add a new triangle with the points in the list
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			bottomTriangle = new TriangleCell(trianglePoints, this);
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			//Add a new triangle with the points in the list
			innerTriangles.add(bottomTriangle);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//Clear the list
		trianglePoints = new ArrayList<>();
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(2));
		trianglePoints.add(points.get(3));
		trianglePoints.add(center);
		
		//Add a new triangle with the points in the list
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			leftTriangle = new TriangleCell(trianglePoints, this);
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			//Add a new triangle with the points in the list
			innerTriangles.add(leftTriangle);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//Clear the list
		trianglePoints = new ArrayList<>();
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(3));
		trianglePoints.add(points.get(0));
		trianglePoints.add(center);
		
		//Add a new triangle with the points in the list
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			topTriangle = new TriangleCell(trianglePoints, this);
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			//Add a new triangle with the points in the list
			innerTriangles.add(topTriangle);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
