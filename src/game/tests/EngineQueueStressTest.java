package game.tests;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.engine.Engine;
import game.engine.IEngineAction;

public class EngineQueueStressTest 
{	
	public static void main(Engine engine)
	{
		//Engine engine = new Engine(100, 0);
		
		//ExecutorService executor = Executors.newCachedThreadPool();
		
		Random random = new Random();
		random.setSeed(1);
		
		
		
		//executor.execute(engine);
		
		int i = 0;
		
		while(true)
		{
			boolean add = true;
			if(i > 1)
			{				
				i = 0;
			}
			if(engine.getActionsSize() > 10000000)
			{
				add = false;
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(add)
			{
				engine.addAction(new oneRunAction(random.nextInt((15000) + 15000)));
			}
			i++;
			try {
				Thread.sleep(0, 1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
	}
}
