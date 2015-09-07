package com.calculator.mrgreat.calculator;

import android.util.Log;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by Mr.Great on 9/5/2015.
 */
public class CalculateExpression {

    private static final String OPERATORS = "()+-*÷";

    protected static String convertToPolishNotation(String infix) {
        try {
            String postfix = "";
            boolean negative = true;
            Stack<String> stack = new Stack<>();
            String standard = convertToStandardExpression(infix);
            //Log.e("standard", stdd);
            StringTokenizer stringTokenizer = new StringTokenizer(standard, OPERATORS, true);
            int count = stringTokenizer.countTokens();
            Log.e("count", "" + count);
            while (stringTokenizer.hasMoreTokens()) {
                String token = stringTokenizer.nextToken().trim();
                String op;
                switch (token) {
                    case "(":
                        stack.push(token);
                        break;
                    case ")":
                        while (!(op = stack.pop()).equals("(")) {
                            postfix += " " + op;

                        }
                        break;
                    case "+":
                    case "-":
                    case "*":
                    case "÷":
                        if (negative) {
                            token = "n" + token;
                            stack.push(token);

                        } else {
                            int p = Priority(token);
                            while (!stack.isEmpty() && !stack.peek().equals("(") && (Priority(stack.peek()) >= p)) {
                                op = stack.pop();
                                postfix += " " + op;

                            }
                            stack.push(token);

                        }
                        negative = true;
                        break;
                    default:
                        postfix += " " + token;
                        negative = false;
                        break;

                }

            }

            while (!stack.isEmpty()) {
                String op = stack.pop();
                postfix += " " + op;

            }

            Log.e("postfox", postfix);
            return postfix;
        } catch (EmptyStackException | NumberFormatException e) {
            throw new ExpressionFormatException();

        }

    }

    protected static String calculatePolishNotation(String postfix) {
        try {
            Stack<Integer> stack = new Stack<>();
            StringTokenizer stringTokenizer = new StringTokenizer(postfix);
            while (stringTokenizer.hasMoreTokens()) {
                String token = stringTokenizer.nextToken();
                if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("÷") || token.equals("n-") || token.equals("n+")) {
                    Calculate(token, stack);

                } else {
                    //Log.d("token", "" + token);
                    stack.push(Integer.valueOf(token));

                }

            }

            int result = stack.pop();
            if (!stack.isEmpty()) {
                throw new ExpressionFormatException();

            }

            return Integer.toString(result);
        } catch (EmptyStackException | NumberFormatException e) {
            throw new ExpressionFormatException();

        }

    }

    private static void Calculate(String operator, Stack<Integer> s) {
        int num1 = s.pop();
        switch (operator) {
            case "n-":
                s.push(-num1);
                break;
            case "n+":
                s.push(num1);
                break;
            default:
                int num2 = s.pop();
                int result;
                switch (operator) {
                    case "+":
                        result = num2 + num1;
                        break;
                    case "-":
                        result = num2 - num1;
                        break;
                    case "*":
                        result = num2 * num1;
                        break;
                    case "÷":
                        result = num2 / num1;
                        break;
                    default:
                        return;

                }
                s.push(result);
                break;

        }

    }

    private static String convertToStandardExpression(String expression) {
        StringTokenizer strT = new StringTokenizer(expression, OPERATORS, true);
        Stack<String> sstack = new Stack<>();
        String standard = "";

        while (strT.hasMoreTokens()) {
            String token2 = strT.nextToken().trim();

            if (!sstack.isEmpty()) {
                String token1 = sstack.pop();

                if (!token1.equals("(") && !token1.equals(")") && !token1.equals("+") && !token1.equals("-") && !token1.equals("*") && !token1.equals("÷") && token2.equals("(")) {
                    token2 = "*" + token2;

                }
                if (!token2.equals("(") && !token2.equals(")") && !token2.equals("+") && !token2.equals("-") && !token2.equals("*") && !token2.equals("÷") && token1.equals(")")) {
                    token1 = token1 + "*";

                }

                standard += token1;

            }
            sstack.push(token2);

        }
        String last = sstack.pop();
        standard += last;

        return standard;

    }

    private static int Priority(String operator) {
        switch (operator) {
            case "n-":
            case "n+":
                return 2;
            case "*":
            case "÷":
                return 1;
            case "+":
            case "-":
                return 0;
            default:
                return -1;
        }

    }

}
