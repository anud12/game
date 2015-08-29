package game.geom;

public interface IVector 
{
	public double getX();
	public double getY();
	
	public void setX( double X);
	public void setY( double Y);
	
	public void normalize();
}
