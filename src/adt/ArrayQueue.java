/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author chinw
 */
import java.util.Iterator;

public class ArrayQueue<T> implements QueueInterface<T> {

    private T[] data;
    int front;
    int rear;
    int size;
    int capacity;

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    @Override
    public boolean isEmpty() { 
        return size == 0; 
    }
    
    @Override
    public boolean isFull() { 
        return size == capacity; 
    }

    @Override
    public void enqueue(T item) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot add more items.");
            return;
        }
        rear = (rear + 1) % capacity;
        data[rear] = item;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }
        T item = data[front];
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        int actualIndex = (front + index) % capacity;
        return data[actualIndex];
    }

    @Override
    public int getSize() { 
        return size; 
    }

    
    @Override
    public void replaceAt(int index, T newItem) {
        if (index < 0 || index >= size) return;
        int actualIndex = (front + index) % capacity;
        data[actualIndex] = newItem;
    }

    public void removeAt(int index) {
        if (index < 0 || index >= size) return;
        for (int i = index; i < size - 1; i++) {
            int from = (front + i + 1) % capacity;
            int to = (front + i) % capacity;
            data[to] = data[from];
        }
        rear = (rear - 1 + capacity) % capacity;
        size--;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            @Override
            public boolean hasNext() { return index < size; }
            @Override
            public T next() {
                T value = get(index);
                index++;
                return value;
            }
        };
    }
}
