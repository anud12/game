package game.library.world.sector.generator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import game.geom.classes.PointI;
import game.library.world.sector.Sector;
import game.library.world.sector.SectorGrid;
import game.library.world.sector.cell.Cell;
import game.library.world.sector.cell.SquareCell;
import game.library.world.sector.cell.TriangleCell;
import game.library.world.sector.cellSelector.CellSelector;

public class SquareSectorGenerator {

	public Sector generate(SectorGrid grid)
	{
		Sector sector = new Sector(Color.CYAN);
		
		Set<Cell> except = new HashSet<>();
		
		List<TriangleCell> triangleList = new ArrayList<TriangleCell>();
		
		SquareCell square = null;
		
		boolean loop = false;
		do
		{
			triangleList.clear();
			
			loop = false;
			square = grid.getEmptyCell(except, CellSelector.leftTriangle(grid));
			except.add(square);
			
			PointI origin = grid.getGridCoordinate(square);
			
			triangleList.add(grid.getSquareByGrid(origin.x, origin.y).getLeftTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x - 1, origin.y).getRightTriangle());
			triangleList.add(grid.getSquareByGrid(origin.x - 1, origin.y).getTopTriangle());
			
			triangleList.add(grid.getSquareByGrid(origin.x - 1, origin.y - 1).getBottomTriangle());
			
			Iterator<TriangleCell> iterator = triangleList.iterator();
			while(iterator.hasNext())
			{
				if(!grid.isTriangleFree(iterator.next()))
				{
					loop = true;
					break;
				}
			}
		}
		while(loop);
		
		sector.addTriangle(triangleList);
		grid.occupyTriangle(sector.getList());
		return sector;
	}
}
