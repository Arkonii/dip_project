package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional;


/**
 * Klasa StudentsService odpowiada za komunukacje z bazą danych za pomocą interfejsu StudentsRepository
 */
@Service
class StudentsService {
    /**
     * Deklaracja repozytorium - interfejsu ktory komunikuję się z bazą danych (z tabelą Students)
     */
    @Autowired
    private StudentsRepository studentsRepository //Deklaracja repozytorium - interfejsu ktory komunikuję się z bazą danych (z tabelą Students)

    /**
     * funkcja zapisująca studenta za pomocą repozytorium
     * @param student obiekt Student z klasy Student.groovy
     * @return obiekt Student z bazy danych ktory zostal zapisany
     */
    def saveStudent(Students student) {
        studentsRepository.save(student)
    }
    /**
     * funkcja pobierająca studenta za pomocą repozytorium
     * @param id Studenta w tabeli Students
     * @return obiekt Student z bazy danych pod wskazanych id
     */
    def getStudentById(Long id) {
        studentsRepository.findById(id).orElse(null)
    }
    /**
     * funkcja dodająca studenta do bazy danych
     * @param name Imię które zostanie dodane do bazy danych
     * @return obiekt Student ktory zostanie dodany
     */
    def addNewStudent(String name) {
        def student = new Students(name: name)
        saveStudent(student)
    }
    /**
     * funkcja usuwająca studenta pod podanym Id
     * @param StudentId id studenta w tabeli Students
     * @return obiekt Student ktory został usunięty (null)
     */
    def deleteStudentById(Long StudentId){
        studentsRepository.findById(StudentId)ifPresent {
            studentsRepository.delete(it)
        }
    }
    /**
     * funkcja usuwajaca całą tabelę Students
     * @return wszystkie obiekty Student które zostały usunięte (null)
     */
    @Transactional
    def deleteAllStudents() {
        studentsRepository.deleteAll();
    }
}
