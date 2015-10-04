package game.experimental;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import javax.swing.JTextField;

import game.gameLoop.MoveAction;
import game.library.Pawn;
import game.library.behaviour.Harvester;
import javafx.scene.input.KeyCode;

public class InputReader extends KeyAdapter
{
	JTextField field;
	Pawn pawn;
	public InputReader (JTextField field, Pawn pawn)
	{
		this.field = field;
		this.pawn = pawn;
	}
	 @Override
     public void keyPressed(KeyEvent event) 
	 {
		 
		 if(event.getKeyCode() == KeyEvent.VK_ENTER)
		 {
			 
			 String text[] = this.field.getText().split(" ");
			 switch(text[0])
				{
					case "move":
					{
						if(text.length > 2)
						{
							float x = Float.parseFloat(text[1]);
							float y = Float.parseFloat(text[2]);
							
							Point2D.Float point2d = new Point2D.Float(x, y);
							pawn.clearActionList();
							pawn.addAction(new MoveAction(pawn, point2d));
							field.setText("");
						}
						break;
						
					}
					case "harvest":
					{
						pawn.setBehavior(new Harvester(pawn));
						break;
					}
				}
		 }
     }
}
