package uk.ac.ucl.bag;

import java.util.Iterator;

public class LinkedListBag<T extends Comparable> extends AbstractBag<T> {
    private static class Element<E> {
        public E value;
        public int occurrences;
        public Element<E> next;

        public Element(E value, int occurrences, Element<E> next) {
            this.value = value;
            this.occurrences = occurrences;
            this.next = next;
        }
    }

    private int maxSize;
    private Element<T> head;
    private int listSize;


    public LinkedListBag() throws BagException {
        this(MAX_SIZE);
    }

    public LinkedListBag(int maxSize) throws BagException {
        if (maxSize > MAX_SIZE) {
            throw new BagException("Attempting to create a Bag with size greater than maximum");
        }
        if (maxSize < 1) {
            throw new BagException("Attempting to create a Bag with size less than 1");
        }
        this.maxSize = maxSize;
        this.head = null;
        this.listSize = 0;


    }

    public void add(T value) throws BagException {

        if (this.head == null) {
            this.head = new Element(value, 1, null);
            this.listSize ++;
            return;
        }
        Element<T> currentNode = this.head;

        while (currentNode != null)
            {
                if (currentNode.value.equals(value)) // Must use compareTo to compare values.
                {
                    currentNode.occurrences++;
                    return;
                }
                currentNode = currentNode.next;
            }

        if (this.listSize < maxSize) {
            Element<T> newNode = new Element(value, 1, null);
            Element<T> endNode = this.head;

            while (endNode.next != null) {
                endNode = endNode.next;
            }

            endNode.next = newNode;

            this.listSize ++;

        } else {
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
        Element<T> currentNode = this.head;

        while (currentNode != null ){

            if (currentNode.value.equals(value))
            {
                return true;
            }
        }
        return false;
    }

    public int countOf(T value)
    {
        Element<T> currentNode = this.head;

        while (currentNode != null)
        {
            if (currentNode.value.equals(value))
            {
                return currentNode.occurrences;
            }
        }
        return 0;
    }

    public void remove(T value)
    {
        Element<T> currentNode = this.head;
        Element<T> previousNode = this.head;
        while (currentNode != null){
            if (currentNode.value.equals(value))
            {
                currentNode.occurrences --;
                this.listSize--;
                if (currentNode.equals(this.head) && this.head.occurrences == 0){
                    this.head = currentNode.next;
                    return;
                }

                if (currentNode.occurrences == 0){
                    // Remove Node
                    previousNode.next = currentNode.next;
                    return;
                }
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
    }

    public boolean isEmpty()
    {
        return this.listSize == 0;
    }

    public int size()
    {
        return this.listSize;
    }

    private class LinkedListBagUniqueIterator implements Iterator<T> {
        private int index = 0;


        public boolean hasNext() {
            if (index < listSize) return true;
            return false;
        }

        public T next() {
            Element<T> currentNode = head;
            for (int i = 0; i < index; i++){
                currentNode = currentNode.next;
            }
            index++;

            return currentNode.value;

        }

    }

    public Iterator<T> iterator()
    {
        return new LinkedListBagUniqueIterator();
    }

    /*
      This class implements an additional iterator that returns all values in a bag including a value for each copy.
      It is also a nested inner class.
     */
    private class LinkedListBagIterator implements Iterator<T>
    {
        private int index = 0;
        private int count = 0;

        public boolean hasNext()
        {
            Element<T> currentNode = head;

            if (index < listSize) {
                for (int i = 0; i < index; i++){
                    currentNode = currentNode.next;
                }

                if (count < currentNode.occurrences) return true;
                if ((count == currentNode.occurrences) && ((index + 1) < listSize)) return true;
            }
            return false;
        }

        public T next()
        {
            Element<T> currentNode = head;
            for (int i = 0; i < index; i++){
                currentNode = currentNode.next;
            }

            if (count < currentNode.occurrences)
            {
                T value = currentNode.value;
                count++;
                return value;
            }
            count = 1;
            index++;
            currentNode = currentNode.next;
            T value = currentNode.value;
            System.out.println(value);
            return currentNode.value;
        }
    }

    public Iterator<T> allOccurrencesIterator()
    {
        return new LinkedListBagIterator();
    }
}

