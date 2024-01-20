import spock.lang.*

class CalculatorSpec extends Specification {

    def calculator
    def setup(){
        and: "utworzenie obiektu Calculator przed każdym testem"
        calculator = new Calculator()
    }

    def "Powinien sprawdzić poprawność dodawania"() {
        when:"deklaracja zmiennej wynikowej i parametrów początkowych funkcji która będzie sprawdzana"
        def result = calculator.add(5,7,1,1)

        then:"sprawdzanie poprawności wyniku"
        result == 14
    }

    def "Powinien sprawdzić poprawność odejmowania z wykorzystaniem bloku where"() {
        when:"deklaracja zmiennej wynikowej i parametrów początkowych funkcji która będzie sprawdzana"
        def result = calculator.sub(a, b)

        then:"sprawdzanie poprawności wyniku"
        result == expectedResult

        where:"deklaracja tabeli danych wejściowych"
        a | b | expectedResult
        2 | 1 | 1
        0 | -5 | -5
        10| 3 | 7
        -2| 1 | -3
    }

    def "Powinien sprawdzić poprawność mnożenia z wykorzystaniem skróconej formy testu bloku expect"() {
        expect :"skrócona forma testu , bez bloku when i then"
        calculator.multi(1,1,1)==1
        calculator.multi(1,-1,1)==-1
        calculator.multi(2,1,1,100)==200
    }

    def "Powinien sprawdzić poprawność dzielenia"() {
        when:"deklaracja zmiennej wynikowej i parametrów początkowych funkcji która będzie sprawdzana"
        def result = calculator.div(2, 2,2)

        then:"sprawdzanie poprawności wyniku"
        result == 0.5
    }

    def "Powinien rzucać wyjątek ArithmeticException przy dzieleniu przez zero"() {
        when:"deklaracja zmiennej wynikowej i parametrów początkowych funkcji która będzie sprawdzana"
        calculator.div(10, 0)

        then:"Sprawdzanie czy wyjątek został wychwycony"
        thrown(ArithmeticException)
    }

    @Ignore
    def "Powinien nie przejśc testu poprawnie"(){
        expect:"sprawdzanie poprawności wyniku"
        calculator.add(2,3)==4
    }

}
