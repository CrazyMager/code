package com.leetcode.labuladong.entity;

import java.util.HashMap;
import java.util.LinkedHashSet;

// 460. LFU 缓存
public class LFUCache {
    // key到val的映射，后面称作KV表
    private HashMap<Integer, Integer> keyToVal;
    // key到freq的映射，后面称为KF表
    private HashMap<Integer, Integer> keyToFreq;
    // freq到key列表的映射，后面称为FK表
    private HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;
    // 记录最小的频次
    private int minFreq;
    // 记录LFU缓存的最大容量
    private int cap;

    public LFUCache(int capacity) {
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKeys = new HashMap<>();
        this.minFreq = 0;
        this.cap = capacity;
    }

    private void increaseFreq(int key) {
         int freq = keyToFreq.get(key);
         keyToFreq.put(key, freq + 1);
         LinkedHashSet set = freqToKeys.get(freq);
         set.remove(key);
         if (!freqToKeys.containsKey(freq + 1)) {
             freqToKeys.put(freq + 1, new LinkedHashSet<Integer>());
         }
         freqToKeys.get(freq + 1).add(key);
         // 如果freq对应的列表空了，移除这个freq
        if (set.isEmpty()) {
            freqToKeys.remove(freq);
            // 如果这个freq恰好是minFreq，更新minFreq
            if (freq == minFreq) {
                this.minFreq++;
            }
        }
    }

    private void removeMinFreqKey() {
        // freq最小的key列表
        LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);
        // 其中最先插入的key就是该被淘汰的key
        int deletedKey = keyList.iterator().next();
        // 更新FK表
        keyList.remove(deletedKey);
        if (keyList.isEmpty()) {
            freqToKeys.remove(this.minFreq);
            // 这里需要更新minFreq吗？
        }
        // 更新KV表
        keyToVal.remove(deletedKey);
        // 更新KF表
        keyToFreq.remove(deletedKey);
    }

    public int get(int key) {
        if (!keyToVal.containsKey(key)) {
            return -1;
        }
        increaseFreq(key);
        return keyToVal.get(key);
    }

    public void put(int key, int value) {
        if (this.cap <= 0) {
            return;
        }
        // 若key已经存在，修改对应的val即可
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, value);
            // key对应的freq加一
            increaseFreq(key);
            return;
        }
        // key不存在，需要插入
        // 容量已满的话需要删除一个freq最小的key
        if (this.cap == keyToVal.size()) {
            removeMinFreqKey();
        }
        // 插入key和val，对应的freq为1
        // 插入KV表
        keyToVal.put(key, value);
        // 插入KF表
        keyToFreq.put(key, 1);
        // 插入FK表
        if (!freqToKeys.containsKey(1)) {
            freqToKeys.put(1, new LinkedHashSet<Integer>());
        }
        freqToKeys.get(1).add(key);
        // 插入新的key后最小的freq肯定是1
        this.minFreq = 1;
    }

}
