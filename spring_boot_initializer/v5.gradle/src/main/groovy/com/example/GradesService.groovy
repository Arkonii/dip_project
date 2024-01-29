package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Klasa GradesService odpowiada za komunukacje z bazą danych za pomocą interfejsu GradesRepository
 */
@Service
class GradesService {
    /**
     * Deklaracja repozytorium - interfejsu ktory komunikuję się z bazą danych (z tabelą Grades)
     */
    @Autowired
    private GradesRepository gradesRepository
    /**
     * Deklaracja repozytorium - interfejsu ktory komunikuję się z bazą danych (z tabelą Students)
     */
    @Autowired
    private StudentsRepository studentsRepository

    /**
     * funkcja zapisująca ocenę za pomocą repozytorium
     * @param grade obiekt Grades z klasy Grades.groovy
     * @return obiekt Grades z bazy danych ktory zostal zapisany
     */
    def saveGrade(Grades grade) {//funkcja zapisująca
        gradesRepository.save(grade)
    }
    /**
     * funkcja pobierająca ocenę za pomocą repozytorium
     * @param id oceny w tabeli Grddes
     * @return obiekt Grades z bazy danych pod wskazanych id
     */
    def getGradeById(Long id) {
        gradesRepository.findById(id).orElse(null)
    }
    /**
     * funkcja dodająca ocene do bazy danych (w tabeli Grades)
     * @param nameOfSubject nazwa przedmiotu
     * @param grade ocena
     * @param student obiekt Students klasy Students.groovy
     * @param weight waga oceny
     * @return obiekt Grades ktory zostanie dodany
     */
    def addNewGrade(String nameOfSubject, double grade, Students student, double weight) {
        def newGrade = new Grades(nameOfSubject: nameOfSubject, grade: grade, student: student, weight: weight)
        saveGrade(newGrade)
    }
    /**
     * funkcja zwracająca obiekty tabeli Grades które zą przypisane do danego studenta do danego przedmiotu
     * @param student obiekt Students klasy Students.groovy
     * @param subject nazwa przedmiotu
     * @return obiekty Grades które pasowały do danego wyszukiwania
     */
    def getGradesByStudentAndSubject(Students student, String subject) {
        gradesRepository.findAllByStudentAndNameOfSubject(student, subject)
    }
    /**
     * funkcja zwracająca obiekty tabeli Grades które są przypisane do danego studenta (z wszystkich przedmiotów)
     * @param student obiekt Students klasy Students.groovy
     * @return obiekty Grades które pasowały do danego wyszukiwania
     */
    def getGradesByStudent(Students student) {
        gradesRepository.findAllByStudent(student)
    }

