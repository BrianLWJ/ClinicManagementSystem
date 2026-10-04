package adt;

import java.util.Iterator;

public class ArrayList<T> implements ListInterface<T> {
    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 50;

    public ArrayList() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        numberOfEntries = 0;
    }

    @Override
    public boolean add(T newEntry) {
        if (isFull()) {
            return false;
        }
        array[numberOfEntries] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        if (isFull() || newPosition < 1 || newPosition > numberOfEntries + 1) {
            return false;
        }
        for (int i = numberOfEntries; i >= newPosition; i--) {
            array[i] = array[i - 1];
        }
        array[newPosition - 1] = newEntry;
        numberOfEntries++;
        return true;
    }

    @Override
    public T remove(int givenPosition) {
        if (givenPosition < 1 || givenPosition > numberOfEntries) {
            return null;
        }
        T removedItem = array[givenPosition - 1];
        for (int i = givenPosition - 1; i < numberOfEntries - 1; i++) {
            array[i] = array[i + 1];
        }
        array[numberOfEntries - 1] = null;
        numberOfEntries--;
        return removedItem;
    }

    @Override
    public void clear() {
        for (int i = 0; i < numberOfEntries; i++) {
            array[i] = null;
        }
        numberOfEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        if (givenPosition < 1 || givenPosition > numberOfEntries) {
            return false;
        }
        array[givenPosition - 1] = newEntry;
        return true;
    }

    @Override
    public T getEntry(int givenPosition) {
        if (givenPosition < 1 || givenPosition > numberOfEntries) {
            return null;
        }
        return array[givenPosition - 1];
    }

    @Override
    public boolean contains(T anEntry) {
        for (int i = 0; i < numberOfEntries; i++) {
            if (array[i].equals(anEntry)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return numberOfEntries == array.length;
    }

    @Override
    public Iterator<T> getIterator(){
        return new ArrayListIterator();
    }

    @Override
    public Iterator<T> iterator() {
        return getIterator();
    }
    
    private class ArrayListIterator implements Iterator<T>{
        int nextIndex;
        public ArrayListIterator(){
            nextIndex = 0;
        }
        @Override
        public boolean hasNext(){
            return nextIndex<numberOfEntries;
        }  
        @Override
        public T next(){
            if(hasNext()){
                T setData =(T) array[nextIndex++];
                return setData;
            }else{
                return null;
            }
        }
    }
}
