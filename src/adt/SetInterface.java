/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author ThisPc
 */
public interface SetInterface<T> extends Iterable<T> {
    public boolean add(T entry);
    public boolean contains(T entry);
    public T remove(T entry);
    public Iterator<T> getIterator();
    public int getNumberOfEntries();
}
