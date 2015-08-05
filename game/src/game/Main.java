package game;

import game.library.Entity;
import game.library.Point;
import java.awt.AWTEvent;

public class Main
{
    public static void main(String[] args)
    {
        Entity entity = new Entity();
        
        
        MainWindow window = new MainWindow();
        window.setVisible(true);
        window.setEntity(entity);
        window.update();
    }
    
}
