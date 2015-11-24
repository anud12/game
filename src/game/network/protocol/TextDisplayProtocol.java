package game.network.protocol;

import java.io.UnsupportedEncodingException;

import game.network.component.Session;

public class TextDisplayProtocol extends Protocol{

	@Override
	public void interpret(byte[] array, Session session) {
		try 
		{
			System.out.print("-----------Reply-----------\n");
			System.out.print("From   :");
			System.out.print(session.getAddress());
			System.out.print("\n");
			System.out.print("Mesage :");
			String words[] = new String(array,"ASCII").split("\n");
			for(int i = 0 ; i < words.length ; i++)
			{
				if(i != 0)
					System.out.print("\t");
				System.out.println(words[i]);
			}
			
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onDisconnect(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isApplicable(byte[] array) {
		return true;
	}

}
