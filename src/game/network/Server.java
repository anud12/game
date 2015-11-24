package game.network;

import java.net.SocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import game.network.component.Router;
import game.network.component.Session;
import game.network.protocol.ChatProtocol;
import game.network.protocol.IProtocol;

public class Server implements Runnable
{
	protected Router router ;
	protected HashMap<SocketAddress, Session> sessions;
	protected IProtocol protocol;
		
	public static void main (String[] args)
	{
		Server server = new Server();
		server.run();
		
	}
	
	public Server()
	{
		sessions = new HashMap<>();
		protocol = new ChatProtocol();
	}
	
	@Override
	public void run() 
	{
		Router router = new Router();
		
		router.setServer(this);
		
		router.run();
		
	}
	
	public void onReply(Session session)
	{
		String reply = session.getReplyList();
		
		Date date = new Date();
		System.out.print(date.toString());
		System.out.print(" | ");
		System.out.print("reply from ");
		
		System.out.print(session.getAddress());
		
		System.out.print(" = ");
		System.out.print(reply);
		System.out.print("\n");
		
		if(protocol.isApplicable(reply.getBytes()))
		{
			System.out.println("TRUE");
			protocol.interpret(reply, session);
		}
			
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
		protocol.onDisconnect(session);
	}
}
