package game.geom.classes;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import game.geom.Geom;

public class RightTriangle
{
	protected List<PointF> points;
	protected ArrayList<Line2D.Float> vertices;
	protected PointF center;
	protected float area;
	
	public static RightTriangle combine(RightTriangle triangle1, RightTriangle triangle2)
	{
		return triangle1.combine(triangle2);
	}
	
	public RightTriangle(List<PointF> points) throws Exception
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
		
		area = calculateArea(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y, points.get(2).x, points.get(2).y);
	}
	
	
	public void print()
	{
		Iterator<PointF> iter = points.iterator();
		while(iter.hasNext())
		{
			@SuppressWarnings("unused")
			Object obj = iter.next();
			//System.out.println(obj);
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
	
	protected PointF calculateSimetry(PointF point, PointF linePoint)
	{
		PointF returnValue = null;
		
		float x = 0;
		float y = 0;
		
		if(linePoint.x > point.x)
		{
			x = linePoint.x + linePoint.x - point.x;
		}
		else
		{
			x = - (point.x - linePoint.x) + linePoint.x ;
		}
		
		if(linePoint.y > point.y)
		{
			y = linePoint.y + linePoint.y - point.y;
		}
		else
		{
			y = - (point.y - linePoint.y) + linePoint.y ;
		}
		
		returnValue = new PointF(x, y);
		
		return returnValue;
	}
	
	public PointF getRandomPoint()
	{
		PointF returnPoint = new PointF();
		
		Random random = new Random();
		
		float r1 = random.nextFloat();
		float r2 = random.nextFloat();
		
		//Source http://stackoverflow.com/questions/19654251/random-point-inside-triangle-inside-java
		returnPoint.x = (float) ((1 - Math.sqrt(r1)) * points.get(0).x + (Math.sqrt(r1) * (1 - r2)) * points.get(1).x + (Math.sqrt(r1) * r2) * points.get(2).x);
		returnPoint.y = (float) ((1 - Math.sqrt(r1)) * points.get(0).y + (Math.sqrt(r1) * (1 - r2)) * points.get(1).y + (Math.sqrt(r1) * r2) * points.get(2).y);
		
		return returnPoint;
	}
	protected float calculateArea(float x, float y, float x2, float y2, float x3, float x4)
	{
		return (float) Math.abs((x*(y2-x4) + x2*(x4-y)+ x3*(y-y2))/2.0);
	}
	
	private float sign(float n)
	{
		return Math.abs(n)/n;
	}
	
	public boolean isInsideTriangle(PointF P)
	{
		PointF A = points.get(0);
		PointF B = points.get(1);
		PointF C = points.get(2);
		
		float planeAB = (A.x-P.x)*(B.y-P.y)-(B.x-P.x)*(A.y-P.y);
		float planeBC = (B.x-P.x)*(C.y-P.y)-(C.x - P.x)*(B.y-P.y);
		float planeCA = (C.x-P.x)*(A.y-P.y)-(A.x - P.x)*(C.y-P.y);
		
		return sign(planeAB)==sign(planeBC) && sign(planeBC)==sign(planeCA);
		
	}
	public RightTriangle combine(RightTriangle triangle)
	{
		RightTriangle returnTriangle = null;
		
		LinkedList<PointF> commonPoints = new LinkedList<PointF>();
				
		PointF loneA = null;
		PointF loneB = null;
		
		Iterator<PointF> pointsA = points.iterator();
		Iterator<PointF> pointsB;
		
		//Find the common points between the triangles
		while(pointsA.hasNext())
		{
			PointF pointA = pointsA.next();
			
			//System.out.println("\tTesting");
			//System.out.println(pointA);
			
			pointsB = triangle.getPoints().iterator();
			
			boolean found = false;
			
			PointF pointB = null;
			
			while(pointsB.hasNext())
			{
				pointB = pointsB.next();
				
				//System.out.println(pointB + " : " + pointB.equals(pointA));
				
				if(pointB.equals(pointA))
				{
					commonPoints.add(pointA);
					found = true;
					
				}
			}
			//If the point is not common is a lone point in A
			if(!found)
			{
				loneA = pointA;
			}
		}
		
		//Find the lone point in B which is not a common point
		pointsB = triangle.getPoints().iterator();
		while(pointsB.hasNext())
		{
			PointF pointB = pointsB.next();
			if(!commonPoints.contains(pointB))
			{
				loneB = pointB;
				break;
			}
		}
		
		//Return if there are more than 2 linePoints
		if(commonPoints.size() != 2)
		{
			return returnTriangle;
		}
		
		
		if(calculateSimetry(loneA, commonPoints.get(0)).equals(loneB))
		{
			List<PointF> points = new LinkedList<PointF>();
			points.add(loneA);
			points.add(loneB);
			points.add(commonPoints.get(1));
			
			try
			{
				returnTriangle = new RightTriangle(points);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			return returnTriangle;
		}
		else
		if(calculateSimetry(loneA, commonPoints.get(1)).equals(loneB))
		{
			List<PointF> points = new LinkedList<PointF>();
			points.add(loneA);
			points.add(loneB);
			points.add(commonPoints.get(0));
			
			try
			{
				returnTriangle = new RightTriangle(points);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return returnTriangle;
		}
		
		return returnTriangle;
	}
}
