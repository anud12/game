package game.library.world.sector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import game.geom.classes.PointF;
import game.geom.classes.PointI;
import game.geom.classes.RightTriangle;
import game.library.world.sector.cell.Cell;
import game.library.world.sector.cell.SquareCell;
import game.library.world.sector.cell.TriangleCell;
import game.library.world.sector.cellSelector.CellSelector;

public class SectorGrid {
	
	//Size of the cell
	protected float cellSize;
	
	//Point [0,0]
	protected PointF origin;
	
	//List to save the grid
	protected HashMap<PointF, SquareCell> squareList;
	protected HashMap<PointF, RightTriangle> triangleList;
	protected HashMap<PointF, RightTriangle> occupiedTriangle;
	protected HashMap<PointF, SquareCell> occupiedSquare;
	
	public HashMap<PointF, SquareCell> getOccupySquare()
	{
		return occupiedSquare;
	}
	
	//Constructor
	public SectorGrid(float cellSize, PointF origin)
	{
		//Size of the cell
		this.cellSize = cellSize;
		//Point [0,0]
		this.origin = origin;
		
		//List to save the grid
		squareList = new HashMap<PointF, SquareCell>();
		triangleList = new HashMap<PointF, RightTriangle>();
		occupiedTriangle = new HashMap<PointF, RightTriangle>();
		occupiedSquare = new HashMap<PointF, SquareCell>();
	}
	
	public void occupyTriangle(TriangleCell triangle)
	{
		//Check if the triangle is already occupied
		if(!occupiedTriangle.containsKey(triangle.getCenter()))
		{
			//Put triangle to occupied list
			occupiedTriangle.put(triangle.getCenter(), triangle);
			//Get parent square
			SquareCell square = triangle.getParent();
			if(!occupiedSquare.containsKey(square.getCenter()))
			{
				//Put square to occupied list
				occupiedSquare.put(square.getCenter(), square);
			}
		}
		
		
	}
	public void occupyTriangle(List<TriangleCell> triangles)
	{
		Iterator<TriangleCell> iterator = triangles.iterator();
		while(iterator.hasNext())
		{
			TriangleCell triangle = iterator.next();
			
			//Check if the triangle is already occupied
			if(!occupiedTriangle.containsKey(triangle.getCenter()))
			{
				//Put triangle to occupied list
				occupiedTriangle.put(triangle.getCenter(), triangle);
				//Get parent square
				SquareCell square = triangle.getParent();
				if(!occupiedSquare.containsKey(square.getCenter()))
				{
					//Put square to occupied list
					occupiedSquare.put(square.getCenter(), square);
				}
			}
		}
	}
	public boolean isTriangleFree(RightTriangle triangle)
	{
		PointF point = triangle.getCenter();
		if(occupiedTriangle.containsKey(point))
			return false;
		
		return true;
	}
	public void occupySquare(SquareCell square)
	{
		//Check if the square is already occupied
		if(!occupiedSquare.containsKey(square.getCenter()))
		//Put triangle to occupied list
		occupiedSquare.put(square.getCenter(), square);
		
	}
	// Check if the square is free //
	public boolean isSquareFree(PointF point)
	{
		if(occupiedSquare.containsKey(point))
			return false;
		return true;
	}
	
	public boolean isSquareFree(PointI point) {
		return isSquareFree(point.x, point.y);
	}
	
