package com.leetcode.labuladong.entity;

/**
 * @description:
 * @projectName:code
 * @see:com.leetcode.labuladong.entity
 * @author:Lujw
 * @createTime:16:20 2021/12/24
 * @version:1.0
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int x) {
        this.val = x;
    }
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
