package com.student.learning.repo;

import com.student.learning.domain.Gender;
import com.student.learning.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldCheckTheStudentWithNameExistsOrNot() {

        //given
        Student student = Student.builder()
                            .studentName("Prakash")
                            .gender(Gender.MALE)
                            .build();
        //when
        studentRepository.save(student);
        //then
        Optional<Student> studentExpected = studentRepository.findByStudentName(student.getStudentName());
        Optional<Student> studentExpected1 = studentRepository.findByStudentName("Ramesh");

        boolean exist = !studentExpected.isEmpty();


        assertThat(exist).isTrue();
        assertThat(studentExpected1.isEmpty()).isNotEqualTo(false);


    }
}