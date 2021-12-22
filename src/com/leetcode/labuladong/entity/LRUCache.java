package com.leetcode.labuladong.entity;

import java.util.HashMap;

public class LRUCache {
    private HashMap<Integer, Node> map;
    private DoubleList cache;
    // 最大容量
    private int cap;

    public LRUCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    // 将某个key提升为最近使用的
    private void makeRecently(int key) {
        Node cur = map.get(key);
        // 先从链表中删除这个结点
        cache.remove(cur);
        // 重新插到队尾
        cache.addLast(cur);
    }

    // 添加最近使用的元素
    private void addRencently(int key, int val) {
        Node x = new Node(key, val);
        map.put(key, x);
        cache.addLast(x);
    }

    // 删除某一个key
    public void deleteKey(int key) {
        Node x = map.remove(key);
        cache.remove(x);
    }

    // 删除最久未使用元素
    private void removeLeastRecently() {
        Node deleteNode = cache.removeFirst();
        map.remove(deleteNode.key);
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        makeRecently(key);
        return map.get(key).val;
    }

    public void put(int key, int val) {
        if (map.containsKey(key)) {
            map.get(key).val = val;
            makeRecently(key);
            return;
        }
        if (cache.size() == cap) {
            removeLeastRecently();
        }
        addRencently(key, val);
    }

}

class Node {
    public int key, val;
    public Node next, prev;
    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

class DoubleList {
    // 头尾虚节点
    private Node head, tail;
    // 链表元素数
    private int size;

    public DoubleList() {
        // 初始化双向链表的数据
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        this.head.next = tail;
        this.tail.prev = head;
        this.size = 0;
    }

    // 在链表尾部添加节点x，时间复杂度为O(1)
    public void addLast(Node x) {
        x.prev = tail.prev;
        x.next = tail;
        tail.prev.next = x;
        tail.prev = x;
        size++;
    }

    // 删除链表中的x结点（x一定存在）
    // 由于是双链表且给的是目标Node结点，时间复杂度为O(1)
    public void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
        size--;
    }

    // 删除链表中的第一个结点，并返回该节点，时间复杂度为O(1)
    public Node removeFirst() {
        if (head.next == tail) {
            return null;
        }
        Node first = head.next;
        remove(first);
        return first;
    }

    // 返回链表长度
    public int size() {
        return size;
    }
}
