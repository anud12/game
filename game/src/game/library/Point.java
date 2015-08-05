package game.library;

public class Point
{
    int x;
    int y;
    
    public Point()
    {
        
    }
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    
    public void setX(int arg)
    {
        x = arg;
    }
    public void setY(int arg)
    {
        y = arg;
    }
}
