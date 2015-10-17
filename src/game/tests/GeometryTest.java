package game.tests;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

import game.geom.classes.Rectangle;
import game.geom.classes.Triangle;

public class GeometryTest 
{
	public static void main(String args[]) throws Exception
	{
		Rectangle rect = new Rectangle(10, 10, 0, 0);
		
		System.out.println(rect.getCenter());
		
		Iterator iter = rect.getPoints().iterator();
		
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
		
		iter = rect.getInnerTriangles().iterator();
		
		while(iter.hasNext())
		{
			((Triangle) iter.next()).print();
		}
		
	}
}
