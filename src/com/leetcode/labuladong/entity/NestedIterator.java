package com.leetcode.labuladong.entity;

/**
 * @description:
 * @projectName:code
 * @see:com.leetcode.labuladong.entity
 * @author:Lujw
 * @createTime:15:31 2021/12/31
 * @version:1.0
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 * <p>
 * // @return true if this NestedInteger holds a single integer, rather than a nested list.
 * public boolean isInteger();
 * <p>
 * // @return the single integer that this NestedInteger holds, if it holds a single integer
 * // Return null if this NestedInteger holds a nested list
 * public Integer getInteger();
 * <p>
 * // @return the nested list that this NestedInteger holds, if it holds a nested list
 * // Return empty list if this NestedInteger holds a single integer
 * public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    private LinkedList<NestedInteger> list;
    public NestedIterator(List<NestedInteger> nestedList) {
        list = new LinkedList(nestedList);
    }

    @Override
    public Integer next() {
        // hasNext方法保证了第一个元素一定是整数类型
        return list.removeFirst().getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!list.isEmpty() && !list.getFirst().isInteger()) {
            List<NestedInteger> first = list.removeFirst().getList();
            for (int i = first.size() - 1; i >= 0; i--) {
                list.addFirst(first.get(i));
            }
        }
        return !list.isEmpty();
    }
}

class NestedInteger {
    private Integer val;
    private List<NestedInteger> list;

    public NestedInteger(Integer val) {
        this.val = val;
        this.list = null;
    }

    public NestedInteger(List<NestedInteger> list) {
        this.list = list;
        this.val = null;
    }

    // 如果其中存的是一个整数，则返回true，否则返回false
    public boolean isInteger() {
        return val != null;
    }

    public Integer getInteger() {
        return this.val;
    }

    public List<NestedInteger> getList() {
        return this.list;
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
