package com.learning.eopi.stacks;

import java.util.Stack;

/**
 * Design a max stack that supports push, pop, top, peekMax and popMax.
 * <p>
 * push(x) -- Push element x onto stack.
 * pop() -- Remove the element on top of the stack and return it.
 * top() -- Get the element on the top.
 * max() -- Retrieve the maximum element in the stack.
 * <p>
 * Elements of Programming Interviews: Stacks: 9.1
 */
public class MaxStack {

    private Stack<Integer> stack;
    private Stack<MaxCount> maxCountStack;

    MaxStack() {
        this.stack = new Stack<>();
        this.maxCountStack = new Stack<>();
    }

    void push(int x) {
        stack.push(x);
        if (maxCountStack.empty()) {
            maxCountStack.push(new MaxCount(x, 1));
        } else {
            MaxCount maxCount = maxCountStack.peek();
            if (maxCount.val == x) {
                maxCountStack.pop();
                maxCountStack.push(new MaxCount(x, maxCount.count + 1));
            } else if (x > maxCount.val) {
                maxCountStack.push(new MaxCount(x, 1));
            }
        }
    }

    void pop() {
        int top = stack.pop();
        MaxCount maxCount = maxCountStack.peek();
        if (top == maxCount.val) {
            if (maxCount.count > 1) {
                maxCountStack.pop();
                maxCountStack.push(new MaxCount(maxCount.val, maxCount.count - 1));
            } else {
                maxCountStack.pop();
            }
        }
    }

    int top() {
        return stack.peek();
    }

    int getMax() {
        return maxCountStack.peek().val;
    }

    private class MaxCount {
        int val;
        int count;

        MaxCount(int val, int count) {
            this.val = val;
            this.count = count;
        }

        @Override
        public String toString() {
            return String.format("MaxStack: %s -> %s times", val, count);
        }
    }

    public static void main(String[] args) {
        MaxStack minStack = new MaxStack();
        minStack.push(2);
        minStack.push(2);
        minStack.push(1);
        minStack.push(4);
        minStack.push(5);
        minStack.push(5);
        minStack.push(3);
        minStack.pop();
        minStack.pop();
        minStack.pop();
        minStack.pop();
        minStack.push(0);
        minStack.push(3);

        System.out.println(minStack.stack);
        System.out.println(minStack.maxCountStack);
    }
}
