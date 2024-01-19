import spock.lang.*

class CalculatorSpec extends Specification {

    def "Powinien dodawać dwie liczby"() {
        given:
        def calculator = new Calculator()

        when:
        def result = calculator.add(5, 7)

        then:
        result == 12
    }

    def "Powinien odejmować drugą liczbę od pierwszej"() {
        given:
        def calculator = new Calculator()

        when:
        def result = calculator.sub(10, 3)

        then:
        result == 7
    }

    def "Powinien mnożyć trzy liczby"() {
        given:
        def calculator = new Calculator()

        when:
        def result = calculator.multi(2, 3, 4)

        then:
        result == 24
    }

    def "Powinien dzielić pierwszą liczbę przez drugą"() {
        given:
        def calculator = new Calculator()

        when:
        def result = calculator.div(15, 3)

        then:
        result == 5.0
    }

    def "Powinien rzucać wyjątek ArithmeticException przy dzieleniu przez zero"() {
        given:
        def calculator = new Calculator()

        when:
        def result = { calculator.div(10, 0) }

        then:
        result.shouldThrow(ArithmeticException, "Nie można dzielić przez zero.")
    }
}