	public boolean isSquareFree(int x, int y)
	{
		PointF point = new PointF(x * cellSize + origin.x, y * cellSize + origin.y);
		return isSquareFree(point);
	}
	public boolean isSquareFree(SquareCell square)
	{
		return isSquareFree(square.getCenter());
	}
	
	
	//  Get methods  //
	//Square
	public SquareCell getSquareByGrid(PointI gridLocation)
	{
		return getSquareByGrid(gridLocation.x, gridLocation.y);
	}
	public SquareCell getSquareByGrid(int x, int y)
	{
		
		//Transform from rows and columns to world position
		PointF point = new PointF(x * cellSize + origin.x, y * cellSize + origin.y);
		
		//Check if square exists
		//If not create it
		if(!squareList.containsKey(point))
		{
			//Create square
			SquareCell square = new SquareCell(cellSize, point.x, point.y);
			//Get triangles
			Iterator<TriangleCell> triangleIterator = square.getInnerTrianglesCell().iterator();
			
			//Add triangles to triangleList
			while(triangleIterator.hasNext())
			{
				TriangleCell triangle = triangleIterator.next();
				triangleList.put(triangle.getCenter(), triangle);
			}
			//Add square to squareList
			squareList.put(point, square);
		}
		
		return squareList.get(point);
	}
	public SquareCell getSquareByKey(PointF key)
	{
		return squareList.get(key);
	}
	public PointI getGridCoordinate(SquareCell square)
	{
		PointF center = square.getCenter();
		PointI gridLocation = new PointI();
		
		gridLocation.x = (int) ((center.x  - origin.x)/ cellSize);
		gridLocation.y = (int) ((center.y  - origin.y) / cellSize);
		
		return gridLocation;
	}
	//Lists
	public Set<PointF> getKeys()
	{
		return squareList.keySet();
	}
	
	//Get square with properties defined by selector
	public SquareCell getEmptyCell(Set<Cell> except, CellSelector selector)
	{
		//Check if this search will modify global
		//parameters
		//Get the current range
		int range = selector.getOmogenRange();
		//Set the boolean used to check
		//if it has been found
		boolean found = false;
		
		//Initialize the point
		PointI point = new PointI(0,0);
		
		//Loop while the empty cell isn't found
		while(!found)
		{
			
			//Reset the location to the new size if applicable
			point.x = range;
			point.y = range;
			
			//Get the square
			SquareCell square = getSquareByGrid(point);
			
			//Check if the square is valid according to
			//the specified selection property
			if(selector.isValid(square, except))
			{
				return selector.returnValue(square);
			}
			
			//Set the location to the negative mirror
			point.x = -range;
			point.y = -range;
			
			//Get the square
			square = getSquareByGrid(point);
			
			//Check if the square is valid according to
			//the specified selection property
			if(selector.isValid(square, except))
			{
				//Return the square
				return selector.returnValue(square);
			}
			
			//Calculate the outer ring size
			//000
			//0X0
			//000
			int maxSteps = (int) (range * 2 + 1);
			
			//Set the iterator to 0
			int offset = 0;
			while(!found && offset < maxSteps)
			{
				//Move to
				//000
				//000
				//0X0
				point.x = -range + offset;
				point.y = -range;
				
				//Get the square
				square = getSquareByGrid(point);
				
				//Check if the square is valid according to
				//the specified selection property
				if(selector.isValid(square, except))
				{
					return selector.returnValue(square);
				}
				//Move to
				//000
				//X00
				//000
				point.x = -range;
				point.y = -range + offset;
				
				//Get the square
				square = getSquareByGrid(point);

				//Check if the square is valid according to
				//the specified selection property
				if(selector.isValid(square, except))
				{
					return selector.returnValue(square);
				}
				
				//Move to
				//0X0
				//000
				//000
				point.x = range - offset;
				point.y = range;
			
				//Get the square
				square = getSquareByGrid(point);

				//Check if the square is valid according to
				//the specified selection property
				if(selector.isValid(square, except))
				{
					return selector.returnValue(square);
				}
				//Move to
				//000
				//00X
				//000
				point.x = range;
				point.y = range - offset;
				
				//Get the square
				square = getSquareByGrid(point);

				//Check if the square is valid according to
				//the specified selection property
				if(selector.isValid(square, except))
				{
					return selector.returnValue(square);
				}
				
				offset++;
				selector.updateOmogenRange();
			}
			//Increase the scope of the search
			if(!found)
			{
				range++;
			}
		}
		return null;
	}
}
