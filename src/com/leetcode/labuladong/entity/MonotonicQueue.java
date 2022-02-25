package com.leetcode.labuladong.entity;

import java.util.LinkedList;

/**
 * @description:单调队列
 * @projectName:code
 * @see:com.leetcode.labuladong.entity
 * @author:Lujw
 * @createTime:14:15 2021/12/24
 * @version:1.0
 */
public class MonotonicQueue {
    LinkedList<Integer> q = new LinkedList<>();

    public void push(int n) {
        while (!q.isEmpty() && q.getLast() < n) {
            q.pollLast();
        }
        q.add(n);
    }

    public int max() {
        return q.getFirst();
    }

    public void pop(int n) {
        if (q.getFirst() == n) {
            q.pollFirst();
        }
    }
}
