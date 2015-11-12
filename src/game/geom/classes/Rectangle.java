package game.geom.classes;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends java.awt.geom.Rectangle2D.Float{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<PointF> points;
	protected PointF center;
	protected float area;
	public Rectangle() 
	{
		super();
		
		
		points = new ArrayList<>();
	}
	
	public Rectangle(float height, float width, float x, float y)
	{
		//Call super constructor
		super(height, width, x, y);
		
		//Calculate the area
		area = height * width;
		
		points = new ArrayList<>();
		
		//Calculate rectangle points
		points.add(new PointF(x + (height/2),y - (width/2)));
		points.add(new PointF(x + (height/2),y + (width/2)));
		points.add(new PointF(x - (height/2),y + (width/2)));
		points.add(new PointF(x - (height/2),y - (width/2)));
		
		//Set the center
		center = new PointF(x, y);
	}
	
	public void setLocation(PointF point)
	{
		//Calls the simplified function
		this.setLocation(point.x , point.y);
	}
	public void setLocation(double x, double y) {
		//Calls the simplified function
		this.setLocation((float)x , (float)y);
	}
	public void setLocation(float x, float y)
	{
		//Modify the center location
		center.setLocation(x,y);
		
		//Calculate the points according
		//to the new center
		//TO REDO!
		points.get(0).setLocation(center.x + (height/2),center.y - (width/2));
		points.get(1).setLocation(center.x + (height/2),center.y + (width/2));
		points.get(2).setLocation(center.x - (height/2),center.y + (width/2));
		points.get(3).setLocation(center.x - (height/2),center.y - (width/2));
	}
	
	public PointF getCenter()
	{
		return center;
	}
	public List<PointF> getPoints()
	{
		return points;
	}
	public float getArea()
	{
		return height * width;
	}

	

}
