package game.geom;

public interface IVector 
{
	//Getters
	public double getX();
	public double getY();
	public double getLength();
	
	//Setters
	public void setX( double X);
	public void setY( double Y);
	
	//Operations
	public void normalize();
	public void multiplyByScalar(double scalar);
	public void equal(IVector vector);
}
