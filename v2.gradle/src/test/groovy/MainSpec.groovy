import spock.lang.*

class exampleSpec extends Specification {
    //pola instancji
    //metody do przygotowania środowiska testowego
    def "przykładowy test"(){
        given:
        def mockObject = Mock(Class)  // Przykładowy obiekt "mockObject" klasy "Class"

        when:
        def wynik = mockObject.add(2, 3) // Przykładowa funkcja add

        then:
        1 * mockObject.function(2, 3) >> 5  // Sprawdzane jest czy w bloku when dana funkcja została wywołana dokładne raz, z dokładnie podanymi danymi i czy wynik się zgadza
    }
    //metody pomocnicze
}
