import groovy.mock.interceptor.MockFor
import org.spockframework.mock.runtime.MockObject
import org.spockframework.mock.runtime.StaticMockMethod
import spock.lang.*

class DatabaseSpec extends Specification {

    def database
    def setup(){
        and:"tworzenie obiektu Database przed każdym testem"
        database = new Database()
    }
    def cleanup(){
        and:"wyczyszczenie wszystkich danych z bazy danych po każdym teście"
        database.clearAllUsersFromDatabase()
    }

    @IgnoreRest
    def "Powinien dodać użytkownika do bazy danych"() {
        when:"dodawanie użytkownika do bazy danych"
        database.addUserToDatabase("Jacek", 25)
        and:"tworzenie mapy addedUser w której szukamy danego użytkownika"
        Map<String, Object> addedUser = database.getUserByNameFromDatabase("Jacek")

        then:"sprawdzanie czy dany użytkownik jest w bazie danych"
        addedUser.name == "Jacek" && addedUser.age == 25

    }

    def "Powinien usunąć użytkownika z bazy danych"() {
        when:"dodanie i usunięcie użytkownika"
        database.addUserToDatabase("Joanna", 30)

        and:"sprawdzenie czy użytkownik został dodany"
        Map<String, Object> addedUser = database.getUserByNameFromDatabase("Joanna")
        assert addedUser.name == "Joanna" && addedUser.age == 30

        and:"usunięcie użytkownika"
        database.removeUserFromDatabase("Joanna")

        and:"utworzenie mapy w której szukamy danego użytkownika"
        Map<String, Object> removedUser = database.getUserByNameFromDatabase("Joanna")

        then:"sprawdzanie czy danego użytkownika nie ma"
        removedUser == null
    }

    def "Powinien zaktualizować dane użytkownika w bazie danych"() {
        when:"dodanie i zaktualizowanie danych użytkownika"
        database.addUserToDatabase("Barbara", 35)

        and:"sprawdzenie czy użytkownik został dodany"
        Map<String, Object> addedUser = database.getUserByNameFromDatabase("Joanna")
        assert addedUser.name == "Barbara" && addedUser.age == 35

        and:"aktualizacja danych użytkownika"
        database.updateUserAgeInDatabase("Barbara", 40)

        and:"utworzenie mapy w której szukamy danego użytkownika"
        Map<String, Object> updatedUser = database.getUserByNameFromDatabase("Barbara")
        then:"sprawdzenie czy dane zostały zmienione"
        updatedUser.age == 40
    }

    def "Powinien pobrać listę użytkowników z bazy danych"() {
        when:"dodanie użytkowników"
        database.addUserToDatabase("Jacek", 25)
        database.addUserToDatabase("Joanna", 30)

        and:"utworzenie listy do której pobieramy wszystkich użytkowników"
        List<Map<String, Object>> userList = database.getAllUsersFromDatabase()

        then:"sprawdzanie czy pobrał wszystkich użytkowników"
        userList.find { it.name == "Jacek" && it.age == 25 } != null
        userList.find { it.name == "Joanna" && it.age == 30 } != null
    }


    def "Powinien znaleźć użytkownika według nazwy w bazie danych"() {
        when:"dodanie użytkownika"
        database.addUserToDatabase("Alicja", 28)

        and:"utworzenie mapy w której szukamy danego użytkownika"
        Map<String, Object> foundUser = database.getUserByNameFromDatabase("Alicja")

        then:"sprawdzanie czy dany użytkownik został znaleziony według nazwy"
        foundUser.name == "Alicja"
    }

    def "Powinien generować unikalny identyfikator użytkownika w bazie danych"() {
        given:"utworzenie 2 użytkowników"
        def user1 = [name: "Jacek", age: 25]
        def user2 = [name: "Joanna", age: 30]

        when:"dodanie 2 użytkowników"
        database.addUserToDatabase(user1.name, user1.age)
        database.addUserToDatabase(user2.name, user2.age)

        and:"sprawdzanie czy użytkownicy zostali dodani"
        Map<String, Object> addedUser = database.getUserByNameFromDatabase(user1.name)
        Map<String, Object> addedUser2 = database.getUserByNameFromDatabase(user2.name)
        assert addedUser.name == user1.name && addedUser.age == user1.age
        assert addedUser.name == user2.name && addedUser.age == user2.age

        and:"wyszukanie użytkownik w bazie danych za pomocą imienia i przypisanie ich id do zmiennych"
        def user1Id = database.getUserByNameFromDatabase(user1.name)?.id
        def user2Id = database.getUserByNameFromDatabase(user2.name)?.id

        then:"sprawdzenie czy id jest prawidłowe"
        user1Id != null
        user2Id != null
        user1Id != user2Id
    }
}
