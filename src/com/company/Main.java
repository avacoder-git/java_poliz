package com.company;

import java.util.Scanner;
import java.util.Stack;
import java.lang.Math;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        String string = input.nextLine();

        string = getClearString(string);
        string = replaceExponentations(string);
//        double answer = getAnswer(string);
//        System.out.println(string);
        string = toPoliz(string);
        System.out.println(string);
//        System.out.println(answer);


    }

    private static int getPriority(char belgi) {
        if (belgi == '^') return 4;
        else if (belgi == '*' || belgi == '/') return 3;
        else if (belgi == '+' || belgi == '-') return 2;
        else if (belgi == '(') return 1;
        else if (belgi == ')') return -1;
        else return 0;
    }


    private static String getClearString(String input) {
        String result = new String();

        for (int i = 0; i < input.length(); i++) {
            char charecter = input.charAt(i);
            if (charecter == '-' && i == 0) {
                result += '0';

            } else if (i != 1  && i != 0 && input.charAt(i - 1) == '(') {
                result += '0';
            }
            result += charecter;
        }

        return result;

    }

    private static double getAnswer(String poliz) {
        String operand = new String();
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < poliz.length(); i++) {
            if (poliz.charAt(i) == ' ') continue;

            if (getPriority(poliz.charAt(i)) == 0) {
                while (poliz.charAt(i) != ' ' && getPriority(poliz.charAt(i)) == 0) {
                    operand += poliz.charAt(i++);
                    if (i == poliz.length()) break;
                }
                stack.push(Double.parseDouble(operand));
                operand = new String();
            }

            if (getPriority(poliz.charAt(i)) > 1) {
                double a = stack.pop(), b = stack.size() > 0 ? stack.pop() : 0;
                if (poliz.charAt(i) == '^') stack.push(Math.pow(b, a));
                if (poliz.charAt(i) == '+') stack.push(b + a);
                if (poliz.charAt(i) == '-') stack.push(b - a);
                if (poliz.charAt(i) == '*') stack.push(b * a);
                if (poliz.charAt(i) == '/') stack.push(b / a);
            }
        }


        return stack.pop();

    }


    public static String toPoliz(String input) throws Exception {
        String current = "";
        Stack<Character> stack = new Stack<>();

        int priority;
        int item2;
        char item;
        String string = "";
        int openedBracked = 0;
        int closedBracked = 0;
        Boolean isOpenedBracked = false;
        String arr2 = "";
        for (int i = 0; i < input.length(); i++) {
            priority = getPriority(input.charAt(i));
            item2 = i + 1 < input.length() ? input.charAt(i + 1) : ' ';




            if (priority == 0)
            {
                current += input.charAt(i);
                string += input.charAt(i);
            }

            if (priority == 1)
            {
                openedBracked++;
                isOpenedBracked = true;
                stack.push(input.charAt(i));
            }

            if (priority > 1 && priority < 4) {
                current += ' ';
                string += ' ';

                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority)
                    {
                        item = stack.pop();
                        current += item;
                        string += item;
                    }
                    else break;
                }
                if (closedBracked == openedBracked && !isOpenedBracked) string = "";
                stack.push(input.charAt(i));
            }
            if (priority == 4) {
                current += ' ';

                current = removeSubstring(current, current.length() - string.length()-1, current.length());
                if (item2 != '-') {
                    arr2 = string;
                    for (int w=1; w < Character.getNumericValue(item2); w++){

                        arr2 += " " + string + "*" ;

                    }
                    current += arr2;
                    arr2 = "";
                } else
                {
                    current += "1 ";
                    stack.push('/');

                    arr2 = string;
                    for (int w =1; w < Character.getNumericValue(input.charAt(i+2)); w++){
                        arr2 += ' ' + string + "*";
                    }
                    current += arr2;
                    arr2 = "";
                    input = removeCharFromStringByIndex(input, i + 2);
                }
                input = removeCharFromStringByIndex(input, i + 1);

            }

            if (priority == -1) {
                current += ' ';
                string += ' ';

                closedBracked++;
                while (getPriority(stack.peek()) != 1){
                    item = stack.pop();
                    current += item;
                    string += item;
                }
                isOpenedBracked = false;
                stack.pop();
            }
        }
        while (!stack.empty()) {
            item = stack.pop();
            current += item;
            string += item;
        }


        return current;
    }

    private static String removeCharFromStringByIndex(String input, int i) {
        return input.substring(0, i) + input.substring(i + 1);
    }

    private static String replaceExponentationByIndex(String input, int i) {
        return input.substring(0, i) + "*10^" + input.substring(i + 1);
    }

    private static String replaceExponentations(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'e') {
                input = replaceExponentationByIndex(input, i);
            }
        }
        return input;
    }
    static String removeSubstring(String text, int startIndex, int endIndex) {
        if (endIndex < startIndex) {
            startIndex = endIndex;
        }

        String a = text.substring(0, startIndex);
        String b = text.substring(endIndex);

        return a + b;
    }
}
