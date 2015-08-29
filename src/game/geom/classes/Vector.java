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
		length = Math.sqrt(x*x + y*y);
	}

	@Override
	public void setY(double Y) 
	{
		this.y = Y;
		length = Math.sqrt(x*x + y*y);
	}

	@Override
	public void normalize() 
	{
		
		x = x / length;
		y = y / length;
	}

	@Override
	public void multiplyByScalar(double scalar) {
		x = x * scalar;
		y = y * scalar;
		length = Math.sqrt(x*x + y*y);
	}

	@Override
	public void equal(IVector vector) {
		this.x = vector.getX();
		this.y = vector.getY();
		this.length = vector.getLength();
	}

	@Override
	public double getLength() {
		return length;
	}
}
