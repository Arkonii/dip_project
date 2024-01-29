package com.example

class RPN {

    /**
     * Removes all characters that are not operators, whitespace, or digits
     *
     * @param expr the expression to parse
     * @return the parsed expression
     */
    private static String parseExpression(String expr) {
        def parsedExpr = expr.replaceAll("[^\\^\\*\\+\\-\\d/\\s]", "")
        def trimmedExpr = parsedExpr.replaceAll("\\s+", " ")
        return trimmedExpr
    }

    /**
     * Computes the outcome of a given expression in Reverse Polish Notation
     *
     * @param expr the expression to compute
     */
    static Double compute(String expr) {
        def validExpr = parseExpression(expr)
        def stack = []

        //println validExpr
        //println "Input\tOperation\tStack after"

        validExpr.split("\\s").each { token ->
            //print "$token\t"

            def secondOperand = 0.0
            def firstOperand = 0.0

            switch (token) {
                case "+":
                    //print "Operate\t\t"
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(firstOperand + secondOperand)
                    break
                case "-":
                    //print "Operate\t\t"
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(firstOperand - secondOperand)
                    break
                case "*":
                    //print "Operate\t\t"
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(firstOperand * secondOperand)
                    break
                case "/":
                    //print "Operate\t\t"
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()

                    if (secondOperand == 0.0) {
                        throw new ArithmeticException("Cannot divide by zero!")
                    }

                    stack.push(firstOperand / secondOperand)
                    break
                case "^":
                    //print "Operate\t\t"
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(Math.pow(firstOperand, secondOperand))
                    break
                default:
                    //print "Push\t\t"
                    stack.push(token.toDouble())
                    break
            }

            //stack
        }
        def result = Double.parseDouble(stack.pop().toString())
        //println(" result in compute : "+result)
        return result
    }

    /**
     * What runs the program
     *
     * @param args the command line arguments
     */
    static main(String[] args) {
        def a
        try {
            def expr = args.join(' ')
            //println expr
            a = compute(expr)
        } catch (Throwable err) {
            println err.getMessage()
        }
        return a
    }
}
