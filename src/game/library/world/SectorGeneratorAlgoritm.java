package game.library.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import game.geom.classes.PointF;
import game.geom.classes.PointI;
import game.geom.classes.Triangle;
import javafx.scene.control.Cell;

public class SectorGeneratorAlgoritm 
{
	int maxDepth;
	float maxArea;
	List<PointF> nodes;
	
	public Sector generate(SectorGrid grid)
	{
		this.maxArea = maxArea;
		
		float area = 0;
		
		Sector sector = new Sector();
		List<TriangleCell> triangles = new ArrayList();
		
		SquareCell startSquare = null;
		
		PointI point = null;
		PointI point2 = null;
		PointI point3 = null;
		PointI point4 = null;
		PointI point5 = null;
		
		LinkedHashSet<SquareCell> squareList = new LinkedHashSet<SquareCell>();
		boolean loop = true;
		while(loop)
		{
			loop = false;
			startSquare = grid.getEmptySquare(squareList);
			
			squareList.add(startSquare);
			point = grid.getGridCoordinate(startSquare);
			
			if(!grid.isSquareFree(grid.getSquareByGrid(point)))
			{
				loop = true;
			}
			
			point2 = new PointI();
			
			point2.x = point.x + 1;
			point2.y = point.y;
			if(!grid.isSquareFree(grid.getSquareByGrid(point2)))
			{
				loop = true;
			}
			
			point3 = new PointI();
			point3.y = point.y + 1;
			point3.x = point.x;
			
			if(!grid.isSquareFree(grid.getSquareByGrid(point3)))
			{
				loop = true;
			}
			point4 = new PointI();
			point4.y = point.y + 2;
			point4.x = point.x;
			
			if(!grid.isSquareFree(grid.getSquareByGrid(point4)))
			{
				loop = true;
			}
			
			point5 = new PointI();
			point5.y = point.y + 1;
			point5.x = point.x + 1;
			
			if(!grid.isSquareFree(grid.getSquareByGrid(point5)))
			{
				loop = true;
			}
		}
		
		sector.addTriangle(grid.getSquareByGrid(point).getRightTriangle());
		sector.addTriangle(grid.getSquareByGrid(point).getBottomTriangle());
		
		sector.addTriangle(grid.getSquareByGrid(point2).getLeftTriangle());
		sector.addTriangle(grid.getSquareByGrid(point2).getBottomTriangle());
		
		sector.addTriangle(grid.getSquareByGrid(point3).getInnerTrianglesCell());
		sector.addTriangle(grid.getSquareByGrid(point4).getInnerTrianglesCell());
		
		sector.addTriangle(grid.getSquareByGrid(point5).getTopTriangle());
		sector.addTriangle(grid.getSquareByGrid(point5).getLeftTriangle());
		
		grid.occupyTriangle(sector.getList());
		
		return sector;
	}
}
