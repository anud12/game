package game.network.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import game.network.Server;

public class Session implements Runnable	
{
	protected Server server;
	
	protected Socket socket;
	protected OutputStreamWriter textOut;
	protected BufferedReader textResponse;
	
	protected SocketAddress address;
	
	protected String reply;
	
	public Session(Socket socket, Server server) throws IOException
	{
		this.server = server;
		this.socket = socket;
		this.textOut = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
		this.textResponse = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ASCII"));
		
		this.address = socket.getRemoteSocketAddress();
		
		server.onConnect(this);	
		
		reply = new String();
	}
	
	protected synchronized String read() throws IOException
	{
		return textResponse.readLine();
	}
	
	@Override
	public void run() 
	{
		try 
		{
			write("Connected");			
			
			while(true)
			{				
				synchronized(reply)
				{
					reply = read();
				}
				
				server.onReply(this);
			}
		}
		catch(SocketException e)
		{
			
			server.onDisconnect(this);
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			server.onDisconnect(this);
		}
	}
	
	public String getReplyList()
	{
		synchronized(reply)
		{
			String returnValue = reply;
			reply = new String();
			return returnValue;
		}
		
	}
	
	public SocketAddress getAddress()
	{
		return address;
	}
	public Server getServer()
	{
		return server;
	}
	
	public void write(String string)
	{
		
		try 
		{
			string = string.replaceAll("\n", "");
			string = string.replaceAll("\r", "");
			textOut.write(string);
			textOut.write("\r\n");
			textOut.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			server.onDisconnect(this);
		}
		
	}
	
	
}
