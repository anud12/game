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
				/*
				System.out.print("Delta Time ");
				System.out.print(engine.getDeltaTime());
				System.out.print(" actions: ");
				System.out.print(engine.getActionsSize());
				System.out.print(" addBuffer: ");
				System.out.print(engine.getAddBufferSize());
				System.out.print(" removeBufffer: ");
				System.out.print(engine.getRemoveBufferSize());
				System.out.print(" threads: ");
				System.out.print(engine.getCurrentThreadNumber());
				System.out.println();
				*/
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
				engine.addAction(new oneRunAction(random.nextInt((15000))));
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
