package game.library.world.sector;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import game.geom.classes.PointF;
import game.geom.classes.RightTriangle;
import game.library.world.sector.cell.TriangleCell;

public class Sector 
{
	protected ArrayList<RightTriangle> triangleList;
	protected Color color;
	
	public Sector()
	{
		this.triangleList = new ArrayList<RightTriangle>();
		
		Random random = new Random();
		
		color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
		
	}
	public Sector(Color color)
	{
		this.triangleList = new ArrayList<RightTriangle>();
		
		this.color = color;
		
	}
	
	public RightTriangle mergeTriangle(RightTriangle triangle1, RightTriangle triangle2)
	{
		//System.out.println("\t\tSTART CHECK");
		
		//System.out.println("\t\tTRIANGLE 1 :");
		//triangle1.print();
		//System.out.println("\t\tTRIANGLE 2 :");
		//triangle2.print();
		RightTriangle returnTriangle = null;
		
		Iterator<PointF> points1 = triangle1.getPoints().iterator();
		
		List<PointF> linePoints = new ArrayList<PointF>();
		PointF lonePoint1 = null;
		
		Iterator<PointF> points2 = triangle2.getPoints().iterator();
		PointF lonePoint2 = null;
		
		PointF symetricalPoint = null;
		PointF rightAnglePoint = null;
		
		boolean found = false;
		while(points1.hasNext())
		{
			found = false;
			PointF testPoint1 = points1.next();
			
			points2 = triangle2.getPoints().iterator();
			System.out.println("\t\tTESTING :" + testPoint1 );
			while(points2.hasNext())
			{
				PointF testPoint2 = points2.next();
				System.out.println(testPoint2 + " : " + testPoint2.equals(testPoint1));
				if(testPoint2.equals(testPoint1))
				{
					linePoints.add(testPoint1);
					found = true;
					break;
				}
			}
			if(!found)
			{
				lonePoint1 = testPoint1;
			}
		}
		if(linePoints.size() != 2)
		{
			//System.out.println("LINEPOINTS NOT EQUAL 2 - RETURNING");
			return null;
		}
		int lonePointTest = 0;
		
		points2 = triangle2.getPoints().iterator();
		while(points2.hasNext())
		{
			PointF point = points2.next();
			if(!linePoints.contains(point))
			{
				lonePoint2 = point;
			}
		}
		
		//System.out.println("\t\tLONEPOINT1");
		//System.out.println(lonePoint1);
		
		//System.out.println("\t\tLONEPOINT2");
		//System.out.println(lonePoint2);
		
		Iterator<PointF> linePointIterator= linePoints.iterator();
		while(linePointIterator.hasNext())
		{
			PointF linePoint = linePointIterator.next();
			//System.out.println("\nLINE POINT :" + linePoint);
			float x = linePoint.x;
			float y = linePoint.y;
			
			if(linePoint.x < lonePoint1.x)
			{
				x = x + (linePoint.x - lonePoint1.x);
			}
			else
			{
				x = x - (lonePoint1.x - linePoint.x);
			}
			
			if(linePoint.y < lonePoint1.y)
			{
				y = y + (linePoint.y - lonePoint1.y);
			}
			else
			{
				y = y - (lonePoint1.y - linePoint.y);
			}
			
			lonePointTest++;
			
			symetricalPoint = new PointF(x, y);
			//System.out.println("SYMETRICAL POINT :");
			//System.out.println(symetricalPoint);
			
			if(symetricalPoint.equals(lonePoint2))
			{
				//System.out.println("FOUND LONEPOINT2 :");
				//System.out.println(lonePoint2);
				found = true;
			}
			else
			{
				rightAnglePoint = linePoint;
				//System.out.println("RIGHT ANGLE POINT :");
				//System.out.println(rightAnglePoint);
				//System.out.println();
			}
			if(lonePointTest > 2)
			{
				//System.out.println("INVALID");
				return null;
			}
		}
		
		if(found)
		{
			LinkedList<PointF> returnTrianglePoints = new LinkedList<PointF>();
			
			//System.out.println(rightAnglePoint);
			//System.out.println(symetricalPoint);
			//System.out.println(lonePoint1);
			
			returnTrianglePoints.add(new PointF(rightAnglePoint.x, rightAnglePoint.y));
			returnTrianglePoints.add(new PointF(lonePoint1.x, lonePoint1.y));
			returnTrianglePoints.add(new PointF(symetricalPoint.x, symetricalPoint.y));
			try
			{
				returnTriangle = new RightTriangle(returnTrianglePoints);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return returnTriangle;
	}
	public List<RightTriangle> getList()
	{
		return triangleList;
	}
	public void addTriangle(RightTriangle triangle)
	{		
		RightTriangle triangleFromList = null;
		RightTriangle mergedTriangle = null;
		
		Iterator<RightTriangle> iterator = triangleList.iterator();
		
		while(iterator.hasNext())
		{
			triangleFromList = iterator.next();
			
			mergedTriangle = triangle.combine(triangleFromList);
			if(mergedTriangle != null)
			{
				triangleList.remove(triangleList.indexOf(triangleFromList)).print();
				triangle = mergedTriangle;
				
				addTriangle(mergedTriangle);
				
				return;
			}
		}
		
		triangleList.add(triangle);
		System.out.println(triangleList.size());
		
	}
	public void addTriangle(List<RightTriangle> list)
	{
		Iterator<RightTriangle> iterator = list.iterator();
		while(iterator.hasNext())
		{
			addTriangle(iterator.next());
		}
	}
	public void addTriangle(Collection<TriangleCell> list)
	{
		Iterator<TriangleCell> iterator = list.iterator();
		while(iterator.hasNext())
		{
			addTriangle(iterator.next());
		}
	}
	public Color getColor()
	{
		return color;
	}
	
}
