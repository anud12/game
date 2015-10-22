package game.library.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import game.geom.classes.PointF;
import game.geom.classes.PointI;
import game.geom.classes.Triangle;

public class SectorGrid {
	
	protected float cellSize;
	
	//Size of the grid
	protected int tesselatedSquare;
	protected int tesselatedTriangle;
	
	//List to save the grid
	protected HashMap<PointF, SquareCell> squareList;
	protected HashMap<PointF, Triangle> triangleList;
	protected HashMap<PointF, Triangle> occupiedTriangle;
	protected HashMap<PointF, SquareCell> occupiedSquare;
	
	public HashMap<PointF, SquareCell> getOccupySquare()
	{
		return occupiedSquare;
	}
	
	//Constructor
	public SectorGrid(float cellSize)
	{
		squareList = new HashMap<PointF, SquareCell>();
		triangleList = new HashMap<PointF, Triangle>();
		
		occupiedTriangle = new HashMap<PointF, Triangle>();
		occupiedSquare = new HashMap<PointF, SquareCell>();
		
		this.cellSize = cellSize;
	}
	
	public void occupyTriangle(TriangleCell triangle)
	{
		//Check if the triangle is already occupied
		if(!occupiedTriangle.containsKey(triangle.getCenter()))
		{
			//Put triangle to occupied list
			occupiedTriangle.put(triangle.getCenter(), triangle);
			//Get parent square
			SquareCell square = triangle.parent;
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
				SquareCell square = triangle.parent;
				if(!occupiedSquare.containsKey(square.getCenter()))
				{
					//Put square to occupied list
					occupiedSquare.put(square.getCenter(), square);
				}
			}
		}
	}
	public boolean isTriangleFree(Triangle triangle)
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
	public boolean isSquareFree(PointI point) {
		return isSquareFree(point.x, point.y);
	}
	
	public boolean isSquareFree(int x, int y)
	{
		PointF point = new PointF(x * cellSize, y * cellSize);
		if(occupiedSquare.containsKey(point))
			return false;
		
		return true;
	}
	public boolean isSquareFree(SquareCell square)
	{
		PointF point = square.getCenter();
		if(occupiedSquare.containsKey(point))
			return false;
		
		return true;
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
		PointF point = new PointF(x * cellSize, y * cellSize);
		
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
		
		gridLocation.x = (int) (center.x / cellSize);
		gridLocation.y = (int) (center.y / cellSize);
		
		return gridLocation;
	}
	//Lists
	public Set<PointF> getKeys()
	{
		return squareList.keySet();
	}
	
	//Get empty cells
	public SquareCell getEmptySquare(Set<SquareCell> except)
	{
		//Check if this search will modify global
		//parameters
		//Get the current range
		int range = tesselatedSquare;
		//Set the boolean used to check
		//if it has been found
		boolean found = false;
		
		//Initialize the point
		PointI point = new PointI(tesselatedSquare,tesselatedSquare);
		
		//Loop while the empty cell isn't found
		while(!found)
		{
			
			//Reset the location to the new size if applicable
			point.x = range;
			point.y = range;
			
			//Get the square
			SquareCell square = getSquareByGrid(point);
			//Check if it is in the used list
			if(isSquareFree(square ) && !except.contains(square))
			{
				return square;
			}
			
			//Set the location to the negative mirror
			point.x = -range;
			point.y = -range;
			
			//Get the square
			square = getSquareByGrid(point);
			//Check if it is in the used list
			if(isSquareFree(square ) && !except.contains(square))
			{
				return square;
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
				//Check if it is in the used list
				if(isSquareFree(square ) && !except.contains(square))
				{
					return square;
				}
				//Move to
				//000
				//X00
				//000
				point.x = -range;
				point.y = -range + offset;
				
				//Get the square
				square = getSquareByGrid(point);
				//Check if it is in the used list
				if(isSquareFree(square ) && !except.contains(square))
				{
					return square;
				}
				
				//Move to
				//0X0
				//000
				//000
				point.x = range - offset;
				point.y = range;
			
				//Get the square
				square = getSquareByGrid(point);
				//Check if it is in the used list
				if(isSquareFree(square ) && !except.contains(square))
				{
					return square;
				}
				//Move to
				//000
				//00X
				//000
				point.x = range;
				point.y = range - offset;
				
				//Get the square
				square = getSquareByGrid(point);
				//Check if it is in the used list
				if(isSquareFree(square ) && !except.contains(square))
				{
					return square;
				}
				
				offset++;
			}
			//Increase the scope of the search
			if(!found)
			{
				range++;
			}
		}
		return null;
	}
	
	public SquareCell getEmptySquare()
	{
		//Get the current range
		int range = tesselatedSquare;
		//Set the boolean used to check
		//if it has been found
		boolean found = false;
		
		//Initialize the point
		PointI point = new PointI(tesselatedSquare,tesselatedSquare);
		
		//Loop while the empty cell isn't found
		while(!found)
		{
			
			//Reset the location to the new size if applicable
			point.x = range;
			point.y = range;
			
			//Get the square
			SquareCell square = getSquareByGrid(point);
			//Check if it is in the used list
			if(isSquareFree(square ))
			{
				return square;
			}
			
			//Set the location to the negative mirror
			point.x = -range;
			point.y = -range;
			
			//Get the square
			square = getSquareByGrid(point);
			//Check if it is in the used list
			if(isSquareFree(square ))
			{
				return square;
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
				//Check if it is in the used list
				if(isSquareFree(square ))
				{
					return square;
				}
				
				//Move to
				//000
				//X00
				//000
				point.x = -range;
				point.y = -range + offset;
				
				//Get the square
				square = getSquareByGrid(point);
				//Check if it is in the used list
				if(isSquareFree(square ))
				{
					return square;
				}
				
				//Move to
				//0X0
				//000
				//000
				point.x = range - offset;
				point.y = range;
			
				//Get the square
				square = getSquareByGrid(point);
				//Check if it is in the used list
				if(isSquareFree(square ))
				{
					return square;
				}
				//Move to
				//000
				//00X
				//000
				point.x = range;
				point.y = range - offset;
				
				//Get the square
				square = getSquareByGrid(point);
				//Check if it is in the used list
				if(isSquareFree(square ))
				{
					return square;
				}
				
				offset++;
			}
			//Increase the scope of the search
			if(!found)
			{
				tesselatedSquare++;
				range = tesselatedSquare;
			}
		}
		return null;
	}
	
	
	public TriangleCell getEmptyTriangle()
	{
		//Set the boolean used to check
		//if it has been found
		boolean found = false;
		
		//Initialize the point
		PointI point = new PointI(tesselatedTriangle,tesselatedTriangle);
		
		//Loop while the empty cell isn't found
		while(!found)
		{
			
			//Reset the location to the new size if applicable
			point.x = tesselatedTriangle;
			point.y = tesselatedTriangle;
			
			//Get the triangles from the squares
			Iterator<TriangleCell> triangles = this.getSquareByGrid(point).innerTriangles.iterator();
			while(triangles.hasNext())
			{
				TriangleCell triangle = triangles.next();
				///Check if it is in the used list
				if(this.isTriangleFree(triangle))
				{
					return triangle;
				}
			}
			
			//Set the location to the negative mirror
			point.x = -tesselatedTriangle;
			point.y = -tesselatedTriangle;
			
			//Check if it is in the used list
			//Get the triangles from the squares
			triangles = this.getSquareByGrid(point).innerTriangles.iterator();
			while(triangles.hasNext())
			{
				TriangleCell triangle = triangles.next();
				///Check if it is in the used list
				if(this.isTriangleFree(triangle))
				{
					return triangle;
				}
			}
			
			//Calculate the outer ring size
			//000
			//0X0
			//000
			int maxSteps = (int) (tesselatedTriangle * 2 + 1);
			
			//Set the iterator to 0
			int iter = 0;
			while(!found && iter < maxSteps)
			{
				//Move to
				//000
				//000
				//0X0
				point.x = -tesselatedTriangle + iter;
				point.y = -tesselatedTriangle;
				
				//Check if it is in the used list
				//Get the triangles from the squares
				triangles = this.getSquareByGrid(point).innerTriangles.iterator();
				while(triangles.hasNext())
				{
					TriangleCell triangle = triangles.next();
					///Check if it is in the used list
					if(this.isTriangleFree(triangle))
					{
						return triangle;
					}
				}
				
				//Move to
				//000
				//X00
				//000
				point.x = -tesselatedTriangle;
				point.y = -tesselatedTriangle + iter;
				
				//Check if it is in the used list
				triangles = this.getSquareByGrid(point).innerTriangles.iterator();
				while(triangles.hasNext())
				{
					TriangleCell triangle = triangles.next();
					///Check if it is in the used list
					if(this.isTriangleFree(triangle))
					{
						return triangle;
					}
				}
				
				//Move to
				//0X0
				//000
				//000
				point.x = tesselatedTriangle - iter;
				point.y = tesselatedTriangle;
			
				//Check if it is in the used list
				triangles = this.getSquareByGrid(point).innerTriangles.iterator();
				while(triangles.hasNext())
				{
					TriangleCell triangle = triangles.next();
					///Check if it is in the used list
					if(this.isTriangleFree(triangle))
					{
						return triangle;
					}
				}
				//Move to
				//000
				//00X
				//000
				point.x = tesselatedTriangle;
				point.y = tesselatedTriangle - iter;
				
				//Check if it is in the used list
				triangles = this.getSquareByGrid(point).innerTriangles.iterator();
				while(triangles.hasNext())
				{
					TriangleCell triangle = triangles.next();
					///Check if it is in the used list
					if(this.isTriangleFree(triangle))
					{
						return triangle;
					}
				}
				
				iter++;
			}
			//Increase the scope of the search
			if(!found)
			{
				tesselatedTriangle++;
			}
		}
		return null;
	}
	
	//TODO: Untested function
	public TriangleCell getEmptyTriangle(Set<TriangleCell> except)
	{
		//Check if this search will modify global
		//parameters
		//Get the current range
		int range = tesselatedTriangle;
		//Set the boolean used to check
		//if it has been found
		boolean found = false;
		
		//Initialize the point
		PointI point = new PointI(tesselatedTriangle,tesselatedTriangle);
		
		//Loop while the empty cell isn't found
		while(!found)
		{
			System.out.println("GRID->Except contains :");
			Iterator<TriangleCell> iterator = except.iterator();
			while(iterator.hasNext())
			{
				System.out.println(iterator.next());
			}
			
			
			//Reset the location to the new size if applicable
			point.x = range;
			point.y = range;
			
			//Get the square
			SquareCell square = getSquareByGrid(point);
			
			Iterator<TriangleCell> triangleIterator = square.getInnerTrianglesCell().iterator();
			while(triangleIterator.hasNext())
			{
				TriangleCell triangle = triangleIterator.next();
				//Check if it is in the used list
				if(isTriangleFree(triangle ) && !except.contains(triangle))
				{
					return triangle;
				}
			}
			
			
			//Set the location to the negative mirror
			point.x = -range;
			point.y = -range;
			
			//Get the square
			square = getSquareByGrid(point);

			triangleIterator = square.getInnerTrianglesCell().iterator();
			while(triangleIterator.hasNext())
			{
				TriangleCell triangle = triangleIterator.next();
				//Check if it is in the used list
				if(isTriangleFree(triangle ) && !except.contains(triangle))
				{
					return triangle;
				}
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
				triangleIterator = square.getInnerTrianglesCell().iterator();
 				while(triangleIterator.hasNext())
				{
					TriangleCell triangle = triangleIterator.next();
					//Check if it is in the used list
					if(isTriangleFree(triangle ) && !except.contains(triangle))
					{
						return triangle;
					}
				}
				//Move to
				//000
				//X00
				//000
				point.x = -range;
				point.y = -range + offset;
				
				//Get the square
				square = getSquareByGrid(point);
				triangleIterator = square.getInnerTrianglesCell().iterator();
				while(triangleIterator.hasNext())
				{
					TriangleCell triangle = triangleIterator.next();
					//Check if it is in the used list
					if(isTriangleFree(triangle ) && !except.contains(triangle))
					{
						return triangle;
					}
				}
				
				//Move to
				//0X0
				//000
				//000
				point.x = range - offset;
				point.y = range;
			
				//Get the square
				square = getSquareByGrid(point);
				triangleIterator = square.getInnerTrianglesCell().iterator();
				while(triangleIterator.hasNext())
				{
					TriangleCell triangle = triangleIterator.next();
					//Check if it is in the used list
					if(isTriangleFree(triangle ) && !except.contains(triangle))
					{
						return triangle;
					}
				}
				//Move to
				//000
				//00X
				//000
				point.x = range;
				point.y = range - offset;
				
				//Get the square
				square = getSquareByGrid(point);
				triangleIterator = square.getInnerTrianglesCell().iterator();
				while(triangleIterator.hasNext())
				{
					TriangleCell triangle = triangleIterator.next();
					//Check if it is in the used list
					if(isTriangleFree(triangle ) && !except.contains(triangle))
					{
						return triangle;
					}
				}
				
				offset++;
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
