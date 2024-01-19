import spock.lang.*

class DatabaseExampleSpec extends Specification {

    def "Powinien dodać użytkownika do bazy danych"() {
        given:
        def databaseExample = new DatabaseExample()

        when:
        databaseExample.addUserToDatabase("John", 25)

        then:
        Map<String, Object> addedUser = databaseExample.getUserByNameFromDatabase("John")
        addedUser.name == "John"
        addedUser.age == 25
    }

    def "Powinien usunąć użytkownika z bazy danych"() {
        given:
        def databaseExample = new DatabaseExample()

        when:
        databaseExample.addUserToDatabase("Jane", 30)
        databaseExample.removeUserFromDatabase("Jane")

        then:
        Map<String, Object> removedUser = databaseExample.getUserByNameFromDatabase("Jane")
        removedUser == null
    }

    def "Powinien zaktualizować dane użytkownika w bazie danych"() {
        given:
        def databaseExample = new DatabaseExample()

        when:
        databaseExample.addUserToDatabase("Bob", 35)
        databaseExample.updateUserAgeInDatabase("Bob", 40)

        then:
        Map<String, Object> updatedUser = databaseExample.getUserByNameFromDatabase("Bob")
        updatedUser.age == 40
    }

    def "Powinien pobrać listę użytkowników z bazy danych"() {
        given:
        def databaseExample = new DatabaseExample()

        when:
        databaseExample.addUserToDatabase("John", 25)
        databaseExample.addUserToDatabase("Jane", 30)

        then:
        List<Map<String, Object>> userList = databaseExample.getAllUsersFromDatabase()
        userList.find { it.name == "John" && it.age == 25 } != null
        userList.find { it.name == "Jane" && it.age == 30 } != null
    }


    def "Powinien znaleźć użytkownika według nazwy w bazie danych"() {
        given:
        def databaseExample = new DatabaseExample()

        when:
        databaseExample.addUserToDatabase("Alice", 28)
        Map<String, Object> foundUser = databaseExample.getUserByNameFromDatabase("Alice")

        then:
        foundUser.name == "Alice"
    }

    def "Powinien generować unikalny identyfikator użytkownika w bazie danych"() {
        given:
        def databaseExample = new DatabaseExample()
        def user1 = [name: "John", age: 25]
        def user2 = [name: "Jane", age: 30]

        when:
        databaseExample.addUserToDatabase(user1.name, user1.age)
        databaseExample.addUserToDatabase(user2.name, user2.age)

        then:
        def user1Id = databaseExample.getUserByNameFromDatabase(user1.name)?.id
        def user2Id = databaseExample.getUserByNameFromDatabase(user2.name)?.id

        user1Id != null
        user2Id != null
        user1Id != user2Id
    }
}
