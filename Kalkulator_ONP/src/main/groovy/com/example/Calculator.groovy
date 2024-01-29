package com.example

class Calculator {
    static def calculate(String expression) {
        def stack = []

        expression.tokenize().each { token ->
            if (isNumber(token)) {
                stack << token.toBigDecimal()
            } else {
                def operand2 = stack.pop()
                def operand1 = stack.pop()

                switch (token) {
                    case '+':
                        stack << (operand1 + operand2)
                        break
                    case '-':
                        stack << (operand1 - operand2)
                        break
                    case '*':
                        stack << (operand1 * operand2)
                        break
                    case '/':
                        if (operand2 == 0) {
                            throw new ArithmeticException("Cannot divide by zero.")
                        }
                        stack << (operand1 / operand2)
                        break
                    default:
                        throw new IllegalArgumentException("Unknown operator: $token")
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid RPN expression.")
        }

        return stack.pop()
    }

    private static boolean isNumber(String token) {
        try {
            token.toBigDecimal()
            return true
        } catch (NumberFormatException e) {
            return false
        }
    }
}

