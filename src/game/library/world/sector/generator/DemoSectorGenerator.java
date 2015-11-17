package game.library.world.sector.generator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import game.geom.classes.PointF;
import game.geom.classes.PointI;
import game.geom.classes.Triangle;
import game.library.world.sector.Sector;
import game.library.world.sector.SectorGrid;
import game.library.world.sector.cell.Cell;
import game.library.world.sector.cell.SquareCell;
import game.library.world.sector.cell.TriangleCell;
import game.library.world.sector.cellSelector.CellSelector;

public class DemoSectorGenerator 
{
	public Sector generate(SectorGrid grid)
	{
		//Create sector with a random color or specified one
		Sector sector = new Sector(Color.white);
		
		//Create set for already tested starting points for the region
		Set<Cell> squareList = new LinkedHashSet<Cell>();
		
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
			
			SquareCell startSquare = grid.getEmptyCell(squareList, CellSelector.leftTriangle(grid));
			
			//Add the returned square in the list
			squareList.add(startSquare);
			
			//Get the grid coordinate of the square
			PointI origin = grid.getGridCoordinate(startSquare);
			
			//Populate the list with the triangles requested from the grid
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getTopTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getRightTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getBottomTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x + 1, origin.y).getLeftTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y - 1).getBottomTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y + 1).getTopTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y + 1).getRightTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x + 1, origin.y + 1).getLeftTriangle());
			
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
	
	public Sector generate2(SectorGrid grid)
	{
		//Create sector with a random color or specified one
		Sector sector = new Sector(Color.white);
		
		//Create set for already tested starting points for the region
		Set<Cell> squareList = new LinkedHashSet<Cell>();
		
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
			
			SquareCell startSquare = grid.getEmptyCell(squareList, CellSelector.square(grid));
			//Add the returned square in the list
			squareList.add(startSquare);
			
			//Get the grid coordinate of the square
			PointI origin = grid.getGridCoordinate(startSquare);
			
			//Populate the list with the triangles requested from the grid
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getTopTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getRightTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getBottomTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getLeftTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x + 1, origin.y).getLeftTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x + 1, origin.y).getBottomTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x - 1, origin.y).getRightTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x - 1, origin.y).getTopTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y - 1).getBottomTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y + 1).getTopTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y + 1).getRightTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x + 1, origin.y + 1).getLeftTriangle());
			
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
	
	public Sector generate3(SectorGrid grid)
	{
		//Create sector with a random color or specified one
		Sector sector = new Sector(Color.white);
		
		//Create set for already tested starting points for the region
		Set<Cell> squareList = new LinkedHashSet<Cell>();
		
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
			
			SquareCell startSquare = grid.getEmptyCell(squareList, CellSelector.rightTriangle(grid));
			//Add the returned square in the list
			squareList.add(startSquare);
			
			//Get the grid coordinate of the square
			PointI origin = grid.getGridCoordinate(startSquare);
			
			//Populate the list with the triangles requested from the grid
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getRightTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getTopTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getBottomTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x + 1, origin.y).getLeftTriangle());
			
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
