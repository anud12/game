package game.library.world.sector.cellSelector;

import java.util.Set;

import game.library.world.sector.SectorGrid;
import game.library.world.sector.cell.Cell;
import game.library.world.sector.cell.SquareCell;


//The CellSelector acts as an common interface and
// as a builder for objects inheriting from this class
public abstract class CellSelector 
{
	//Static Builder fields
	private static BottomTriangleSelector bottomTriangle;
	private static RightTriangleSelector rightTriangle;
	private static LeftTriangleSelector leftTriangle;
	private static TopTriangleSelector topTriangle;
	private static SquareSelector square;
	
	//Getter for the static fields
	public static CellSelector bottomTriangle(SectorGrid grid)
	{
		if(bottomTriangle == null)
		{
			bottomTriangle = new BottomTriangleSelector(grid);
		}
		bottomTriangle.setGrid(grid);
		return bottomTriangle;
	}
	public static CellSelector rightTriangle(SectorGrid grid)
	{
		if(rightTriangle == null)
		{
			rightTriangle = new RightTriangleSelector(grid);
		}
		rightTriangle.setGrid(grid);
		return rightTriangle;
	}
	public static CellSelector leftTriangle(SectorGrid grid)
	{
		if(leftTriangle == null)
		{
			leftTriangle = new LeftTriangleSelector(grid);
		}
		leftTriangle.setGrid(grid);
		return leftTriangle;
	}
	public static CellSelector topTriangle(SectorGrid grid)
	{
		if(topTriangle == null)
		{
			topTriangle = new TopTriangleSelector(grid);
		}
		topTriangle.setGrid(grid);
		return topTriangle;
	}
	public static CellSelector square(SectorGrid grid)
	{
		if(square == null)
		{
			square = new SquareSelector(grid);
		}
		square.setGrid(grid);
		return square;
	}
	
	// Implemented Methods
	
	//Parent grid
	protected SectorGrid parentGrid;
	
	//The range from which to search
	protected int omogenRange; 
		
	//Constructor
	public CellSelector(SectorGrid grid)
	{
		this.parentGrid = grid;
		omogenRange = 0;
	}
	//Setting the grid
	public void setGrid(SectorGrid grid)
	{
		this.parentGrid = grid;
		
	}
	//Setting the omogenRange
	public int getOmogenRange()
	{
		return omogenRange;
	}
	
	// Abstract methods to be implemented by the children//
	
	//The value to return
	public abstract SquareCell returnValue(SquareCell squareCell);
	//The algorithm to search
	public abstract boolean isValid(SquareCell squareCell, Set<Cell> except);
	
	
	

	
}
