package com.student.learning.service;

import com.student.learning.domain.Student;
import com.student.learning.exception.NoUserFoundException;
import com.student.learning.exception.UserNameExistException;
import com.student.learning.repo.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void addStudent(Student student) throws UserNameExistException {
        Optional<Student> existName = studentRepository.findByStudentName(student.getStudentName());
        if(existName.isEmpty())
            studentRepository.save(student);
        else
            throw new UserNameExistException(String.format("Username already exist %s", student.getStudentName()));

    }

    public void deleteStudent (Integer studentID) throws RuntimeException{
        Optional<Student> student = studentRepository.findById(studentID);
        student.orElseThrow(()-> new NoUserFoundException(String.format("No user found with Id %d", studentID)));
        studentRepository.delete(student.get());

    }
}
