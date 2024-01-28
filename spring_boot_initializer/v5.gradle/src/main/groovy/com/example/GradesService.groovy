package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GradesService {
    @Autowired
    private GradesRepository gradesRepository
    @Autowired
    private StudentsRepository studentsRepository
    @Autowired
    private StudentsController studentsController

    def saveGrade(Grades grade) {
        gradesRepository.save(grade)
    }

    def getGradeById(Long id) {
        gradesRepository.findById(id).orElse(null)
    }

    def addNewGrade(String nameOfSubject, double grade, Students student, double weight) {
        def newGrade = new Grades(nameOfSubject: nameOfSubject, grade: grade, student: student, weight: weight)
        saveGrade(newGrade)
    }
    def getGradesByStudentAndSubject(Students student, String subject) {
        gradesRepository.findAllByStudentAndNameOfSubject(student, subject)
    }
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
}
