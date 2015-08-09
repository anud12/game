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
        rectangle = new Rectangle();
    }
}
