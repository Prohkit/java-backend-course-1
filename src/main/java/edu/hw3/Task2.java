package edu.hw3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Task2 {
    private static Stack<Character> bracketsStack = new Stack<>();
    private static List<String> clusters = new ArrayList<>();
    private static StringBuilder cluster = new StringBuilder();

    private Task2() {
    }

    public static List<String> clusterize(String sequenceOfBrackets) {
        char[] sequenceOfBracketsChars = sequenceOfBrackets.toCharArray();
        for (int i = 0; i < sequenceOfBrackets.length(); i++) {
            if (sequenceOfBracketsChars[i] == '(') {
                handleOpenBracket();
            } else if (bracketsStack.peek() == '(') {
                handleCloseBracket();
                if (bracketsStack.isEmpty()) {
                    createCluster();
                }
            }
        }
        if (bracketsStack.isEmpty()) {
            List<String> result = new ArrayList<>(clusters);
            clearState();
            return result;
        } else {
            clearState();
            throw new IllegalArgumentException("Sequence of brackets is incorrect");
        }
    }

    private static void handleOpenBracket() {
        bracketsStack.push('(');
        cluster.append('(');
    }

    private static void handleCloseBracket() {
        cluster.append(')');
        bracketsStack.pop();
    }

    private static void createCluster() {
            clusters.add(cluster.toString());
            cluster.delete(0, cluster.length());
    }

    private static void clearState() {
        bracketsStack.clear();
        clusters.clear();
        cluster.delete(0, cluster.length());
    }
}
