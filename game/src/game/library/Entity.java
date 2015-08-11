package game.library;

import java.awt.Point;

public class Entity implements Comparable
{

    //Attributes:
	private char character;
    private Rectangle rectangle;
    
    
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
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    public void setRectangle(Rectangle rectangle)
    {
        this.rectangle = rectangle;
    }
    
    //gets the location of our entity
    public Point getPosition()
    {
    	return this.rectangle.getCenter();
    }
   
    //constructor implicit 
    /*
    public Entity()
    {
        rectangle = new Rectangle();
    } 
    */  
    //constructor Entity
    public Entity(int width,int lenght,Point origin)
    {
    	rectangle = new Rectangle(width,lenght,origin);
    }
    
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

}
