package adt;
/**
 *
 * @author User
 */

import java.util.Iterator;

public class LinkedList<T> implements ListInterface<T> {
    private Node firstNode;
    private int numOfEntries;

    public LinkedList() {
        firstNode = null;
        numOfEntries = 0;
    }

    @Override
    public boolean add(T newEntry) {
        Node newNode = new Node(newEntry);

        if (isEmpty()) {
            firstNode = newNode;
        } else {
            Node currentNode = firstNode;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
        }

        numOfEntries++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= numOfEntries + 1)) {
            Node newNode = new Node(newEntry);

            if ((newPosition == 1)) { // insert at first
                newNode.next = firstNode;
                firstNode = newNode;
            } else {
                Node nodeBefore = firstNode;

                for (int i = 1; i < newPosition - 1; i++) {
                    nodeBefore = nodeBefore.next;
                }
                newNode.next = nodeBefore.next;
                nodeBefore.next = newNode;
            }
            numOfEntries++;
        } else {
            isSuccessful = false;
        }
        return isSuccessful;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numOfEntries)) {
            if (givenPosition == 1) {
                result = firstNode.data;
                firstNode = firstNode.next;
            } else {
                Node nodeBefore = firstNode;

                for (int i = 1; i < givenPosition - 1; ++i) {
                    nodeBefore = nodeBefore.next;
                }
                result = nodeBefore.next.data;
                nodeBefore.next = nodeBefore.next.next;
            }
            numOfEntries--;
        }
        return result;
    }

    @Override
    public void clear() {
        firstNode = null;
        numOfEntries = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((givenPosition >= 1) && (givenPosition <= numOfEntries)) {
            Node currentNode = firstNode;

            for (int i = 1; i < givenPosition; ++i) {
                currentNode = currentNode.next;
            }
            currentNode.data = newEntry;
        } else {
            isSuccessful = false;
        }
        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numOfEntries)) {
            Node currentNode = firstNode;

            for (int i = 1; i < givenPosition; ++i) {
                currentNode = currentNode.next;
            }
            result = currentNode.data;
        }
        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return found;
    }

    @Override
    public int getNumberOfEntries() {
        return numOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numOfEntries == 0;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public Iterator<T> getIterator() {
        return new LinkedListIterator();
    }

    @Override
    public Iterator<T> iterator() {
        return getIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node currentNode;

        public LinkedListIterator() {
            currentNode = firstNode; // start at the head
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements in the list");
            }
            T data = currentNode.data;
            currentNode = currentNode.next;
            return data;
        }
    }

    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
