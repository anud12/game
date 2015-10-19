package game.library.world;

import java.util.List;

import game.geom.classes.PointF;
import game.geom.classes.Triangle;

public class SectorGeneratorAlgoritm 
{
	int maxDepth;
	float maxArea;
	
	public Sector generate(SectorGrid grid, PointF point)
	{
		int x = (int) point.x;
		int y = (int) point.y;
		
		
		Sector sector = new Sector();
		List<TriangleCell> triangles;
		SquareCell square = grid.getSquareByGrid(x, y);
		//grid.occupySquare(square);
		triangles = square.getInnerTrianglesCell();
		
		sector.addTriangle(triangles);
		grid.occupyTriangle(triangles);
		
		square = grid.getSquareByGrid(x - 1, y);
		
		if(grid.isSquareFree(square))
		{
			triangles = square.getInnerTrianglesCell();
			sector.addTriangle(triangles.get(0));
			grid.occupyTriangle(triangles.get(0));
		}
		
		square = grid.getSquareByGrid(x, y - 1);
		
		if(grid.isSquareFree(square))
		{
			triangles = square.getInnerTrianglesCell();
			sector.addTriangle(triangles.get(1));
			grid.occupyTriangle(triangles.get(1));
		}
		return sector;
	}
	
	public Sector generate2(SectorGrid grid, TriangleCell cell)
	{
		Sector sector = new Sector();
		sector.addTriangle(cell);
		grid.occupyTriangle(cell);
		return sector;
	}

}
