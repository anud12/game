package game.library.world.sector.cell;

import java.util.List;

import game.geom.classes.PointF;
import game.geom.classes.Triangle;

public class TriangleCell extends Triangle implements Cell
{

	protected SquareCell parent;
	
	public TriangleCell(List<PointF> points, SquareCell parent) throws Exception 
	{
		super(points);
		this.parent = parent;
	}
	
	public SquareCell getParent()
	{
		return parent;
	}

}
