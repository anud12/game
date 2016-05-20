package game.library.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

import game.library.entity.Entity;

public class EntityPositionContainer<E extends Entity> implements Collection<E>
{
	//Comparator used for ascending ordering
	private class xAxisComparator<T extends Entity> implements Comparator<T>
	{
		@Override
		public int compare(T o1, T o2)
		{
			if(o1.getCenter().x > o2.getCenter().x)
			{
				return 1;
			}
			if(o1.getCenter().x < o2.getCenter().x)
			{
				return -1;
			}
			return 0;
		}
	}
	//Comparator used for descending ordering
	private class yAxisComparator<T extends Entity> implements Comparator<T>
	{
		@Override
		public int compare(T o1, T o2)
		{
			if(o1.getCenter().y > o2.getCenter().y)
			{
				return 1;
			}
			if(o1.getCenter().y < o2.getCenter().y)
			{
				return -1;
			}
			return 0;
		}
	}
	
	//Containers 
	protected ArrayList<E> xAxis;
	protected ArrayList<E> yAxis;
	
	//Comparators
	protected xAxisComparator<E> xComparator;
	protected yAxisComparator<E> yComparator;
	
	public EntityPositionContainer()
	{
		xAxis = new ArrayList<E>();
		yAxis = new ArrayList<E>();
		
		xComparator = new xAxisComparator<E>();
		yComparator = new yAxisComparator<E>();
	}
	public int indexOf(E e)
	{
		return Collections.binarySearch(xAxis, e, new xAxisComparator<E>());
	}
	public int indexOfY(E e)
	{
		return Collections.binarySearch(yAxis, e, new yAxisComparator<E>());
	}
	
	public E get(int index)
	{
		return xAxis.get(index);
	}
	public E getY(int index)
	{
		return yAxis.get(index);
	}
	
	public void sort()
	{
		xAxis.sort(xComparator);
		yAxis.sort(yComparator);
	}
	@Override
	public boolean add(E e)
	{
		xAxis.add(e);
		yAxis.add(e);
		
		sort();
		return true;
	}
	@Override
	public boolean addAll(Collection<? extends E> arg0)
	{
		xAxis.addAll(arg0);
		yAxis.addAll(arg0);
		
		sort();
		return true;
	}
	@Override
	public void clear()
	{
		xAxis.clear();
		yAxis.clear();
	}
	@Override
	public boolean contains(Object arg0)
	{
		return xAxis.contains(arg0);
	}
	@Override
	public boolean containsAll(Collection<?> arg0)
	{
		return xAxis.containsAll(arg0);
	}
	@Override
	public boolean isEmpty()
	{
		return xAxis.isEmpty();
	}
	@Override
	public ListIterator<E> iterator()
	{
		return (ListIterator<E>) xAxis.iterator();
	}
	public ListIterator<E> iteratorY()
	{
		return (ListIterator<E>) yAxis.iterator();
	}
	@Override
	public boolean remove(Object arg0)
	{
		xAxis.remove(arg0);
		yAxis.remove(arg0);
		
		sort();
		return true;
	}
	@Override
	public boolean removeAll(Collection<?> arg0)
	{
		xAxis.removeAll(arg0);
		yAxis.removeAll(arg0);
		
		sort();
		return true;
	}
	@Override
	public boolean retainAll(Collection<?> arg0)
	{
		xAxis.retainAll(arg0);
		yAxis.retainAll(arg0);
		
		sort();
		return true;
	}
	@Override
	public int size()
	{
		return xAxis.size();
	}
	@Override
	public Object[] toArray()
	{
		return xAxis.toArray();
	}
	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return xAxis.toArray(arg0);
	}
}
