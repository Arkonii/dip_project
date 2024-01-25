import groovy.sql.Sql

final class Database {
   static Sql sql = Sql.newInstance("jdbc:sqlite:testdb.db", null, null, 'org.sqlite.JDBC')

    static void main(String[] args) {
        createDatabase()
        addStudent("Tom", "Jones", 19)
        addGrade(1, 1, 4, 2)
        println(calculateSubjectAverage(1, 1))
        println(calculateOverallAverage(1))
    }

    static void createDatabase() {
        def url = "jdbc:sqlite:testdb.db"
        def sql = Sql.newInstance(url, null, null, 'org.sqlite.JDBC')

        try {
            // Tworzenie tabeli STUDENTS
            sql.execute("""
    CREATE TABLE IF NOT EXISTS STUDENTS (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT,
        surname TEXT,
        age INTEGER
    )
""")

            // Dodawanie studentów do tabeli STUDENTS
            def studentsData = [
                    ["Jacek", "Kowalski", 20],
                    ["Joanna", "Młyczarska", 22],
                    ["Alicja", "Iglica", 21]
            ]

            studentsData.each { student ->
                // Sprawdzenie, czy student już istnieje
                def existingStudent = sql.firstRow("SELECT id FROM STUDENTS WHERE name = ? AND surname = ? AND age = ?", student)

                if (!existingStudent) {
                    // Jeżeli student nie istnieje, dodajemy go
                    sql.execute("INSERT INTO STUDENTS (name, surname, age) VALUES (?, ?, ?)", student)
                }
            }

// Tworzenie tabeli SUBJECTS
            sql.execute("""
    CREATE TABLE IF NOT EXISTS SUBJECTS (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        subject_name TEXT,
        weight_of_subject INTEGER
    )
""")

// Dodawanie przedmiotów do tabeli SUBJECTS
            def subjectsData = [
                    ["Matematyka", 2],
                    ["Fizyka", 3],
                    ["Informatyka", 1]
            ]

            subjectsData.each { subject ->
                // Sprawdzenie, czy przedmiot już istnieje
                def existingSubject = sql.firstRow("SELECT id FROM SUBJECTS WHERE subject_name = ?", [subject[0]])

                if (!existingSubject) {
                    // Jeżeli przedmiot nie istnieje, dodajemy go
                    sql.execute("INSERT INTO SUBJECTS (subject_name, weight_of_subject) VALUES (?, ?)", subject)
                }
            }

// Tworzenie tabeli GRADES
            sql.execute("""
    CREATE TABLE IF NOT EXISTS GRADES (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        student_id INTEGER,
        subject_id INTEGER,
        grade INTEGER,
        weight_of_grade INTEGER,
        FOREIGN KEY(student_id) REFERENCES STUDENTS(id),
        FOREIGN KEY(subject_id) REFERENCES SUBJECTS(id)
    )
""")

// Dodawanie ocen do tabeli GRADES
            def studentId = 1
            def subjectId = 1

            sql.execute("INSERT INTO GRADES (student_id, subject_id, grade, weight_of_grade) VALUES (?, ?, ?, ?)", [studentId, subjectId, 5, 2])

        } finally {
            sql.close()
        }
    }
    // Funkcja dodająca studenta
    static def addStudent(name, surname, age) {
        // Sprawdzenie, czy student już istnieje
        def existingStudent = sql.firstRow("SELECT id FROM STUDENTS WHERE name = ? AND surname = ? AND age = ?", [name, surname, age])

        if (!existingStudent) {
            // Jeżeli student nie istnieje, dodajemy go
            sql.execute("INSERT INTO STUDENTS (name, surname, age) VALUES (?, ?, ?)", [name, surname, age])
        }
    }

// Funkcja dodająca ocenę danemu studentowi
    static def addGrade(studentId, subjectId, grade, weightOfGrade) {
        sql.execute("INSERT INTO GRADES (student_id, subject_id, grade, weight_of_grade) VALUES (?, ?, ?, ?)", [studentId, subjectId, grade, weightOfGrade])
    }

// Funkcja usuwająca daną ocenę studentowi (za pomocą id oceny)
    static def deleteGrade(gradeId) {
        sql.execute("DELETE FROM GRADES WHERE id = ?", [gradeId])
    }

// Funkcja aktualizująca ocenę studenta (za pomocą id oceny)
    static def updateGrade(gradeId, newGrade, newWeight) {
        sql.execute("UPDATE GRADES SET grade = ?, weight_of_grade = ? WHERE id = ?", [newGrade, newWeight, gradeId])
    }

// Funkcja wyliczająca średnią dla danego przedmiotu dla danego studenta z uwzględnieniem wagi ocen
    static def calculateSubjectAverage(studentId, subjectId) {
        def result = sql.firstRow("""
        SELECT AVG(grade * weight_of_grade) / AVG(weight_of_grade) AS weighted_avg
        FROM GRADES
        WHERE student_id = ? AND subject_id = ?
    """, [studentId, subjectId])

        return result?.weighted_avg ?: 0
    }

// Funkcja wyliczająca średnią dla każdego studenta z wszystkich przedmiotów z uwzględnieniem wagi przedmiotu dla danego studenta
    static def calculateOverallAverage(studentId) {
        def result = sql.firstRow("""
        SELECT AVG(GRADES.grade * SUBJECTS.weight_of_subject) / AVG(SUBJECTS.weight_of_subject) AS weighted_avg
        FROM GRADES
        INNER JOIN SUBJECTS ON GRADES.subject_id = SUBJECTS.id
        WHERE GRADES.student_id = ?
    """, [studentId])

        return result?.weighted_avg ?: 0
    }
}
