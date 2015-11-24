package game.network.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.network.Server;

public class Session implements Runnable	
{
	protected Server server;
	
	protected Socket socket;
	protected OutputStreamWriter textOut;
	protected OutputStream byteOut;
	protected InputStream inBufferStream;
	
	protected List<Byte> message;
	
	protected SocketAddress address;
		
	public Session(Socket socket, Server server) throws IOException
	{
		this.server = server;
		this.socket = socket;
		this.byteOut = socket.getOutputStream();
		this.textOut = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
		this.inBufferStream = socket.getInputStream();
		
		this.address = socket.getRemoteSocketAddress();
		
		server.onConnect(this);
		
		message = new LinkedList<Byte>();
	}
	
	protected synchronized void read() throws IOException
	{
		int readValue;
		boolean loop = false;
		do
		{
			loop = false;
			readValue = inBufferStream.read();
			
			if(readValue != 0 && readValue != -1)
			{
				loop = true;
				message.add((byte)(readValue));
				
			}
			
		}
		while(loop);
	}
	
	@Override
	public void run() 
	{
		try 
		{
			write("Connected");			
			
			while(true)
			{
				read();
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
	
	public synchronized byte[] getReply()
	{
		byte[] returnValue = new byte[message.size()];
		
		int i = 0;
		Iterator<Byte> iterator = message.iterator();
		while(iterator.hasNext())
		{
			returnValue[i] = iterator.next().byteValue();
			i++;
		}
		
		message.clear();
		return returnValue;
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
			textOut.write(string);
			
			textOut.flush();
			byteOut.write(0);
			byteOut.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			server.onDisconnect(this);
		}
		
	}
	
	
}
