import spock.lang.*

class StringConverterSpec extends Specification {

    def "Powinien łączyć ciągi znaków"() {
        given:
        def stringConverter = new StringConverter()

        when:
        def result = stringConverter.add("Hello", " ", "World")

        then:
        result == "Hello World"
    }

    def "Powinien zwracać odwrotność ciągów znaków"() {
        given:
        def stringConverter = new StringConverter()

        when:
        def result = stringConverter.reciprocal("abc", "123")

        then:
        result == "cba321"
    }

    def "Powinien zwracać true, gdy ciąg jest pusty lub null"() {
        given:
        def stringConverter = new StringConverter()

        when:
        def resultEmpty = stringConverter.emptyOrNull()
        def resultNotEmpty = stringConverter.emptyOrNull("Non-empty")

        then:
        resultEmpty == true
        resultNotEmpty == false
    }

    def "Powinien zamieniać ciąg na małe litery"() {
        given:
        def stringConverter = new StringConverter()

        when:
        def result = stringConverter.makeLowCase("HELLO")

        then:
        result == "hello"
    }

    def "Powinien zamieniać ciąg na duże litery"() {
        given:
        def stringConverter = new StringConverter()

        when:
        def result = stringConverter.makeUpperCase("world")

        then:
        result == "WORLD"
    }
}
