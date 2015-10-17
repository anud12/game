package game.geom.classes;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Triangle extends Rectangle2D.Float
{
	protected List<Point> points;
	protected ArrayList<Line2D.Float> vertices;
	public Triangle(List<Point> points) throws Exception
	{
		if(points.size() > 3)
			throw new Exception("Number of points over 3, got" + points.size());
		
		this.points = points;
		
		
		vertices = new ArrayList<Line2D.Float>();
		
		//Lines initialization
		vertices.add(new Line2D.Float(this.points.get(0), this.points.get(1)));
		vertices.add(new Line2D.Float(this.points.get(1), this.points.get(2)));
		vertices.add(new Line2D.Float(this.points.get(2), this.points.get(0)));	
		
		
	}
	public void print()
	{
		Iterator iter = vertices.iterator();
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
	}
	public List<Point> getPoints()
	{
		return points;
	}
}
