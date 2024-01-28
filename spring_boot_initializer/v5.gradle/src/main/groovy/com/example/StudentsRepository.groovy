package com.example

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentsRepository extends JpaRepository<Students, Long> {
    // pozostała część interfejsu...
}

