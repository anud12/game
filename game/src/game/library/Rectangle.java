package game.library;

public class Rectangle
{
    private Point vertices[];   
    
    public Point[] getVertices()
    {
        return vertices;
    }
    
    public void setWidth(int width)
    {
         vertices[0].y = center.y - width/2;
         vertices[1].y = center.y + width/2;
         vertices[2].y = center.y + width/2;    
         vertices[3].y = center.y - width/2;        
    }
    
    public void setHeight(int height)
    {
        vertices[0].x = center.x + height/2;
        vertices[1].x = center.x + height/2;
        vertices[2].x = center.x - height/2;
        vertices[3].x = center.x - height/2;
    }
    private Point center;
    
    public Point getCenter()
    {
        return center;
    }
    
    public Rectangle(int width, int height)
    {
         vertices = new Point[4];
         
         center = new Point();
         
         vertices[0] = new Point();
         vertices[0].x = center.x + height/2;
         vertices[0].y = center.y - width/2;
         
         vertices[1] = new Point();
         vertices[1].x = center.x + height/2;
         vertices[1].y = center.y + width/2;
         
         vertices[2] = new Point();
         vertices[2].x = center.x - height/2;
         vertices[2].y = center.y + width/2;
         
         vertices[3] = new Point();
         vertices[3].x = center.x - height/2;
         vertices[3].y = center.y - width/2;
    }
    
    public Rectangle()
    {
        vertices = new Point[4];
         
        center = new Point();
        
        for(int i = 0 ; i < 4 ; i++)
        {
            vertices[i] = new Point();
        }
    }
    
    public void transformMove(int x, int y)
    {
        center.x += x;
        center.y += y;
        
        for(int i = 0 ; i < 4 ; i++)
        {
            vertices[i].x += x;
            vertices[i].y += y;
        }
    }
}
