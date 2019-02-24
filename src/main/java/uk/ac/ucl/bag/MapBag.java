package uk.ac.ucl.bag;

import java.util.HashMap;
import java.util.Iterator;

public class MapBag<T extends Comparable> extends AbstractBag<T>
{



    private int maxSize;
    HashMap<T, Integer> contents;

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
        this.contents = new HashMap<T, Integer>();
    }

    public void add(T value) throws BagException
    {
        for (T KeyValue: contents.keySet()){

            if (KeyValue.compareTo(value) == 0) // Must use compareTo to compare values.
            {
                contents.replace(KeyValue, contents.get(KeyValue) + 1);
                return;
            }
        }
        if (contents.size() < maxSize)
        {
            contents.put(value, 1);
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
        for (T KeyValue: contents.keySet())
        {
            if (KeyValue.compareTo(value) == 0)
            {
                return true;
            }
        }
        return false;
    }

    public int countOf(T value)
    {
        for (T KeyValue: contents.keySet())
        {
            if (KeyValue.compareTo(value) == 0)
            {
                return contents.get(KeyValue);
            }
        }
        return 0;
    }

    public void remove(T value)
    {
        for (T KeyValue: contents.keySet()){

            if (KeyValue.compareTo(value) == 0) // Must use compareTo to compare values.
            {
                contents.replace(KeyValue, contents.get(KeyValue) - 1);

                if (contents.get(KeyValue) == 0)
                {
                    contents.remove(KeyValue);
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

        Iterator iterator = contents.keySet().iterator();

        public boolean hasNext()
        {
            if (iterator.hasNext()) return true;
            return false;
        }

        public T next()
        {
            return (T) iterator.next();

        }
    }

    public Iterator<T> iterator()
    {
        return new MapBagUniqueIterator();
    }

    private class MapBagIterator implements Iterator<T>
    {
        Iterator iterator = contents.keySet().iterator();
        private int count = 1; //Counting Occurrences
        private boolean check = false; //True if program has checked if Key has several occurrences and it does
        private T Key;

        public boolean hasNext()
        {
            if (check == true){ //Has occurrences to iterate through
                if (count <= contents.get(Key)) {
                    return true;
                }
                check = false; //Iterated through all the occurrences
            }

            if (check == false){

                if (iterator.hasNext()){
                    count = 1; //First occurrence
                    return true;
                }
            }

            return false;
        }

        public T next()
        {
            if (count == 1){ //First occurrence so get next Key
                Key = (T) iterator.next();

            }

            if (count < contents.get(Key) || (count == contents.get(Key) && count != 1)) //Separates the keys which have more than one occurrence
            {
                check = true;
                count++;
            }
            else { //Only had one occurrence
                check = false;
            }

            return Key;
        }
    }

    public Iterator<T> allOccurrencesIterator()
    {
        return new MapBagIterator();
    }
}
