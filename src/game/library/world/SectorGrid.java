package game.library.world;

import java.util.HashMap;
import java.util.Set;

import game.geom.classes.Point;

public class SectorGrid {
	
	HashMap<Point, SquareCell> rectangleList;
	
	public SectorGrid(int rows, int collums, int height)
	{
		rectangleList = new HashMap<Point, SquareCell>();
		
		for(int i = 0; i < rows ;i++)
		{
			for(int j = 0 ; j < collums ; j++)
			{
				try {
					SquareCell cell = new SquareCell(height, i * height, j * height);
					rectangleList.put(cell.getCenter(), cell);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	public SquareCell getCell(Point point) throws Exception
	{
		return rectangleList.get(point);
	}
	
	public Set<Point> getList()
	{
		return rectangleList.keySet();
	}
}
