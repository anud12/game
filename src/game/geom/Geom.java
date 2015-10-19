package game.geom;

import java.util.Iterator;
import java.util.List;

import game.geom.classes.PointF;

public class Geom {
	public static PointF getCentroid(List<PointF>points)
	{
		PointF returnVal = new PointF();
		
		Iterator<PointF> iterator = points.iterator();
		
		float x = 0;
		float y = 0;
		
		while(iterator.hasNext())
		{
			PointF point = iterator.next();
			x += point.x;
			y += point.y;
		}
		returnVal.x = x/points.size();
		returnVal.y = y/points.size();
		
		return returnVal;
	}
}
