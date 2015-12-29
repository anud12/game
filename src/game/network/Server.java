package game.network;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import game.network.component.Router;
import game.network.component.Session;
import game.network.services.ChatService;
import game.network.services.Service;
import game.network.services.TextDisplayService;

public class Server implements Runnable
{
	//Router is used to listen and create sessions
	protected Router router ;
	//Sessions represents a connection
	protected HashMap<SocketAddress, Session> sessions;
	//List of loaded services
	protected HashSet<Service> services;
		
	public static void main (String[] args)
	{
		Server server = new Server();
		server.run();
		server.addService(new ChatService());
		server.addService(new TextDisplayService());
		
	}
	
	public Server()
	{
		sessions = new HashMap<>();
		services = new HashSet<Service>();
	}
	
	@Override
	public void run() 
	{
		Router router = new Router();
		
		router.setServer(this);
		
		router.run();
		
	}
	//
	public void onReply(Session session) throws Exception
	{
		//Get the message
		byte[] message = session.getReply();
		
		Iterator<Service> iterator = services.iterator();
		//Send the message to every service
		while(iterator.hasNext())
		{
			Service protocol = iterator.next();
			
			//Check if the message is for this service
			if(protocol.isApplicable(message))
			{
				//Send the message to the service
				protocol.interpret(message, session);
			}
		}
	}
	
	public void addService (Service service)
	{
		this.services.add(service);
	}
	
	public void broadcastGlobal(String message)
	{
		for(Iterator<SocketAddress> iterator  = sessions.keySet().iterator(); iterator.hasNext();)
		{
			sessions.get(iterator.next()).write(message);
		}
	}
	public synchronized void addSession(Session session)
	{
		sessions.put(session.getAddress(), session);
		System.out.print("Server sesssions :");
		System.out.print(sessions.size());
		System.out.print("\n");
	}
	public synchronized void removeSession(Session session)
	{
		sessions.remove(session.getAddress());
		System.out.print("Server sesssions :");
		System.out.print(sessions.size());
		System.out.print("\n");
	}
	
	public void onConnect(Session session)
	{
		System.out.print(session.getAddress());
		System.out.print(" connected");
		System.out.print("\n");
		addSession(session);
	}
	
	public void onDisconnect(Session session)
	{
		System.out.print(session.getAddress());
		System.out.print(" disconnected");
		System.out.print("\n");
		removeSession(session);
		
		Iterator<Service> iterator = services.iterator();
		while(iterator.hasNext())
		{
			Service service = iterator.next();
			service.onDisconnect(session);
		}
		
	}
}
