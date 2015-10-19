package game.library.world;

import java.util.ArrayList;
import java.util.List;

import game.geom.classes.PointF;
import game.geom.classes.Rectangle;
import game.geom.classes.Triangle;

public class SquareCell extends Rectangle
{
	protected List<TriangleCell> innerTriangles;
	public SquareCell(float height, float x, float y)
	{
		super(height, height, x, y);
	}
	
	public List<TriangleCell> getInnerTrianglesCell()
	{
		if(innerTriangles == null)
			calculateInnerTriangles();
		
		return innerTriangles;
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
		
		//Try used if there are less than
		//3 points in trianglePoints list
		try 
		{
			//Add a new triangle with the points in the list
			innerTriangles.add(new TriangleCell(trianglePoints, this));
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
			//Add a new triangle with the points in the list
			innerTriangles.add(new TriangleCell(trianglePoints, this));
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
			//Add a new triangle with the points in the list
			innerTriangles.add(new TriangleCell(trianglePoints, this));
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
			//Add a new triangle with the points in the list
			innerTriangles.add(new TriangleCell(trianglePoints, this));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
