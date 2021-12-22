package com.company;

import java.util.Stack;

public class ReverseStack {
    public static int getAndRemoveLastElement(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = getAndRemoveLastElement(stack);
            stack.push(result);
            return last;
        }
    }

    public static void reverse(Stack<Integer> stack) {
        if(stack.isEmpty()) {
            return;
        } else {
            int i = getAndRemoveLastElement(stack);
            reverse(stack);
            stack.push(i);
        }
    }

    public static void main(String[] args) {
        // write your code here
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack.toString());
        ReverseStack.reverse(stack);
        System.out.println(stack.toString());
    }
}
