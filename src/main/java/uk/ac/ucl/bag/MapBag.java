package uk.ac.ucl.bag;

import java.util.HashMap;
import java.util.Iterator;

public class MapBag<T extends Comparable> extends AbstractBag<T>
{

    private static class Element<E extends Comparable>
    {

        public int count;
        public E value;
        public Element(int count, E value)
        {
            this.count = count;
            this.value = value;
        }
    }

    private int maxSize;
    HashMap<Integer, Element<T>> contents;

    public MapBag() throws BagException
    {
        this(MAX_SIZE);
    }

    public MapBag(int maxSize) throws BagException
    {
        if (maxSize > MAX_SIZE)
        {
            throw new BagException("Attempting to create a Bag with size greater than maximum");
        }
        if (maxSize < 1)
        {
            throw new BagException("Attempting to create a Bag with size less than 1");
        }
        this.maxSize = maxSize;
        this.contents = new HashMap<Integer, Element<T>>();
    }

    public void add(T value) throws BagException
    {
        for (Element element: contents.values()){

            if (element.value.compareTo(value) == 0) // Must use compareTo to compare values.
            {
                element.count ++;
                return;
            }
        }
        if (contents.size() < maxSize)
        {
            contents.put(contents.size(), new Element(1, value));
        }
        else
        {
            throw new BagException("Bag is full");
        }
    }

    public void addWithOccurrences(T value, int occurrences) throws BagException
    {
        for (int i = 0 ; i < occurrences ; i++)
        {
            add(value);
        }
    }

    public boolean contains(T value)
    {
        for (Element element : contents.values())
        {
            if (element.value.compareTo(value) == 0)
            {
                return true;
            }
        }
        return false;
    }

    public int countOf(T value)
    {
        for (Element element : contents.values())
        {
            if (element.value.compareTo(value) == 0)
            {
                return element.count;
            }
        }
        return 0;
    }

    public void remove(T value)
    {
        for (Element element : contents.values())
        {
            if (element.value.compareTo(value) == 0)
            {
                element.count --;

                if (element.count == 0)
                {
                    contents.remove(element);
                    return;
                }
            }
        }
    }

    public boolean isEmpty()
    {
        return contents.size() == 0;
    }

    public int size()
    {
        return contents.size();
    }

    private class MapBagUniqueIterator implements Iterator<T>
    {
        private int index = 0;

        public boolean hasNext()
        {
            if (index < contents.size()) return true;
            return false;
        }

        public T next()
        {
            return contents.get(index++).value;
        }
    }

    public Iterator<T> iterator()
    {
        return new MapBagUniqueIterator();
    }

    private class MapBagIterator implements Iterator<T>
    {
        private int index = 0;
        private int count = 0;

        public boolean hasNext()
        {
            if (index < contents.size()) {
                if (count < contents.get(index).count) return true;
                if ((count == contents.get(index).count) && ((index + 1) < contents.size())) return true;
            }
            return false;
        }

        public T next()
        {
            if (count < contents.get(index).count)
            {
                T value = contents.get(index).value;
                count++;
                return value;
            }
            count = 1;
            index++;
            return contents.get(index).value;
        }
    }

    public Iterator<T> allOccurrencesIterator()
    {
        return new MapBagIterator();
    }
}
