package game.geom.classes;

import game.geom.IVector;

public class Vector implements IVector 
{
	private double x;
	private double y;
	private double length;
	
	@Override
	public double getX() 
	{
		return x;
	}

	@Override
	public double getY() 
	{
		return y;
	}
	
	@Override
	public void setX(double X) 
	{
		this.x = X;
	}

	@Override
	public void setY(double Y) 
	{
		this.y = Y;
	}

	@Override
	public void normalize() 
	{
		length = Math.sqrt(x*x + y*y);
		x = x / length;
		y = y / length;
	}
}
