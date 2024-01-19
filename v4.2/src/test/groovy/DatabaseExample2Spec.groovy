import spock.lang.Specification


    def databaseExample

    def setup() {

        databaseExample = new DatabaseExample()
    }
    def "flaga poczatek"() {
        expect:
        1==0
    }

    def "Należy dodać użytkownika do bazy danych"() {
        when:

        databaseExample.addUserToDatabase("John", 25)

        then:

        Map<String, Object> addedUser = databaseExample.getUserFromDatabase("John")
        addedUser.name == "John"
        addedUser.age == 25
    }

    def "Należy usunąć użytkownika z bazy danych"() {
        when:

        databaseExample.addUserToDatabase("Jane", 30)

        databaseExample.removeUserFromDatabase("Jane")

        then:

        Map<String, Object> removedUser = databaseExample.getUserFromDatabase("Jane")
        removedUser == null
    }

    def "Należy zaktualizować dane użytkownika w bazie danych"() {
        when:

        databaseExample.addUserToDatabase("Bob", 35)

        databaseExample.updateUserAgeInDatabase("Bob", 40)

        then:

        Map<String, Object> updatedUser = databaseExample.getUserFromDatabase("Bob")
        updatedUser.age == 40
    }

    def "Powinien pobrać listę użytkowników z bazy danych"() {
        when:

        List<Map<String, Object>> userList = databaseExample.getAllUsersFromDatabase()

        then:

        userList.size() > 0
    }

    def "Powinien znaleźć użytkownika według nazwy w bazie danych"() {
        when:

        databaseExample.addUserToDatabase("Alice", 28)

        Map<String, Object> foundUser = databaseExample.getUserByNameFromDatabase("Alice")

        then:

        foundUser.name == "Alice"
    }

    def "Powinien generować unikalny identyfikator użytkownika w bazie danych"() {
        when:

        def user1 = [name: "John", age: 25]
        def user2 = [name: "Jane", age: 30]
        databaseExample.addUserToDatabase(user1.name, user1.age)
        databaseExample.addUserToDatabase(user2.name, user2.age)

        then:

        user1.id != user2.id
    }
    def "flaga koniec"() {
        expect:
        1==0
    }
