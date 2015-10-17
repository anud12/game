package game.geom.classes;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Rectangle extends java.awt.geom.Rectangle2D.Float{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<Triangle> InnerTriangles;
	protected List<Point> points;
	protected Point center;
	public Rectangle() 
	{
		super();
		
		InnerTriangles = new ArrayList<>();
		
		points = new ArrayList<>();
	}
	
	public Rectangle(float height, float width, float x, float y) throws Exception
	{
		//Call super constructor
		super(height, width, x, y);
		
		//Initialize lists
		InnerTriangles = new ArrayList();
		points = new ArrayList<>();
		
		//Calculate rectangle points
		points.add(new Point(x + (height/2),y - (width/2)));
		points.add(new Point(x + (height/2),y + (width/2)));
		points.add(new Point(x - (height/2),y + (width/2)));
		points.add(new Point(x - (height/2),y - (width/2)));
		
		//Set the center
		center = new Point(x, y);
		
		//Initialize the array used to fill the rectangle
		ArrayList<Point> trianglePoints = new ArrayList<>();
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(0));
		trianglePoints.add(points.get(1));
		trianglePoints.add(center);
		
		//Add a new triangle with the points in the list
		InnerTriangles.add(new Triangle(trianglePoints));
		//Clear the list
		trianglePoints = new ArrayList<>();
		
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(1));
		trianglePoints.add(points.get(2));
		trianglePoints.add(center);
		
		//Add a new triangle with the points in the list
		InnerTriangles.add(new Triangle(trianglePoints));
		//Clear the list
		trianglePoints = new ArrayList<>();
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(2));
		trianglePoints.add(points.get(3));
		trianglePoints.add(center);
		
		//Add a new triangle with the points in the list
		InnerTriangles.add(new Triangle(trianglePoints));
		//Clear the list
		trianglePoints = new ArrayList<>();
		
		//Creating a triangle//
		//Add the points to the list
		trianglePoints.add(points.get(3));
		trianglePoints.add(points.get(0));
		trianglePoints.add(center);
		
		//Add a new triangle with the points in the list
		InnerTriangles.add(new Triangle(trianglePoints));
		
	}
	
	public void setLocation(Point2D.Float point)
	{
		this.setLocation(point.x , point.y);
	}
	public void setLocation(float x, float y)
	{
		center.setLocation(x,y);
		points.get(0).setLocation(center.x + (height/2),center.y - (width/2));
		points.get(1).setLocation(center.x + (height/2),center.y + (width/2));
		points.get(2).setLocation(center.x - (height/2),center.y + (width/2));
		points.get(3).setLocation(center.x - (height/2),center.y - (width/2));
	}
	
	public List<Triangle> getInnerTriangles()
	{	
		return InnerTriangles;
	}
	public Point getCenter()
	{
		return center;
	}
	public List<Point> getPoints()
	{
		return points;
	}

	public void setLocation(double x, double y) {
		this.setLocation((float)x , (float)y);
		
	}

}
