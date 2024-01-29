package com.example

/**
 * Klasa RPN to rdzeń kalkulator, przekazując do niej poprawnie utworzone równanie ONP, zwraca poprawny wynik takiego równania
 */
class RPN {

    /**
     * Funkcja parsująca znaki z równania ONP
     * @param expr ciąg znaków
     * @return
     */
    private static String parseExpression(String expr) {
        def parsedExpr = expr.replaceAll("[^\\^\\*\\+\\-\\d/\\s]", "")
        def trimmedExpr = parsedExpr.replaceAll("\\s+", " ")
        return trimmedExpr
    }

    /**
     * funkcja wykorzystująca stos do obliczenia wyniku równania ONP
     * @param expr ciąg znaków który został przekazany jako równanie ONP
     * @return wynik równania ONP
     */
    static Double compute(String expr) {
        def validExpr = parseExpression(expr)
        def stack = []

        validExpr.split("\\s").each { token ->
            def secondOperand = 0.0
            def firstOperand = 0.0

            switch (token) {
                case "+":
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(firstOperand + secondOperand)
                    break
                case "-":
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(firstOperand - secondOperand)
                    break
                case "*":
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(firstOperand * secondOperand)
                    break
                case "/":
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()

                    if (secondOperand == 0.0) {
                        throw new ArithmeticException("Nie można dzielić przez 0!")
                    }

                    stack.push(firstOperand / secondOperand)
                    break
                case "^":
                    secondOperand = stack.pop()
                    firstOperand = stack.pop()
                    stack.push(Math.pow(firstOperand, secondOperand))
                    break
                default:
                    stack.push(token.toDouble())
                    break
            }
        }
        def result = Double.parseDouble(stack.pop().toString())
        return result
    }

    /**
     * funkcja główna do której przekazuję się równanie ONP
     * @param args ciąg znaków przekazany jako równanie ONP
     * return wynik równania ONP
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
