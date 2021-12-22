package com.leetcode.labuladong.entity;

import java.util.LinkedHashMap;
import java.util.Map;

//使用LinkedHashMap实现
//LinkedHashMap底层就是用的【HashMap 加 双链表】实现的，而且本身已经实现了按照访问顺序的存储。
//此外，LinkedHashMap中本身就实现了一个方法removeEldestEntry用于判断是否需要移除最不常读取的数，
//方法默认是直接返回false，不会移除元素，所以需要重写该方法,即当缓存满后就移除最不常用的数。
public class LRUCache2 {
    private LinkedHashMap<Integer, Integer> cache;
    private int capacity;

    public LRUCache2(int capacity) {
        cache = new LinkedHashMap(capacity,0.75F,true){ // accessOrder = true;//排序方式 false 基于插入顺序  true  基于访问顺序
            protected boolean removeEldestEntry(Map.Entry eldest) {
                if (capacity + 1 == cache.size()) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        return cache.get(key);
    }

    public void put(int key, int val) {
        cache.put(key, val);
    }
}
