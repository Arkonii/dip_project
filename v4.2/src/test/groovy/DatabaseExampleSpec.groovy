import spock.lang.*

class DatabaseExampleSpec extends Specification {

    def "test tesu"(){
        given:
        def a=1
        when:
        a=a+1
        then:
        a==2
    }

    def "flaga koniec"() {
        expect:
        1==1
    }
}
