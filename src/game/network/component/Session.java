package game.network.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import game.library.attribute.Attributes;
import game.network.Server;

public class Session implements Runnable	
{
	//Parent server to send the received messages
	protected Server server;
	
	//The socket used to communicate with the client
	protected Socket socket;
	
	// Streams //
	protected OutputStreamWriter textOut;
	protected OutputStream byteOut;
	protected InputStream inBufferStream;
	
	//List of messages
	protected List<Byte> message;
	
	//Attributes
	protected SocketAddress address;
	protected Attributes attributes;
	
	public Session(Socket socket, Server server) throws IOException
	{
		this.server = server;
		this.socket = socket;
		this.byteOut = socket.getOutputStream();
		this.textOut = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
		this.inBufferStream = socket.getInputStream();
		
		this.address = socket.getRemoteSocketAddress();
		this.attributes = new Attributes();
		server.onConnect(this);
		
		message = new LinkedList<Byte>();
	}
	
	protected synchronized void read() throws IOException
	{
		int readValue;
		boolean loop = false;
		
		//Read until it receives 0 or -1
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
			write("Connected\n");			
			
			while(true)
			{
				read();
				//Send to server the message
				server.onReply(this);
			}
		}
		catch(Exception e)
		{
			if(e.getMessage() != "Connection reset")
				e.printStackTrace();
			server.onDisconnect(this);
			try 
			{
				socket.close();
			} 
			catch (IOException e1) 
			{
			}
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
	public Attributes getAttributes()
	{
		return attributes;
	}
	//Send message to the client 
	public void write(String string)
	{
		write(string.getBytes());
	}
	public void write(byte[] message)
	{
		try 
		{
			byteOut.write(message);
			byteOut.write(0);
			byteOut.flush();
			
			System.out.println(message);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			server.onDisconnect(this);
		}
	}
	public OutputStream getStream()
	{
		try
		{
			return socket.getOutputStream();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
