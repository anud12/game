package game.library.world;

import java.util.List;

import game.geom.classes.Rectangle;

public class SquareCell extends Rectangle
{
	protected List<SquareCell> adiacentTriangles;
	
	public SquareCell(float height, float x, float y) throws Exception
	{
		super(height, height, x, y);
	}
}
