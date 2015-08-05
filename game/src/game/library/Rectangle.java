package game.library;

public class Rectangle
{

    private Point x1;

    public Point getX1()
    {
        return x1;
    }

    public void setX1(Point x1)
    {
        this.x1 = x1;
    }

    private Point x2;

    public Point getX2()
    {
        return x2;
    }

    public void setX2(Point x2)
    {
        this.x2 = x2;
    }

    private Point y1;

    public Point getY1()
    {
        return y1;
    }

    public void setY1(Point y1)
    {
        this.y1 = y1;
    }

    private Point y2;

    public Point getY2()
    {
        return y2;
    }

    public void setY2(Point y2)
    {
        this.y2 = y2;
    }

    public Rectangle()
    {
         x1 = new Point();
         x2 = new Point();
         y1 = new Point();
         y2 = new Point();
    }
}
