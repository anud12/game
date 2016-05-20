package game.tests.trianglePointGenerator;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.geom.classes.PointF;
import game.geom.classes.RightTriangle;

public class TrianglePointGeneratorTest 
{
	public static void main(String args[])
	{
		int lenght = 100;
		int width = 100;
		
		LinkedList<PointF> points = new LinkedList<>();
		
		points.add(new PointF(0,0));
		points.add(new PointF(0,width));
		points.add(new PointF(lenght,0));
		
		try
		{			
			GLViewTriangleTest test = new GLViewTriangleTest(null, null);
		
			RightTriangle triangle = new RightTriangle(points);
			
			test.setRightTriangle(triangle);
			
			PointF point;
			
			ExecutorService service = Executors.newCachedThreadPool();
			
			service.execute(test);
			float step = 1f;
			float x = 0;
			float y = 0;
			
			while(y <= width)
			{
				test.addPointCheck(new PointF(x,y));
				x+= step;
				
				if(x > width)
				{
					x = 0;
					y += step;
				}
				
				Thread.sleep(0,1);
			}
			
			while( y > -1)
			{
				test.addPointCheck(triangle.getRandomPoint());
				Thread.sleep(1);
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
