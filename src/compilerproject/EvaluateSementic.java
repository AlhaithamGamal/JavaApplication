/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerproject;

import java.util.Stack;

/**
 *
 * @author HaithamGamal
 */
public class EvaluateSementic {
    
    public static Number evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();
 int c=0;
         // Stack for numbers: 'values'
        Stack<Number> values = new Stack<Number>();
 
        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();
 
        for (int i = 0; i < tokens.length; i++)
        {
             // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;
 
            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9'||tokens[i]=='.')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9'||tokens[i]=='.'){
                    sbuf.append(tokens[i++]);}
                   for (int j = 0; j < sbuf.length(); j++) {
                    if(sbuf.charAt(j)=='.')c++;
                }
                if(c>0)
                      values.push(Double.parseDouble(sbuf.toString()));
                else
                    values.push(Integer.parseInt(sbuf.toString()));
             
            }
 
            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);
 
            // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }
 
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                     tokens[i] == '*' || tokens[i] == '/'||tokens[i] == '%' || tokens[i] == '^')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
 
                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }
 
        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
 
        // Top of 'values' contains result, return it
        return  values.pop();
    }
 
    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if(op2 == '(' && op1 != ')' || op2 == ')' && op1 != '('|| op1 == '(' && op2 != ')'){
           
                return false;
            }
        else
            if (op2 == '(' || op2 == ')'){
           
            return false;
        }
            
        else
            if ((op1 == '*' || op1 == '/'||op1 == '^' || op1 == '%') && (op2 == '+' || op2 == '-'))
            return false;
        else
            {     System.out.println("Error closing braces");
            return true;
            }
    }
 
    // A utility method to apply an operator 'op' on operands 'a' 
    // and 'b'. Return the result.
    public static Number applyOp( char  op, Number  b, Number  a)
    {
        switch (op)
        {
        case '+':
      if(a.getClass().getName()=="java.lang.Double"||b.getClass().getName()=="java.lang.Double")
              {return a.doubleValue()+b.doubleValue();}
     else
       if(a.getClass().getName()=="java.lang.Integer"&&b.getClass().getName()=="java.lang.Integer")
          return a.intValue()+b.intValue();
        case '-':
               if(a.getClass().getName()=="java.lang.Double"||b.getClass().getName()=="java.lang.Double")
              {return a.doubleValue()-b.doubleValue();}
     else
       if(a.getClass().getName()=="java.lang.Integer"&&b.getClass().getName()=="java.lang.Integer")
          return a.intValue()-b.intValue();
        case '*':
                if(a.getClass().getName()=="java.lang.Double"||b.getClass().getName()=="java.lang.Double")
              {return a.doubleValue()*b.doubleValue();}
     else
       if(a.getClass().getName()=="java.lang.Integer"&&b.getClass().getName()=="java.lang.Integer")
          return a.intValue()*b.intValue();
        case '^':
               if(a.getClass().getName()=="java.lang.Double"||b.getClass().getName()=="java.lang.Double")
              {return Math.pow(a.doubleValue(), b.doubleValue());}
     else
       if(a.getClass().getName()=="java.lang.Integer"&&b.getClass().getName()=="java.lang.Integer")
          return Math.pow(a.intValue(), b.intValue());
        case '%':
               if(a.getClass().getName()=="java.lang.Double"||b.getClass().getName()=="java.lang.Double")
              {return a.doubleValue()%b.doubleValue();}
     else
       if(a.getClass().getName()=="java.lang.Integer"&&b.getClass().getName()=="java.lang.Integer")
          return a.intValue()%b.intValue();
        case '/':
            if (b.intValue()==0)
                throw new
                UnsupportedOperationException("Cannot divide by zero");
              if(a.getClass().getName()=="java.lang.Double"||b.getClass().getName()=="java.lang.Double")
              {return a.doubleValue()/b.doubleValue();}
     else
       if(a.getClass().getName()=="java.lang.Integer"&&b.getClass().getName()=="java.lang.Integer")
          return a.intValue()/b.intValue();
           
        }
      
      
        return 0;
       
    }
    
}
