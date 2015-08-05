package game.library;

public class Entity
{

    char character;
    
    public Entity()
    {
        rectangle = new Rectangle();
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


    public void setCharacter(char ch)
    {
        character = ch;
    }
    public char getCharacter()
    {
        return character;
    }
}
