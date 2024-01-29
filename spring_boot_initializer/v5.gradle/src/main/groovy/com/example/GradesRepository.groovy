package com.example

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GradesRepository extends JpaRepository<Grades, Long> {
    List<Grades> findAllByStudentAndNameOfSubject(Students student, String nameOfSubject);
    List<Grades> findAllByStudent(Students student);

}
