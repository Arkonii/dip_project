package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/students")
class StudentsController {

    @Autowired
    private StudentsService studentsService

    @PostMapping
    ResponseEntity addNewStudent(@RequestParam String name) {
        studentsService.addNewStudent(name)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/{id}")
    ResponseEntity getStudentById(@PathVariable Long id) {
        def student = studentsService.getStudentById(id)
        return student ?
                ResponseEntity.ok(student) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
}
