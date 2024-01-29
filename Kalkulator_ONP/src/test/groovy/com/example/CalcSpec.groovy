package com.example

import spock.lang.*

/**
 * Klasa testów Spock, sprawdzająca popranowść działania kalkulatora ONP
 */
class CalcSpec extends Specification {
    def "Test poprawności wyników operacji arytmetycznych"() {
        expect:"Deklaracja równania i sprawdzanie oczekiwanego wyniku"
        RPN.compute(input) == expectedResult

        where:"Implementacja tabeli danych z wykorzystaniem bloku where"
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
        when:"Deklaracja równania"
        def result = RPN.compute("5 0 /")

        then:"Sprawdzanie czy podany wyjątek został wychwycony"
        thrown(ArithmeticException)
    }

    def "Test niepoprawnego wyrażenia - za mało operandów"() {
        when:"Deklaracja równania"
        def result = RPN.compute("2 3 + +")

        then:"Sprawdzanie czy podany wyjątek został wychwycony"
        thrown(NoSuchElementException)
    }

    def "Test niepoprawnego wyrażenia - puste wyrażenie"() {
        when:"Deklaracja równania"
        def result = RPN.compute("")

        then:"Sprawdzanie czy podany wyjątek został wychwycony"
        thrown(NumberFormatException)
    }

    def "Test integracyjny klasy RPN"() {
        given:"Deklaracja liczb"
        def expr = "2 3 +"

        when:"Deklaracja równania z wykorzystaniem zadeklarowanego String"
        def result = RPN.compute(expr)

        then:"Sprawdzanie oczekiwanego wyniku"
        result == 5.0
    }
}
