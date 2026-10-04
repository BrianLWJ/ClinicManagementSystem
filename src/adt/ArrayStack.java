/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.NoSuchElementException;

/**
 *
 * @author ThisPc
 */
public class ArrayStack<T> implements StackInterface<T> {
    private T[] entries;
    private int top;
    
    private static int DEFAULT_SIZE = 50;

    public ArrayStack() {
        entries = (T[]) new Object[DEFAULT_SIZE];
        top = 0;
    }
    
    @Override
    public void push(T entry) {
        if(top == entries.length) throw new OutOfMemoryError("ArrayStack is full.");
        entries[top++] = entry;
    }

    @Override
    public T pop() {
        if(top == 0) throw new NoSuchElementException("ArrayStack is empty.");
        return entries[--top];
    }

    @Override
    public boolean isEmpty() {
        return top == 0;
    }
    
}
