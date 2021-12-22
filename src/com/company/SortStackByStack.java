package com.company;

import java.util.Stack;

public class SortStackByStack {
    public static void sortStackByStack(Stack<Integer> stack) {
        Stack<Integer> help = new Stack<>();
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            if (help.isEmpty() || cur < help.peek()) {
                help.push(cur);
            } else {
                while (!help.isEmpty() && cur > help.peek()) {
                    stack.push(help.pop());
                }
                help.push(cur);
            }
        }
        while (!help.isEmpty()) {
            stack.push(help.pop());
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(1);
        stack.push(7);
        stack.push(2);
        stack.push(5);
        System.out.println(stack.toString());
        SortStackByStack.sortStackByStack(stack);
        System.out.println(stack.toString());
    }
}
