package game.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


@SuppressWarnings("serial")
public class NameCollection<E extends IHasName> extends HashSet<E>
{
	protected HashMap<String, E> elements = new HashMap<>();
	
	@Override
	public boolean add(E e)
	{
		elements.put(e.getName(), e);
		return super.add(e);
	}
	public boolean addAll(Collection<? extends E> c)
	{
		Iterator<? extends E> iterator = c.iterator();
		
		while(iterator.hasNext())
		{
			E e = iterator.next();
			
			elements.put(e.getName(), e);
		}
		return super.addAll(c);
	}
	public boolean remove(E o)
	{	
		
		elements.remove(o.getName());
		return super.remove(o);
		
	}
	
	public boolean removeAll(Collection<?> c)
	{
		@SuppressWarnings("unchecked")
		Iterator<E> iterator = (Iterator<E>) c.iterator();
		
		while(iterator.hasNext())
		{
			E e = iterator.next();
			
			elements.remove(e.getName());
		}
		return super.removeAll(c);
	}
	
	public boolean contains(String name)
	{
		return elements.containsKey(name);
	}
	public E get(String name)
	{
		return elements.get(name);
	}
	public void clear()
	{
		elements.clear();
		this.clear();
	}
}
