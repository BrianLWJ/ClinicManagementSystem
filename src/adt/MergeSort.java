/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author ThisPc
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T> {
    private ListInterface<T> list;
    
    private MergeSort() {
        list = new ArrayList<>();
    }
    
    public MergeSort(ListInterface<T> original) {
        this();
        for(var i: original) list.add(i);
    }

    @Override
    public ListInterface<T> sort() {
       return internalSort(false).list;
    }
    
        @Override
    public ListInterface<T> reversedSort() {
       return internalSort(true).list;
    }
    
    private MergeSort<T> internalSort(boolean reversed) {
        if(list.getNumberOfEntries() <= 1) return this;
        var split = Split();
        return split.getEntry(1).internalSort(reversed).Merge(split.getEntry(2).internalSort(reversed), reversed);
    }
    
    private ArrayList<MergeSort<T>> Split() {
        int halfSize = list.getNumberOfEntries() / 2;
        
        ArrayList<MergeSort<T>> split = new ArrayList<>();
        split.add(new MergeSort());
        split.add(new MergeSort());
        
        int index = 1;
        for(var i: list) {
            split.getEntry((index++ <= halfSize) ? 1 : 2).list.add(i);
        }
        
        return split;
    }
    
    private MergeSort<T> Merge(MergeSort<T> o, boolean reversed) {
        MergeSort<T> merge = new MergeSort();
        
        int leftIndex = 1, rightIndex = 1;
        while(leftIndex <= list.getNumberOfEntries() && rightIndex <= o.list.getNumberOfEntries()){
            T left = list.getEntry(leftIndex);
            T right = o.list.getEntry(rightIndex);

            int cmp = left.compareTo(right);
            boolean condition = reversed ? cmp < 0 : cmp > 0;
            
            if(condition) {
                merge.list.add(right);
                rightIndex++;
            }
            else{
                merge.list.add(left);
                leftIndex++;
            }
        }
        for(int i = leftIndex; i <= list.getNumberOfEntries(); i++) merge.list.add(list.getEntry(i));
        for(int i = rightIndex; i <= o.list.getNumberOfEntries(); i++) merge.list.add(o.list.getEntry(i));
        
        return merge;
    }
}
