package game.library;

public class Entity
{

    private char character;
    
    public void setCharacter(char ch)
    {
        character = ch;
    }
    public char getCharacter()
    {
        return character;
    }
    
    private Point location;
    
    public void setLocation(int x ,int y)
    {
        location.setX(x);
        location.setY(y);
    }
    public void setLocation(Point point)
    {
        location.setX(point.getX());
        location.setY(point.getY());
    }
    
    private Rectangle rectangle;
 
    public Rectangle getRectangle()
    {
        return rectangle;
    }
    public void setRectangle(Rectangle rectangle)
    {
        this.rectangle = rectangle;
    }
    
    public Entity()
    {
        location = new Point();
        rectangle = new Rectangle();
    }
}
