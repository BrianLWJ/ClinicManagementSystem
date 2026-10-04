/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author ThisPc
 */
public interface Sort<T extends Comparable<T>> {
    public ListInterface<T> sort();
    public ListInterface<T> reversedSort();
}
