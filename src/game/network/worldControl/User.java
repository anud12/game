package game.network.worldControl;

import game.library.player.Player;
import game.network.component.Session;

public class User
{
	protected Session session;
	protected Player player;
	
	public User(Session session)
	{
		this.session = session;
	}
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	public Player getPlayer()
	{
		return player;
	}
	public void write(byte[] message)
	{
		session.write(message);
	}
	
}
