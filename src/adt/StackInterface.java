/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author ThisPc
 */
public interface StackInterface<T> {
    public void push(T entry);
    public T pop();
    public boolean isEmpty();
}
