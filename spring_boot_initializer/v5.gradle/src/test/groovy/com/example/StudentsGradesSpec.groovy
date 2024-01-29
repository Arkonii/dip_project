package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.*

/**
 * Klasa testów Spock, sprawdzająca popranowść działania bazy danych Spring Boot h2, tabel Students i Grades
 */
@SpringBootTest
class StudentsGradesSpec extends Specification  {
    /**
     * deklaracja serwisów - klas obsługujących tabelę Students i Grades
     */
    @Autowired StudentsService studentsService
    @Autowired GradesService gradesService

    static App
    def setupSpec() {
        and:"Inicjalizacja aplikacji Spring Boot przed 1 testem"
        App = new Application()
        App.main()
    }

    def cleanupSpec() {
        and:"Zamykanie serwera po zakończeniu ostatniego testu"
        App.mainStop()
    }
    def cleanup(){
        and:"Kasowanie zmian dokonanych w tabelach bazy danych po zakończeniu każdego testu"
        studentsService.deleteAllStudents()
        gradesService.deleteAllGrades()
    }

    def "Sprawdzanie poprawności dodawania użytkowników"() {
        when:"Dodawanie studenta"
        studentsService.addNewStudent("Jacek Kowalski")

        then:"Sprawdzanie czy student pod tym indeksem istnieje"
        studentsService.getStudentById(1L) != null
    }
    def "Sprawdzanie poprawności dodawania ocen"() {
        when:"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Matematyka",4.0,student,1.0)

        then :"Sprawdzanie czy ocena pod tym indeksem istnieje"
        gradesService.getGradeById(1L)!=null
    }
    def "Sprawdzanie poprawności składni w dodawanych ocenach"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        def add = gradesService.addNewGrade("Fizyka",5.0,student,2.0)

        when :"Tworzenie zmiennej grades do której zwracany zostanie obiekt(tabela) Grades"
        def grades=gradesService.getGradeById(add.id)

        then :"Sprawdzanie czy pod wskazanym id znajduje się zmienna z wskazanym typem"
        grades.id instanceof Long
        grades.nameOfSubject instanceof String
        grades.grade instanceof Double
        grades.weight instanceof Double
    }
    def "Sprawdzanie poprawności aktualizacji oceny"(){
        given:"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        def add = gradesService.addNewGrade("Fizyka",5.0,student,2.0)

        when :"Tworzenie zmiennej grades do której zwracany zostanie obiekt(tabela) Grades oraz utworzenie zmiennej update która zainicjuje aktualizacje"
        def update= gradesService.updateGradeById(add.id,"Informatyka",4.0,1.5)
        def grades=gradesService.getGradeById(add.id)

        then:"Sprawdzenie czy aktualizacja się powiodła"
        grades.nameOfSubject=="Informatyka"
        grades.grade==4.0
        grades.weight==1.5
    }
    def "Sprawdzanie poprawności usuwania oceny"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        def add = gradesService.addNewGrade("Fizyka",5.0,student,2.0)

        when :"Usuwanie oceny"
        gradesService.deleteGradeById(add.id)

        then :"Sprawdzanie czy ocena została usunięta"
        gradesService.getGradeById(add.id)==null
    }
    def "Sprawdzanie poprawności wyliczania średniej arytmetycznej dla danego studenta dla danego przedmiotu gdy nie ma żadnej oceny"(){
        given :"Dodawanie studenta"
        def student = studentsService.addNewStudent("Jacek Kowalski")

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie czy udało się wyliczyć średnią"
        average==null
    }
    def "Sprawdzanie poprawności wyliczania średniej arytmetycznej dla danego studenta dla danego przedmiotu przy jednej ocenie"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==5.0
    }
    def "Sprawdzanie poprawności wyliczania średniej arytmetycznej dla danego studenta dla danego przedmiotu przy dwóch ocenach"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Fizyka",4.5,student,1.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==4.75
    }
    def "Sprawdzanie poprawności wyliczania średniej arytmetycznej dla danego studenta dla danego przedmiotu przy wielu ocenach"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Matematyka",3.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Fizyka",4.0,student,1.0)
        gradesService.addNewGrade("Fizyka",4.5,student,2.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==4.5
    }
    def "Sprawdzanie poprawności wyliczania średniej ważonej dla danego studenta dla danego przedmiotu gdy nie ma żandej oceny"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateWeightedAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie czy udało się wyliczyć średnią"
        average==null
    }
    def "Sprawdzanie poprawności wyliczania średniej ważonej dla danego studenta dla danego przedmiotu przy jednej ocenie"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateWeightedAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==5.0
    }
    def "Sprawdzanie poprawności wyliczania średniej ważonej dla danego studenta dla danego przedmiotu przy 2 ocenach"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Fizyka",4.5,student,1.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateWeightedAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average>4.83 && average<4.84
    }
    def "Sprawdzanie poprawności wyliczania średniej ważonej dla danego studenta dla danego przedmiotu przy wielu ocenach"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Matematyka",3.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Fizyka",4.0,student,1.0)
        gradesService.addNewGrade("Fizyka",4.5,student,2.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateWeightedAverageGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==4.6
    }
    def "Sprawdzanie poprawności wyliczania mediany dla danego studenta dla danego przedmiotu gdy nie ma żandej oceny"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateMedianGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie czy udało się wyliczyć średnią"
        average==null
    }
    def "Sprawdzanie poprawności wyliczania mediany dla danego studenta dla danego przedmiotu przy jednej ocenie"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateMedianGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==5.0
    }
    def "Sprawdzanie poprawności wyliczania mediany dla danego studenta dla danego przedmiotu przy 3 ocenach"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Fizyka",2.0,student,3.0)
        gradesService.addNewGrade("Fizyka",2.0,student,1.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateMedianGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==2.0
    }
    def "Sprawdzanie poprawności wyliczania mediany dla danego studenta dla danego przedmiotu przy wielu ocenach"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Fizyka",2.0,student,3.0)
        gradesService.addNewGrade("Fizyka",3.0,student,1.0)
        gradesService.addNewGrade("Fizyka",3.0,student,2.0)
        gradesService.addNewGrade("Fizyka",4.0,student,3.0)
        gradesService.addNewGrade("Fizyka",4.5,student,1.0)
        gradesService.addNewGrade("Fizyka",3.0,student,2.0)
        gradesService.addNewGrade("Fizyka",3.0,student,3.0)
        gradesService.addNewGrade("Fizyka",3.5,student,1.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateMedianGradeForSubject(student.id,"Fizyka")

        then :"Sprawdzanie poprawności wyniku"
        average==3.0
    }

    def "Sprawdzanie poprawności wyliczania średniej dla danego studenta dla wszystkich przedmiotów przedmiotów"(){
        given :"Dodawanie studenta i oceny"
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Matematyka",3.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Fizyka",4.0,student,1.0)

        when :"Tworzenie zmiennej average do której zostanie zwrócony wynik funkcji"
        def average = gradesService.calculateAverageGradeForStudent(student.id)
        def weightedAverage = gradesService.calculateWeightedAverageGradeForStudent(student.id)
        def median = gradesService.calculateMedianGradeForStudent(student.id)

        then :"Sprawdzanie poprawności wyniku"
        average==3.2
        weightedAverage>3.2
        median==3.0
    }
}
