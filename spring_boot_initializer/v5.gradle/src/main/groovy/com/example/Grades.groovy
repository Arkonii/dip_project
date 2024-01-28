package com.example

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.*


@Entity
class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String nameOfSubject
    double grade
    double weight

    @ManyToOne
    @JoinColumn(name = "student_id")
    Students student
}