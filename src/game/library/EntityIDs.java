package game.library;

import java.util.HashSet;

public class EntityIDs 
{
	static protected HashSet<Integer> usedNumbers;
	
	
	static public boolean addID(int number)
	{
		if(usedNumbers == null)
		{
			usedNumbers = new HashSet<Integer>();
		}
		
		if(usedNumbers.contains(number))
		{
			return false;
		}
		
		usedNumbers.add(number);
		
		return true;
	}
	
}
