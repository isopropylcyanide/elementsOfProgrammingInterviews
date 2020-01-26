package com.learning.eopi.stacks;

import java.util.Stack;

/**
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * <p>
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMax() -- Retrieve the minimum element in the stack.
 *
 * @link https://leetcode.com/problems/min-stack/
 */
public class MinStack {

    private Stack<Integer> stack;
    private Stack<MinCount> minCountStack;

    MinStack() {
        this.stack = new Stack<>();
        this.minCountStack = new Stack<>();
    }

    void push(int x) {
        stack.push(x);
        if (minCountStack.empty()) {
            minCountStack.push(new MinCount(x, 1));
        } else {
            MinCount minSoFar = minCountStack.peek();
            if (minSoFar.val == x) {
                minCountStack.pop();
                minCountStack.push(new MinCount(x, minSoFar.count + 1));
            } else if (x < minSoFar.val) {
                minCountStack.push(new MinCount(x, 1));
            }
        }
    }

    void pop() {
        int top = stack.pop();
        MinCount minSoFar = minCountStack.peek();
        if (top == minSoFar.val) {
            if (minSoFar.count > 1) {
                minCountStack.pop();
                minCountStack.push(new MinCount(minSoFar.val, minSoFar.count - 1));
            } else {
                minCountStack.pop();
            }
        }
    }

    int top() {
        return stack.peek();
    }

    int getMin() {
        return minCountStack.peek().val;
    }

    private class MinCount {
        int val;
        int count;

        MinCount(int val, int count) {
            this.val = val;
            this.count = count;
        }

        @Override
        public String toString() {
            return String.format("MinStack: %s -> %s times", val, count);
        }
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(-3);
        minStack.push(0);
        minStack.push(4);
        minStack.push(-5);
        minStack.push(-2);
        minStack.push(1);
        minStack.pop();
        minStack.pop();
        minStack.push(0);
        minStack.push(-13);

        System.out.println(minStack.stack);
        System.out.println(minStack.minCountStack);
    }
}
