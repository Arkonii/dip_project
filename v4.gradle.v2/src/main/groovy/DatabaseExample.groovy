import groovy.sql.Sql

class DatabaseExample {
    static void main(String[] args) {
        createDatabase()
    }

    static void createDatabase() {
        def url = "jdbc:sqlite:testdb.db"
        def sql = Sql.newInstance(url, null, null, 'org.sqlite.JDBC')

        try {
            sql.execute("""
                CREATE TABLE IF NOT EXISTS USERS (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    age INTEGER
                )
            """)
        } finally {
            sql.close()
        }
    }

    static void addUserToDatabase(String name, int age) {
        def url = "jdbc:sqlite:testdb.db"
        def sql = Sql.newInstance(url, null, null, 'org.sqlite.JDBC')

        try {
            sql.execute("INSERT INTO USERS (name, age) VALUES (?, ?)", [name, age])
            println("Added user: $name, Age: $age")
        } finally {
            sql.close()
        }
    }

    static void updateUserAgeInDatabase(String name, int newAge) {
        def url = "jdbc:sqlite:testdb.db"
        def sql = Sql.newInstance(url, null, null, 'org.sqlite.JDBC')

        try {
            sql.execute("UPDATE USERS SET age = ? WHERE name = ?", [newAge, name])
            println("Updated age for user $name to $newAge")
        } finally {
            sql.close()
        }
    }

    static void removeUserFromDatabase(String name) {
        def url = "jdbc:sqlite:testdb.db"
        def sql = Sql.newInstance(url, null, null, 'org.sqlite.JDBC')

        try {
            sql.execute("DELETE FROM USERS WHERE name = ?", [name])
            println("Removed user: $name")
        } finally {
            sql.close()
        }
    }

    static List<Map<String, Object>> getAllUsersFromDatabase() {
        def url = "jdbc:sqlite:testdb.db"
        def sql = Sql.newInstance(url, null, null, 'org.sqlite.JDBC')

        try {
            def result = sql.rows("SELECT * FROM USERS")
            println("All users in the database:")
            result.each { user ->
                println("ID: ${user.id}, Name: ${user.name}, Age: ${user.age}")
            }
            return result // Dodaj zwracanie wyniku zapytania
        } finally {
            sql.close()
        }
    }


    static Map<String, Object> getUserByNameFromDatabase(String name) {
        def url = "jdbc:sqlite:testdb.db"
        def sql = Sql.newInstance(url, null, null, 'org.sqlite.JDBC')

        try {
            def user = sql.firstRow("SELECT * FROM USERS WHERE name = ?", [name])
            if (user) {
                println("User found - ID: ${user.id}, Name: ${user.name}, Age: ${user.age}")
                return user
            } else {
                println("User not found")
                return null
            }
        } finally {
            sql.close()
        }
    }
}
