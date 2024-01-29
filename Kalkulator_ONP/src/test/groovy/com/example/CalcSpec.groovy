package com.example

import spock.lang.*

class CalcSpec extends Specification {
    def "Test poprawności wyników operacji arytmetycznych"() {
        expect:
        RPN.compute(input) == expectedResult

        where:
        input| expectedResult
        "2 3 +"| 5.0
        "5 3 -"| 2.0
        "4 5 *"| 20.0
        "10 2 /"| 5.0
        "2 3 ^"| 8.0
        "3 4 2 * +"| 11.0
        "2 3 + "| 5.0
    }
    def "Test dzielenia przez zero"() {
        when:
        def result = RPN.compute("5 0 /")

        then:
        thrown(ArithmeticException)
    }

    def "Test niepoprawnego wyrażenia - za mało operandów"() {
        when:
        def result = RPN.compute("2 3 + +")

        then:
        thrown(NoSuchElementException)
    }

    def "Test niepoprawnego wyrażenia - puste wyrażenie"() {
        when:
        def result = RPN.compute("")

        then:
        thrown(NumberFormatException)
    }



    def "Test integracyjny klasy RPN"() {
        given:
        def expr = "2 3 +"

        when:
        def result = RPN.compute(expr)

        then:
        result == 5.0
    }
}
