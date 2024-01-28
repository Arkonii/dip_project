package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentsService {
    @Autowired
    private StudentsRepository studentsRepository

    def saveStudent(Students student) {
        studentsRepository.save(student)
    }

    def getStudentById(Long id) {
        studentsRepository.findById(id).orElse(null)
    }

    def addNewStudent(String name) {
        def student = new Students(name: name)
        saveStudent(student)
    }
}