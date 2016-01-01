package game.library.player;

import java.util.HashSet;

public class PlayerIDs 
{
	static protected HashSet<Integer> usedNumbers;
	
	
	static public void addID(int number)
	{
		if(usedNumbers == null)
		{
			usedNumbers = new HashSet<Integer>();
		}
		
		if(usedNumbers.contains(number))
		{
			return;
		}
		
		usedNumbers.add(number);
		
		return;
	}
	static public boolean isFree(int number)
	{
		if(usedNumbers == null)
		{
			usedNumbers = new HashSet<Integer>();
		}
		if(usedNumbers.contains(number))
		{
			return false;
		}
		return true;
	}
	
}
