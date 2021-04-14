package com.student.learning.repo;

import com.student.learning.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {


    Optional<Student> findByStudentName(String studentName);
}
