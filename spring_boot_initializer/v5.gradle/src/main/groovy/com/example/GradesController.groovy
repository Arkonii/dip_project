package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/grades")
class GradesController {
    @Autowired
    private GradesService gradesService

    @GetMapping("/{id}")
    def getGradeById(@PathVariable Long id) {
        gradesService.getGradeById(id)
    }

    @PostMapping("/add")
    def addNewGrade(@RequestParam String nameOfSubject, @RequestParam double grade, @RequestParam Long studentId, @RequestParam double weight) {
        def student = new Students(id: studentId)
        gradesService.addNewGrade(nameOfSubject, grade, student, weight)
    }
}
