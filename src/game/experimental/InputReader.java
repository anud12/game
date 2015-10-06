package game.experimental;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.concurrent.ExecutorService;

import javax.swing.JTextField;

import game.gameLoop.MoveAction;
import game.library.Entity;
import game.library.Pawn;
import game.library.behaviour.Harvester;
import game.library.interfaces.IWorld;
import game.tests.InteractiveTest;
import javafx.scene.input.KeyCode;

public class InputReader extends KeyAdapter
{
	JTextField field;
	Pawn pawn;
	IWorld world;
	ExecutorService executor;
	
	
	public InputReader (JTextField field, Pawn pawn, IWorld world, ExecutorService executor)
	{
		this.field = field;
		this.pawn = pawn;
		this.world = world;
		
		this.executor = executor;
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
						field.setText("");
						break;
					}
					case "spawn":
					{
						if(text.length > 3)
						{
							float x = Float.parseFloat(text[2]);
							float y = Float.parseFloat(text[3]);
							switch(text[1])
							{
								case "resource":
								{
									Entity ent = new Entity(2, 2, new Point2D.Float(x,y), world);
									
									ent.addKeyword("resource");
									break;
								}
								case "smallShip":
								{
									Pawn pawn = new Pawn(10, 10, new Point2D.Float(x,y), world);
							    	pawn.setMovementSpeed(0.010f);
							    	
									TextInterface inter = new TextInterface(pawn, executor);
									
									executor.execute(inter);
									
									InteractiveTest.gl.addAction(pawn);
									break;
								}
								case "dropOff":
								{
							    	Pawn pawn = new Pawn(20, 20, new Point2D.Float(x,y), world);
							    	pawn.setMovementSpeed(0.005f);
							    	pawn.addKeyword("dropOff");
							    	
									TextInterface inter = new TextInterface(pawn, executor);
									
									executor.execute(inter);
									break;
								}
								
							}
							field.setText("");
						}
						break;
					}
				}
		 }
     }
}
