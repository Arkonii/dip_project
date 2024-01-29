package com.example

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.support.Repositories
import org.springframework.stereotype.Repository
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.EnableTransactionManagement
import spock.lang.*

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@SpringBootTest
class DatabaseSpec extends Specification  {
    @Autowired StudentsService studentsService
    @Autowired GradesService gradesService

    static App

    def setupSpec() {
        App = new Application()
        App.main()
    }

    def cleanupSpec() {
        App.mainStop()
    }
    def cleanup(){
        studentsService.deleteAllStudents()
        gradesService.deleteAllGrades()
    }

    def "Sprawdzanie poprawności dodawania użytkowników"() {
        when:
        studentsService.addNewStudent("Jacek Kowalski")

        then:
        studentsService.getStudentById(1L) != null
    }
    def "Sprawdzanie poprawności dodawania ocen"() {
        when:
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Matematyka",4.0,student,1.0)

        then :
        gradesService.getGradeById(1L)!=null
    }
    def "Sprawdzanie poprawności składni w dodawanych ocenach"(){
        given :
        def student = studentsService.addNewStudent("Jacek Kowalski")
        def add = gradesService.addNewGrade("Fizyka",5.0,student,2.0)

        when :
        def grades=gradesService.getGradeById(add.id)

        then :
        grades.id instanceof Long
        grades.nameOfSubject instanceof String
        grades.grade instanceof Double
        grades.weight instanceof Double
    }
    def "Sprawdzanie poprawności aktualizacji oceny"(){
        when :
        def student = studentsService.addNewStudent("Jacek Kowalski")
        def add = gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        def update= gradesService.updateGradeById(add.id,"Informatyka",4.0,1.5)
        def grades=gradesService.getGradeById(add.id)
        then:
        grades.nameOfSubject=="Informatyka"
        grades.grade==4.0
        grades.weight==1.5
    }
    def "Sprawdzanie poprawności usuwania oceny"(){
        when :
        def student = studentsService.addNewStudent("Jacek Kowalski")
        def add = gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.deleteGradeById(add.id)
        then :
        gradesService.getGradeById(add.id)==null
    }
    def "Sprawdzanie poprawności wyliczania średniej dla danego studenta dla danego przedmiotu"(){
        given :
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Matematyka",3.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Fizyka",4.0,student,1.0)
        when :
        def average = gradesService.calculateAverageGradeForSubject(student.id,"Fizyka")
        def weightedAverage = gradesService.calculateWeightedAverageGradeForSubject(student.id,"Fizyka")
        def median = gradesService.calculateMedianGradeForSubject(student.id,"Matematyka")
        then :
        average==4.5
        weightedAverage>4.5
        median==2.0
    }
    def "Sprawdzanie poprawności wyliczania średniej dla danego studenta dla wszystkich przedmiotów przedmiotów"(){
        given :
        def student = studentsService.addNewStudent("Jacek Kowalski")
        gradesService.addNewGrade("Fizyka",5.0,student,2.0)
        gradesService.addNewGrade("Matematyka",3.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Matematyka",2.0,student,1.0)
        gradesService.addNewGrade("Fizyka",4.0,student,1.0)
        when :
        def average = gradesService.calculateAverageGradeForStudent(student.id)
        def weightedAverage = gradesService.calculateWeightedAverageGradeForStudent(student.id)
        def median = gradesService.calculateMedianGradeForStudent(student.id)
        then :
        average==3.2
        weightedAverage>3.2
        median==3.0
    }
}
