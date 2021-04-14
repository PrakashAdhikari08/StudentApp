package com.student.learning.service;

import com.student.learning.domain.Gender;
import com.student.learning.domain.Student;
import com.student.learning.exception.NoUserFoundException;
import com.student.learning.exception.UserNameExistException;
import com.student.learning.repo.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    private static final Integer STUDENT_ID = 12345;

    @BeforeEach
    void setUp(){
        studentService = new StudentService(studentRepository);
    }


    @Test
    void canGetAllStudents() {
        //when
        studentService.getAllStudents();
        //then
        verify(studentRepository).findAll();

    }

    @Test
    void addStudent() {
        Student student = createStudent();
        //when
        studentService.addStudent(student);
        //then
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(argumentCaptor.capture());

        Student capturedStudent = argumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    private Student createStudent(){
        return Student.builder()
                .studentID(STUDENT_ID)
                .studentName("Prakash")
                .gender(Gender.MALE)
                .build();
    }

    @Test
    void throwExceptionWhenUsernameTaken(){
        Student student = createStudent();
        studentService.addStudent(student);

        given(studentRepository.findByStudentName(student.getStudentName())).willReturn(Optional.of(student));

        assertThatThrownBy(()-> studentService.addStudent(student)).isInstanceOf(UserNameExistException.class)
                .hasMessageContaining(String.format("Username already exist %s", student.getStudentName()));
//        verify(studentRepository, never()).save(any());
    }

    @Test
    void checkDoTheUserExistToBeDeleted(){
        boolean isEmpty = studentRepository.findById(5).isEmpty();
        assertThat(isEmpty).isTrue();
    }
    @Test
    void deleteStudent() {
        Student student = createStudent();
        given(studentRepository.findById(anyInt())).willReturn(Optional.of(student));

        studentService.deleteStudent(student.getStudentID());

        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void userNotFoundExceptionWhileDeleting(){
        Student student = createStudent();
        given(studentRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThatThrownBy(()-> studentService.deleteStudent(student.getStudentID())).isInstanceOf(NoUserFoundException.class)
                .hasMessageContaining(String.format("No user found with Id %d", student.getStudentID()));
//        verify(studentRepository, never()).save(any());

    }
}