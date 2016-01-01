package game.tests;

import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.engine.Engine;
import game.experimental.ExperimentalWorld;
import game.experimental.TextInterface;
import game.geom.classes.PointF;
import game.graphicalUserInterface.MainFrame;
import game.graphicalUserInterface.panels.EngineJPanel;
import game.graphicalUserInterface.panels.PanelSelector;
import game.library.attribute.AttributeSelector;
import game.library.pawn.Pawn;
import game.library.world.IWorld;

public class test
{

	public static void main(String args[])
	{
		Engine engine = new Engine(1000, 8);
		
		IWorld world = new ExperimentalWorld();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		PointF point;
    	Pawn pawn;
    	TextInterface inter;
    	
    	MainFrame mainWindow = new MainFrame();
    	PanelSelector selector = new PanelSelector();
    	mainWindow.add(selector);
		
    	EngineJPanel enginePanel = new EngineJPanel(engine);
    	selector.addPanel("Engine", enginePanel);
    	executor.execute(enginePanel);
    	
    	point = new PointF(70,100);
    	
    	pawn = new Pawn(20, 20, point, world);
    	pawn.set(AttributeSelector.movementSpeed(), 0.005f);
    	pawn.addKeyword("dropOff");
    	pawn.set(AttributeSelector.color(), Color.gray);
    	
		inter = new TextInterface(pawn, executor);
		selector.addPanel(pawn.get(AttributeSelector.ID()).toString(), inter);
		
		executor.execute(inter);
		
		engine.addAction(pawn.getController());
		engine.addVariable(world);
		
		executor.execute(engine);
	}

}
