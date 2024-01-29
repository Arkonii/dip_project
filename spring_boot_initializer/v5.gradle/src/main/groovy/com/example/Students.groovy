package com.example

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.*



@Entity
class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String name

    @OneToMany(mappedBy = "student", orphanRemoval = true, fetch = FetchType.EAGER)
    List<Grades> grades
}
