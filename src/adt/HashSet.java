/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author ThisPc
 */
public class HashSet<T> implements SetInterface<T> {
    private static int DEFAULT_SIZE = 200;
    
    private ListInterface<T> entries;
    private T[] buffer;
    
    protected int GenerateIndex(Object entry){
        return Math.abs(entry.hashCode()) % buffer.length;
    }
    
    protected T Get(Object entry) {
        return buffer[GenerateIndex(entry)];
    }
    
    protected T GetFromIndex(int index) {
        return buffer[index];
    }
    
    protected void SetFromIndex(int index, T entry) {
        buffer[index] = entry;
    }
    
    public HashSet(){
        entries = new ArrayList<>();
        buffer = (T[]) new Object[DEFAULT_SIZE];
    }

    @Override
    public boolean add(Object entry) {
        int index = GenerateIndex(entry);
        for(int i = 0; i < buffer.length; i++) {
            if (buffer[index] == null) {
                buffer[index] = (T)entry;
                entries.add((T)entry);
                return true;
            }
            if(buffer[index].equals(entry)) {
                return true;
            }
            index = (index + 1) % buffer.length;
        }
        return false;
    }

    @Override
    public boolean contains(Object entry) {
        int index = GenerateIndex(entry);
        for(int i = 0; i < buffer.length; i++){
            if(buffer[index] == null) return false;
            if(buffer[index].equals(entry)) return true;
            index = (index + 1) % buffer.length;
        }
        return false;
    }
    
    private void RemoveFromEntries(Object entry) {
        for(int i = 1; i < entries.getNumberOfEntries(); i++) {
            if(entries.getEntry(i).equals(entry)) {
                entries.remove(i);
                break;
            }
        }
    }

    @Override
    public T remove(Object entry) {
        int index = GenerateIndex(entry);
        for(int i = 0; i < buffer.length; i++){
            if(buffer[index] == null) return null;
            if(buffer[index].equals(entry)){
                RemoveFromEntries(buffer[index]);
                buffer[index] = null;
                Rehash(index);
                return (T)entry;
            }
            index = (index + 1) % buffer.length;
        }
        return null;
    }
    
    public void Rehash(int index){
        while(buffer[++index] != null){
            add(buffer[index]);
            buffer[index] = null;
        }
    }

    @Override
    public Iterator getIterator() {
        return entries.getIterator();
    }

    @Override
    public int getNumberOfEntries() {
        return entries.getNumberOfEntries();
    }

    @Override
    public Iterator iterator() {
        return getIterator();
    }
    
}
