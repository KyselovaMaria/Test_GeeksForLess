package org.example;

import java.util.List;
import java.util.Scanner;


public class App 
{
    public static void main(String[] args) {

        ConnectionToDb dbConnection = new ConnectionToDb();

        MathExpressionValidator val = new MathExpressionValidator();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an expression: ");
        String expression = scanner.nextLine();

        System.out.println("Amount of numbers in the equation: " + val.countNumbers(expression) );

        if (val.isValidExpression(expression) && val.isValidCharsAndSequence(expression)) {
            dbConnection.saveEquation(expression);
            System.out.println("The expression is valid.");

            System.out.print("Enter a root: ");
            double root = scanner.nextDouble();

            if (val.evaluateExpression(expression, root)) {
                dbConnection.updateEquationRoot(expression, root);
                System.out.println("The entered number is a root of the equation.");
            }
            else {
                System.out.println("The entered number is not a root of the equation.");
            }
        } else {
            System.out.println("The expression is invalid.");
        }


        System.out.print("Enter a root to search for equations: ");
        double searchRoot = scanner.nextDouble();

        List<String> equations = dbConnection.searchEquationsByRoot(searchRoot);
        if (!equations.isEmpty()) {
            System.out.println("Equations with the specified root were found in the database:");
            for (String equation : equations) {
                System.out.println(equation);
            }
        } else {
            System.out.println("No equations with the specified root were found in the database.");
        }

    }

}