    /**
     * funkcja wyliczająca średnią arytmetyczną dla danego studenta dla danego przedmiotu
     * @param studentId id studenta z tabeli Students
     * @param subject nazwa przedmiotu
     * @return średnia arytmetyczna dla danego studenta dla danego przedmiotu
     */
    def calculateAverageGradeForSubject(Long studentId, String subject) {
        def student = studentsRepository.findById(studentId).orElse(null)
        if (student) {
            def grades = getGradesByStudentAndSubject(student, subject)
            if (grades) {
                def sum = grades.collect { it.grade }.sum()
                return sum / grades.size()
            }
        }
        return null
    }
    /**
     * funkcja wyliczająca średnią ważoną dla danego studenta dla danego przedmiotu
     * @param studentId id studenta z tabeli Students
     * @param subject nazwa przedmiotu
     * @return średnia ważoną dla danego studenta dla danego przedmiotu
     */
    def calculateWeightedAverageGradeForSubject(Long studentId, String subject) {
        def student = studentsRepository.findById(studentId).orElse(null)
        if (student) {
            def grades = getGradesByStudentAndSubject(student, subject)
            if (grades) {
                def weightedSum = grades.collect { it.grade * it.weight }.sum()
                def totalWeight = grades.collect { it.weight }.sum()
                return weightedSum / totalWeight
            }
        }
        return null
    }
    /**
     * funkcja wyliczająca mediane dla danego studenta dla danego przedmiotu
     * @param studentId id studenta z tabeli Students
     * @param subject nazwa przedmiotu
     * @return mediana dla danego studenta dla danego przedmiotu
     */
    def calculateMedianGradeForSubject(Long studentId, String subject) {
        def student = studentsRepository.findById(studentId).orElse(null)
        if (student) {
            def grades = getGradesByStudentAndSubject(student, subject)
            if (grades) {
                // Sortowanie ocen przed obliczeniem mediany
                def sortedGrades = grades.sort { it.grade }
                def size = sortedGrades.size()

                if (size % 2 == 0) {
                    def middle1 = sortedGrades[size / 2 - 1].grade
                    def middle2 = sortedGrades[size / 2].grade
                    return (middle1 + middle2) / 2
                } else {
                    return sortedGrades[size / 2].grade
                }
            }
        }
        return null
    }
    /**
     * funkcja wyliczająca średnią arytmetyczną dla danego studenta (z wszystkich przedmiotów)
     * @param studentId id studenta z tabeli Students
     * @param subject nazwa przedmiotu
     * @return średnia arytmetyczna dla danego studenta
     */
    def calculateAverageGradeForStudent(Long studentId) {
        def student = studentsRepository.findById(studentId).orElse(null)
        if (student) {
            def grades = getGradesByStudent(student)
            if (grades) {
                def sum = grades.collect { it.grade }.sum()
                return sum / grades.size()
            }
        }
        return null
    }
    /**
     * funkcja wyliczająca średnią ważoną dla danego studenta (z wszystkich przedmiotów)
     * @param studentId id studenta z tabeli Students
     * @param subject nazwa przedmiotu
     * @return średnia ważoną dla danego studenta
     */
    def calculateWeightedAverageGradeForStudent(Long studentId) {
        def student = studentsRepository.findById(studentId).orElse(null)
        if (student) {
            def grades = getGradesByStudent(student)
            if (grades) {
                def weightedSum = 0.0
                def totalWeight = 0.0

                grades.each { grade ->
                    if (grade.weight != null) {
                        weightedSum += grade.grade * grade.weight
                        totalWeight += grade.weight
                    }
                }

                return totalWeight != 0.0 ? weightedSum / totalWeight : null
            }
        }
        return null
    }
    /**
     * funkcja wyliczająca mediane dla danego studenta (z wszystkich przedmiotów)
     * @param studentId id studenta z tabeli Students
     * @param subject nazwa przedmiotu
     * @return mediana arytmetyczna dla danego studenta
     */
    def calculateMedianGradeForStudent(Long studentId) {
        def student = studentsRepository.findById(studentId).orElse(null)
        if (student) {
            def grades = getGradesByStudent(student)
            if (grades) {
                // Sortowanie ocen przed obliczeniem mediany
                def sortedGrades = grades.sort { it.grade }
                def size = sortedGrades.size()

                if (size % 2 == 0) {
                    def middle1 = sortedGrades[size / 2 - 1].grade
                    def middle2 = sortedGrades[size / 2].grade
                    return (middle1 + middle2) / 2
                } else {
                    return sortedGrades[size / 2].grade
                }
            }
        }
        return null
    }
    /**
     * funkcja usuwająca ocenę pod podanym Id
     * @param gradeId id oceny w tabeli Grades
     * @return obiekt Grades ktory został usunięty (null)
     */
    def deleteGradeById(Long gradeId) {
        gradesRepository.findById(gradeId).ifPresent {
            gradesRepository.delete(it)
        }
    }
    /**
     * funkcja aktualizująca ocene pod wskazanym id
     * @param gradeId id oceny tabeli Grades którą aktualizujemy
     * @param newNameOfSubject nowa nazwa przedmiotu
     * @param newGrade nowa ocena
     * @param newWeight nowa waga oceny
     * @return zaktualizowany obiekt Grades z tabeli Grades
     */
    def updateGradeById(Long gradeId, String newNameOfSubject, double newGrade, double newWeight) {
        def existingGrade = gradesRepository.findById(gradeId).orElse(null)

        if (existingGrade) {
            existingGrade.nameOfSubject = newNameOfSubject
            existingGrade.grade = newGrade
            existingGrade.weight = newWeight

            gradesRepository.save(existingGrade)
        } else {
            println("Blad id : "+gradeId)
        }
    }
    /**
     * funkcja usuwajaca całą tabelę Grades
     * @return wszystkie obiekty Grades które zostały usunięte (null)
     */
    @Transactional
    def deleteAllGrades() {
        gradesRepository.deleteAll();
    }
}
