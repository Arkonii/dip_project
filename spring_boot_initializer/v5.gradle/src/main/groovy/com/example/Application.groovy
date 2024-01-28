package com.example

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class Application {

	@Bean
	CommandLineRunner demo(StudentsService studentsService, GradesService gradesService) {
		return { args ->
			// Dodanie nowego studenta
			studentsService.addNewStudent("John Doe")

			// Pobranie dodanego studenta
			def johnDoe = studentsService.getStudentById(1L)
			println("Student ID: ${johnDoe.id}, Name: ${johnDoe.name}")

			// Dodanie nowej oceny dla studenta
			gradesService.addNewGrade("Math", 4.0, johnDoe, 0.8) // Dodano wagę oceny
			gradesService.addNewGrade("Math", 4.0, johnDoe, 0.8) // Dodano wagę oceny
			gradesService.addNewGrade("Math", 5.0, johnDoe, 1.6) // Dodano wagę oceny
			gradesService.addNewGrade("Math", 5.0, johnDoe, 1.6) // Dodano wagę oceny

			// Pobranie dodanej oceny
			def mathGrade = gradesService.getGradeById(1L)
			println("Grade ID: ${mathGrade.id}, Subject: ${mathGrade.nameOfSubject}, Grade: ${mathGrade.grade}, Weight: ${mathGrade.weight}")

			// Dodanie kolejnej oceny dla tego samego studenta
			gradesService.addNewGrade("Physics", 3.0, johnDoe, 0.7) // Dodano wagę oceny

			// Pobranie wszystkich ocen dla studenta
			def allGradesForJohnDoe = johnDoe.grades
			allGradesForJohnDoe.each { grade ->
				println("Grade ID: ${grade.id}, Subject: ${grade.nameOfSubject}, Grade: ${grade.grade}, Weight: ${grade.weight}")
			}

			// Obliczanie średniej arytmetycznej dla danego studenta i przedmiotu
			def average =gradesService.calculateAverageGradeForSubject(johnDoe.id, "Math")
			println("Average Grade for John Doe in Math: $average")

// Obliczanie ważonej średniej dla danego studenta i przedmiotu
			def weightedAverage = gradesService.calculateWeightedAverageGradeForSubject(johnDoe.id, "Math")
			println("Weighted Average Grade for John Doe in Math: $weightedAverage")

// Obliczanie mediany dla danego studenta i przedmiotu
			def median = gradesService.calculateMedianGradeForSubject(johnDoe.id, "Math")
			println("Median Grade for John Doe in Math: $median")

			def weightedForAll=gradesService.calculateWeightedAverageGradeForStudent(johnDoe.id)
			println("Wazona dla wszystkich przedmiotów : $weightedForAll")

		}
	}

	static void main(String[] args) {
		SpringApplication.run(Application, args)
	}
}
