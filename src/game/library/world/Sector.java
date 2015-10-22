package game.library.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.geom.classes.Triangle;

public class Sector 
{
	protected ArrayList<TriangleCell> triangleList;
	protected Color color;
	
	public Sector()
	{
		this.triangleList = new ArrayList<TriangleCell>();
		
		Random random = new Random();
		
		color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
		
	}
	public Sector(Color color)
	{
		this.triangleList = new ArrayList<TriangleCell>();
		
		
		this.color = color;
		
	}
	public List<TriangleCell> getList()
	{
		return triangleList;
	}
	public void addTriangle(TriangleCell triangle)
	{
		triangleList.add(triangle);
	}
	public void addTriangle(List<TriangleCell> list)
	{
		triangleList.addAll(list);
	}
	public Color getColor()
	{
		return color;
	}
	
}
