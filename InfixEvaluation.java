/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author alexgeronimo
 */
public class InfixEvaluation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner input = checkFile("hw5_input.txt");
  
        while(input.hasNextLine()) {
            String expression = input.nextLine();
            System.out.println(expression+"="+evaluate(expression));
                
        } 

    }
    //************** End Main *******************************
    
    /**
     * 
     * @param file
     * @return scanner input or null if file not found 
     */
    public static Scanner checkFile(String file){
        Scanner input;
        try {
            input = new Scanner(new File(file));
            return input;
        } 
        catch (FileNotFoundException ex){
            ex.printStackTrace();
            return null;
        }
        
    }
    
    /**
     * Reads first line of .txt - returns integer value 
     * @param in_file
     * @return int first row
     */
    public static int getLines(Scanner in_file){
        int num_lines = in_file.nextInt();
        return num_lines;
    }
    
    // These methods were modified from http://www.geeksforgeeks.org/expression-evaluation/
    
    /**
     * 
     * @param expression
     * @return value of full expression
     */
    public static int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();
 
         // Stack for numbers: 'values'
        LinkedStack<Integer> values = new LinkedStack();
 
        // Stack for Operators: 'ops'
        LinkedStack<Character> ops = new LinkedStack();
 
        for (int i = 0; i < tokens.length; i++)
        {
             // Skip if has whitespace
            if (tokens[i] == ' ')
                continue;
 
            // Push to number stack 
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                
                values.push(Integer.parseInt(sbuf.toString()));
            }
 
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                     tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                
                while (!ops.isEmpty()&& hasPrecedence(tokens[i], ops.top()))
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
 
                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }
 
        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.isEmpty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
 
        // Top of 'values' contains result, return it
        
        return values.pop();
    }
 
   /**
    * 
    * @param op1
    * @param op2
    * @return true if current operator is * or /, otherwise return false 
    */
    public static boolean hasPrecedence(char op1, char op2)
    {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
 
    /**
     * 
     * @param op current operator
     * @param b value 1
     * @param a value 2
     * @return value of a "op" b
     */
    public static int applyOp(Character op, int b, int a)
    {
        switch (op)
        {
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
        case '/':
            if (b == 0)
                throw new
                UnsupportedOperationException("Cannot divide by zero");
            return a / b;
        }
        return 0;
    }
    
    
    
    
}
