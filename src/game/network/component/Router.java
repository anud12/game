package game.network.component;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.network.Server;

public class Router implements Runnable
{
	//Parent server to send the received messages
	protected Server server;
	
	public void setServer(Server server)
	{
		this.server = server;
	}
	
	@Override
	public void run() {
		try 
		{
			@SuppressWarnings("resource")
			//Create new socket with the port
			ServerSocket socket = new ServerSocket(35000);
			
			System.out.print("Connection open ");
			System.out.print(socket.getLocalSocketAddress());
			
			System.out.print("\n");
			
			ExecutorService executor = Executors.newCachedThreadPool();
			
			//Listens for a new connection
			while(true)
			{
				Session session = new Session(socket.accept(), server);
				//Create a new session for the connection
				executor.execute(session);
			}
		}
		catch (IOException e) 
		{
			System.err.println("Unable to open port");
			e.printStackTrace();
		}
	}
}
