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

public interface QueueInterface<T> {
    void enqueue(T item);
    T dequeue();
    boolean isEmpty();
    boolean isFull();
    T get(int index);
    int getSize();
    Iterator<T> iterator();
    void replaceAt(int index, T newItem);
    void removeAt(int index);
}
