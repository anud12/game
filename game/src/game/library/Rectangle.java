package game.library;

import java.awt.Point;

public class Rectangle
{
    private Point vertices[];
    private Point center;
    
    //get @cele 4 puncte care alcatuiesc dreptunghiul
    public Point[] getVertices()
    {
        return vertices;
    }
    
    //set Rectangle width/height
    public void setWidth(int width)
    {
         vertices[0].y = (int)center.y - width/2;
         vertices[1].y = (int)center.y + width/2;
         vertices[2].y = (int)center.y + width/2;    
         vertices[3].y = (int)center.y - width/2;        
    }
    public void setHeight(int height)
    {
        vertices[0].x = (int)center.x + height/2;
        vertices[1].x = (int)center.x + height/2;
        vertices[2].x = (int)center.x - height/2;
        vertices[3].x = (int)center.x - height/2;
    }
    
    //get Rectangle origin/center
    public Point getCenter()
    {
    	return center;
    }
    
    //constructor Rectangle
    //am adaugat 'origin' aici.
    public Rectangle(int width, int height, Point origin)
    {
         vertices = new Point[4];
         
         //center = new Point();
         center = origin;
         
         vertices[0] = new Point();
         vertices[0].x = (int)center.x + height/2;
         vertices[0].y = (int)center.y - width/2;
         
         vertices[1] = new Point();
         vertices[1].x = (int)center.x + height/2;
         vertices[1].y = (int)center.y + width/2;
         
         vertices[2] = new Point();
         vertices[2].x = (int)center.x - height/2;
         vertices[2].y = (int)center.y + width/2;
         
         vertices[3] = new Point();
         vertices[3].x = (int)center.x - height/2;
         vertices[3].y = (int)center.y - width/2;
    }
    
    /*
    public Rectangle()
    {
        vertices = new Point[4];
         
        center = new Point();
        
        for(int i = 0 ; i < 4 ; i++)
        {
            vertices[i] = new Point();
        }
    }
    */
    
    public void translate(int x, int y)
    {
        center.x += x;
        center.y += y;
        
        for(int i = 0 ; i < 4 ; i++)
        {
            vertices[i].x += x;
            vertices[i].y += y;
        }
    }
    public void translatef(float x, float y)
    {
    	center.x += x;
        center.y += y;
    }
    public void rotate(double deg)
    {
    	int x;
    	int y;
    	
    	double rad = Math.toRadians(deg);
    	
    	for(int i = 0 ; i < 4 ; i++)
    	{
    		x = (int) ( Math.cos(rad) * vertices[i].x - Math.sin(rad) * vertices[i].y );
    		y = (int) ( Math.sin(rad) * vertices[i].x + Math.cos(rad) * vertices[i].y );
    		
    		vertices[i].x = x;
    		vertices[i].y = y;
    	}
    }
}
