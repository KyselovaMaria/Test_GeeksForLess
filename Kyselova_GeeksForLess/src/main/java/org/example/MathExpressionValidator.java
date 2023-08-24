package org.example;

import java.util.Stack;
import static org.example.Eval.eval;


public class MathExpressionValidator {

    public static boolean isValidExpression(String expression) {
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static boolean isValidCharsAndSequence(String expression) {
        String allowedCharacters = "0123456789.+-*/=()X"; // Only symbols are allowed

        for (char ch : expression.toCharArray()) {
            if (allowedCharacters.indexOf(ch) == -1) {
                return false;
            }
        }


        int equalCount = 0;
        for (char ch : expression.toCharArray()) {
            if (ch == '=') {
                equalCount++;
                }
            }
        if (equalCount != 1) {
            return false;
        }


        String operators = "+-*/";
        for (int i = 0; i < expression.length() - 1; i++) {
            char curr = expression.charAt(i);
            char next = expression.charAt(i + 1);
            String operatorSequence = Character.toString(curr) + Character.toString(next);
            if (operators.contains(Character.toString(curr)) && operators.contains(Character.toString(next))
                    && !(operatorSequence.equals("*-") || operatorSequence.equals("/-") || operatorSequence.equals("=-"))) {
                return false; // Two operators in a row
            }
        }

        return true;
    }

    public static boolean evaluateExpression(String expression, double root) {
        String substitutedExpression = expression.replace("X", Double.toString(root));
        String[] sides = substitutedExpression.split("=");

        double leftValue = eval(sides[0]);
        double rightValue = eval(sides[1]);

        return Math.abs(leftValue - rightValue) < 1e-9;
    }


    public static int countNumbers(String expression) {
        int count = 0;
        boolean inNumber = false;

        for (char ch : expression.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.') {
                if (!inNumber) {
                    inNumber = true;
                    count++;
                }
            } else {
                inNumber = false;
            }
        }

        return count;
    }
}

