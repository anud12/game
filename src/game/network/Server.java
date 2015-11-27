package game.network;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import game.network.component.Router;
import game.network.component.Session;
import game.network.protocol.ChatProtocol;
import game.network.protocol.Protocol;
import game.network.protocol.TextDisplayProtocol;

public class Server implements Runnable
{
	protected Router router ;
	protected HashMap<SocketAddress, Session> sessions;
	protected HashSet<Protocol> protocols;
		
	public static void main (String[] args)
	{
		Server server = new Server();
		server.run();
		server.addProtocol(new ChatProtocol());
		server.addProtocol(new TextDisplayProtocol());
		
	}
	
	public Server()
	{
		sessions = new HashMap<>();
		protocols = new HashSet<Protocol>();
	}
	
	@Override
	public void run() 
	{
		Router router = new Router();
		
		router.setServer(this);
		
		router.run();
		
	}
	
	public void onReply(Session session) throws Exception
	{
		byte[] message = session.getReply();
		
		Iterator<Protocol> iterator = protocols.iterator();
		
		while(iterator.hasNext())
		{
			Protocol protocol = iterator.next();
			if(protocol.isApplicable(message))
			{
				protocol.interpret(message, session);
			}
		}
	}
	
	public void addProtocol (Protocol protocol)
	{
		this.protocols.add(protocol);
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
		
		Iterator<Protocol> iterator = protocols.iterator();
		while(iterator.hasNext())
		{
			Protocol protocol = iterator.next();
			protocol.onDisconnect(session);
		}
		
	}
}
