package game.library.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import game.geom.classes.PointF;
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
	public void occupyTriangle(List<TriangleCell> triangles)
	{
		Iterator<TriangleCell> iterator = triangles.iterator();
		while(iterator.hasNext())
		{
			TriangleCell triangle = iterator.next();
			occupiedTriangle.put(triangle.getCenter(), triangle);
			SquareCell square = triangle.parent;
			if(!occupiedSquare.containsKey(square.getCenter()))
			{
				//Put square to occupied list
				occupiedSquare.put(square.getCenter(), square);
			}
		}
	}
	public void occupySquare(SquareCell square)
	{
		//Put triangle to occupied list
		occupiedSquare.put(square.getCenter(), square);
		
	}
	
	public boolean isTriangleFree(Triangle triangle)
	{
		PointF point = triangle.getCenter();
		if(occupiedTriangle.containsKey(point))
			return false;
		
		return true;
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
				Triangle triangle = triangleIterator.next();
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
	public Set<PointF> getKeys()
	{
		return squareList.keySet();
	}
	public PointF getEmptySquare()
	{
		//Set the boolean used to check
		//if it has been found
		boolean found = false;
		
		//Initialize the point
		PointF point = new PointF(tesselatedSquare,tesselatedSquare);
		
		//Loop while the empty cell isn't found
		while(!found)
		{
			
			//Reset the location to the new size if applicable
			point.x = tesselatedSquare;
			point.y = tesselatedSquare;
						
			//Check if it is in the used list
			if(isSquareFree((int)point.x, (int)point.y ))
			{
				found = true;
				break;
			}
			
			//Set the location to the negative mirror
			point.x = -tesselatedSquare;
			point.y = -tesselatedSquare;
			
			//Check if it is in the used list
			if(isSquareFree((int)point.x, (int)point.y ))
			{
				found = true;
				break;
			}
			
			//Calculate the outer ring size
			//000
			//0X0
			//000
			int maxSteps = (int) (tesselatedSquare * 2 + 1);
			
			//Set the iterator to 0
			int iter = 0;
			while(!found && iter < maxSteps)
			{
				//Move to
				//000
				//000
				//0X0
				point.x = -tesselatedSquare + iter;
				point.y = -tesselatedSquare;
				
				//Check if it is in the used list
				if(isSquareFree((int)point.x, (int)point.y ))
				{
					found = true;
					break;
				}
				
				//Move to
				//000
				//X00
				//000
				point.x = -tesselatedSquare;
				point.y = -tesselatedSquare + iter;
				
				//Check if it is in the used list
				if(isSquareFree((int)point.x, (int)point.y ))
				{
					found = true;
					break;
				}
				
				//Move to
				//0X0
				//000
				//000
				point.x = tesselatedSquare - iter;
				point.y = tesselatedSquare;
			
				//Check if it is in the used list
				if(isSquareFree((int)point.x, (int)point.y ))
				{
					found = true;
					break;
				}
				//Move to
				//000
				//00X
				//000
				point.x = tesselatedSquare;
				point.y = tesselatedSquare - iter;
				
				//Check if it is in the used list
				if(isSquareFree((int)point.x, (int)point.y ))
				{
					found = true;
					break;
				}
				
				iter++;
			}
			//Increase the scope of the search
			if(!found)
			{
				tesselatedSquare++;
			}
		}
		return point;
	}
	
	public TriangleCell getEmptyTriangle()
	{
		//Set the boolean used to check
		//if it has been found
		boolean found = false;
		
		//Initialize the point
		PointF point = new PointF(tesselatedTriangle,tesselatedTriangle);
		
		//Loop while the empty cell isn't found
		while(!found)
		{
			
			//Reset the location to the new size if applicable
			point.x = tesselatedTriangle;
			point.y = tesselatedTriangle;
			
			//Get the triangles from the squares
			Iterator<TriangleCell> triangles = this.getSquareByGrid((int)point.x, (int)point.y ).innerTriangles.iterator();
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
			triangles = this.getSquareByGrid((int)point.x, (int)point.y ).innerTriangles.iterator();
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
				triangles = this.getSquareByGrid((int)point.x, (int)point.y ).innerTriangles.iterator();
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
				triangles = this.getSquareByGrid((int)point.x, (int)point.y ).innerTriangles.iterator();
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
				triangles = this.getSquareByGrid((int)point.x, (int)point.y ).innerTriangles.iterator();
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
				triangles = this.getSquareByGrid((int)point.x, (int)point.y ).innerTriangles.iterator();
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
}
