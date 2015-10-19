package game.geom.classes;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.geom.Geom;

public class Triangle extends Rectangle2D.Float
{
	protected List<PointF> points;
	protected ArrayList<Line2D.Float> vertices;
	protected PointF center;
	protected float area;
	public Triangle(List<PointF> points) throws Exception
	{
		//Check if there are more than 3 points
		if(points.size() > 3)
			throw new Exception("Number of points over 3, got" + points.size());
		
		this.points = points;
		
		
		
		//Initialize the list for the vertices
		vertices = new ArrayList<Line2D.Float>();
		
		//Lines declaration
		vertices.add(new Line2D.Float(this.points.get(0), this.points.get(1)));
		vertices.add(new Line2D.Float(this.points.get(1), this.points.get(2)));
		vertices.add(new Line2D.Float(this.points.get(2), this.points.get(0)));	
		
		//Calculate the center
		center = Geom.getCentroid(points);
		
		//Calculate the area
		 float s = 0;
		 //Calculate the length of the vertices
		 float a = (float) points.get(0).distance(points.get(1));
		 float b = (float) points.get(1).distance(points.get(2));
		 float c = (float) points.get(2).distance(points.get(0));
		 
		 s = a + b + c;		 
		 s = s / 2;
		 
		 area = (float) Math.sqrt(s * (s - a) * (s - b) * (s - c));
	}
	public void print()
	{
		Iterator iter = vertices.iterator();
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
	}
	public List<PointF> getPoints()
	{
		return points;
	}
	public PointF getCenter()
	{
		return center;
	}
	public float getArea()
	{
		return area;
	}
}
