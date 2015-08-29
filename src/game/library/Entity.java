package game.library;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Entity
{

    //Attributes:
	private char character;
    private Rectangle2D.Float rectangle;
    private Point2D.Float center;
    
    
    //Methods:
    //get/set character [?]
    public void setCharacter(char ch)
    {
        character = ch;
    }
    public char getCharacter()
    {
        return character;
    }
   
    //get/set rectangle 
    public Rectangle2D.Float getRectangle()
    {
        return rectangle;
    }
    public void setRectangle(Rectangle2D.Float rectangle)
    {
        this.rectangle = rectangle;
    }
    
    //gets the location of our entity
    public Point2D.Float getCenter()
    {
    	return this.center;
    }
    public void setCenter(float x , float y)
    {
    	this.center.x = x;
    	this.center.y = y;
    }
    //constructor Entity
    public Entity(int width,int height,Point2D.Float origin)
    {
    	rectangle = new Rectangle2D.Float();
    	
    	rectangle.x = origin.x;
    	rectangle.y = origin.y;
    	
    	this.center = new Point2D.Float(origin.x,origin.y);
    	
    	rectangle.width = width;
    	rectangle.height = height;
    }
    
    /*
	@Override
    public int compareTo(Object o) {
	try{
		Entity e = (Entity)o;	
		if(this.getPosition().distance(new Point(0,0)) == e.getPosition().distance(new Point(0,0)))
		{
			return 0;
		}
		else if (this.getPosition().distance(new Point(0,0)) > e.getPosition().distance(new Point(0,0)))
		{
			return 1;
		}
		else return -1;
	}
	catch(Exception ex){
		return 0;
	}
	}
	*/
}
