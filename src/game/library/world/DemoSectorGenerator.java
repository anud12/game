package game.library.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import game.geom.classes.PointF;
import game.geom.classes.PointI;
import game.geom.classes.Triangle;
import javafx.scene.control.Cell;

public class DemoSectorGenerator 
{
	int maxDepth;
	float maxArea;
	List<PointF> nodes;
	
	public Sector generate(SectorGrid grid)
	{
		//Create sector with a random color or specified one
		Sector sector = new Sector(Color.orange);
		
		//State the usable variables
		SquareCell startSquare = null;
		
		PointI point = null;
		PointI point2 = null;
		PointI point3 = null;
		PointI point4 = null;
		PointI point5 = null;
		
		//Create set for already tested starting points for the region
		LinkedHashSet<SquareCell> squareList = new LinkedHashSet<SquareCell>();
		
		//Loop until the point returned has the correct free space
		boolean loop = true;
		while(loop)
		{
			loop = false;
			//Get an empty square from the grid,
			//except the ones in the squareList
			startSquare = grid.getEmptySquare(squareList);
			
			//Add the returned square in the list
			squareList.add(startSquare);
			
			//Get the grid coordinate of the square
			point = grid.getGridCoordinate(startSquare);
			
			//Check if the square is free
			if(!grid.isSquareFree(grid.getSquareByGrid(point)))
			{
				//Keep looping
				loop = true;
			}
			
			//Create a point to check at
			point2 = new PointI();
			
			point2.x = point.x + 1;
			point2.y = point.y;
			
			//Check if the square is free
			if(!grid.isSquareFree(grid.getSquareByGrid(point2)))
			{
				//Keep looping
				loop = true;
			}
			
			//Create a point to check at
			point3 = new PointI();
			point3.y = point.y + 1;
			point3.x = point.x;
			
			//Check if the square is free
			if(!grid.isSquareFree(grid.getSquareByGrid(point3)))
			{
				//Keep looping
				loop = true;
			}
			
			//Create a point to check at
			point4 = new PointI();
			point4.y = point.y + 2;
			point4.x = point.x;
			
			//Check if the square is free
			if(!grid.isSquareFree(grid.getSquareByGrid(point4)))
			{
				//Keep looping
				loop = true;
			}
			
			//Create a point to check at
			point5 = new PointI();
			point5.y = point.y + 1;
			point5.x = point.x + 1;
			
			//Check if the square is free
			if(!grid.isSquareFree(grid.getSquareByGrid(point5)))
			{
				//Keep looping
				loop = true;
			}
		}
		
		//Add to the sector the triangles
		sector.addTriangle(grid.getSquareByGrid(point).getRightTriangle());
		sector.addTriangle(grid.getSquareByGrid(point).getBottomTriangle());
		
		sector.addTriangle(grid.getSquareByGrid(point2).getLeftTriangle());
		sector.addTriangle(grid.getSquareByGrid(point2).getBottomTriangle());
		
		sector.addTriangle(grid.getSquareByGrid(point3).getInnerTrianglesCell());
		sector.addTriangle(grid.getSquareByGrid(point4).getInnerTrianglesCell());
		
		sector.addTriangle(grid.getSquareByGrid(point5).getTopTriangle());
		sector.addTriangle(grid.getSquareByGrid(point5).getLeftTriangle());
		
		//Tell the grid that these triangles are in use
		grid.occupyTriangle(sector.getList());
		
		//Return the sector
		return sector;
	}
	public Sector generate2(SectorGrid grid)
	{
		Sector sector = new Sector(Color.red);
		
		List<PointI> points = new ArrayList();
		
		Set<SquareCell> except = new HashSet<SquareCell>();
		
		boolean loop = true;
		
		while(loop)
		{
			loop = false;
			
			points.clear();
			
			SquareCell square = grid.getEmptySquare(except);
			
			except.add(square);
			
			points.add(grid.getGridCoordinate(square));
			
			Iterator<PointI> iterator = points.iterator();
			
			while(iterator.hasNext())
			{
				if(!grid.isSquareFree(iterator.next()))
				{
					loop = true;
					break;
				}
			}
		}
		
		Iterator<PointI> iterator = points.iterator();
		
		while(iterator.hasNext())
		{
			sector.addTriangle(grid.getSquareByGrid(iterator.next()).getInnerTrianglesCell());
		}
		
		grid.occupyTriangle(sector.getList());
		return sector;
	}
	
	public Sector generate3(SectorGrid grid)
	{
		//Create sector with a random color or specified one
		Sector sector = new Sector(Color.CYAN);
		
		//Create set for already tested starting points for the region
		LinkedHashSet<SquareCell> squareList = new LinkedHashSet<SquareCell>();
		
		ArrayList<TriangleCell> triangleList = new ArrayList<TriangleCell>();
		
		//Loop until the point returned has the correct free space
		boolean loop = true;
		while(loop)
		{
			loop = false;
			
			//Clear the list
			triangleList.clear();
			
			//Get an empty square from the grid,
			//except the ones that where tried before (located in squareList)
			
			SquareCell startSquare = grid.getEmptySquare(squareList);
			
			//Add the returned square in the list
			squareList.add(startSquare);
			
			//Get the grid coordinate of the square
			PointI origin = grid.getGridCoordinate(startSquare);
			
			//Populate the list with the triangles requested from the grid
			triangleList.addAll(grid.getSquareByGrid(origin.x, origin.y).getInnerTrianglesCell());
			
			triangleList.add(grid.getSquareByGrid(origin.x + 1, origin.y).getLeftTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y - 1).getBottomTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y + 1).getTopTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y + 1).getLeftTriangle());
			
			//Check if the triangles are not used
			synchronized(triangleList)
			{
				Iterator<TriangleCell> sectorIterator = triangleList.iterator();
				
				while(sectorIterator.hasNext())
					if(!grid.isTriangleFree(sectorIterator.next()))
						loop = true;
			}
		}
		//Add the list to sector
		sector.addTriangle(triangleList);
		//Tell the grid that these triangles are in use
		grid.occupyTriangle(sector.getList());
		
		//Return the sector
		return sector;
	}
}
