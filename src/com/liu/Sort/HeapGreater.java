package com.liu.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *加强堆
 * 重点是构建反向索引表
 */
public class HeapGreater<T> {
    private ArrayList<T> heap;
    private HashMap<T,Integer> indexMap;//反向索引表
    private int heapSize;
    private Comparator<? super T> comp;//比较器

    public HeapGreater(Comparator<T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }
    public boolean isEmpty() {
        return heapSize == 0;
    }
    public int size() {
        return heapSize;
    }
    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }
    public T peek() {
        return heap.get(0);
    }
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj,heapSize);
        heapInsert(heapSize++);
    }
    public T pop() {
        T ans = heap.get(0);
        //最后的位置和开头的交换
        //在反向索引表中删除掉ans
        //从0开始向下调整
        swap(0,heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }
    // 请返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }


    /**
     *删除堆上的任意一个元素
     * 方法就是用堆上最后一个位置上的元素替代那个要删除的元素
     * 然后让替代的那个元素向上或向下调整
     * 时间复杂度O(LogN)
     */
    public void remove(T obj) {
        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace) {
            heap.set(index, replace);//那最后的元素替换要删除的元素
            indexMap.put(replace,index);
            reign(replace);
        }
    }

    /**
     *系统不会帮你实现的功能
     * 给了你一个对象，内部元素如果发生了变化
     * 怎么把这个堆变得有序
     */
    public void reign(T obj) {
        heapInsert(indexMap.get(obj));//向上调整
        heapify(indexMap.get(obj));//向下调整
    }
    /**
     * 小根堆
     */
    private void heapInsert(int index) {
        while (comp.compare(heap.get(index),heap.get((index - 1)/2)) < 0) {
            swap(index,(index - 1)/2);
            index = (index - 1) /2;
        }
    }
    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            //选出最小的元素
            int smallest = left + 1 < heapSize && comp.compare(heap.get(left + 1),heap.get(left)) < 0 ? (left + 1) : left;
            //考虑父节点和左右节点谁的值更小
            // 然后把值最小的下标赋值给smallest
            smallest = comp.compare(heap.get(smallest),heap.get(index)) < 0 ? smallest : index;
            if (smallest == index) break;
            swap(smallest,index);
            index = smallest;//向下调整
            left = index * 2 + 1;
        }
    }
    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        //先换堆上的数
        heap.set(i,o2);
        heap.set(j,o1);
        //再换反向索引表的元素
        indexMap.put(o2,i);
        indexMap.put(o1,j);
    }
}
