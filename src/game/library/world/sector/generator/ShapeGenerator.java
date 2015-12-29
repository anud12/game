package game.library.world.sector.generator;

import java.util.List;

import game.geom.classes.PointF;
import game.library.world.sector.Sector;
import game.library.world.sector.SectorGrid;

public class ShapeGenerator implements SectorGenerator
{
	protected List<PointF> points;
	
	public ShapeGenerator(List<PointF> points)
	{
		this.points = points;
	}
	@Override
	public Sector generateFromCenter(SectorGrid grid)
	{
		
		return null;
	}

	@Override
	public Sector generateAtPoint(SectorGrid grid, PointF point)
	{
		try
		{
			throw new Exception("Not implemented");
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
}
