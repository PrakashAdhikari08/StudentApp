package com.student.learning.controller;

import com.student.learning.domain.Student;
import com.student.learning.exception.NoUserFoundException;
import com.student.learning.exception.UserNameExistException;
import com.student.learning.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.datatransfer.StringSelection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return new ResponseEntity("Student Added", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{studentID}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer studentID){
        studentService.deleteStudent(studentID);
        return new ResponseEntity<String>("Student Deleted", HttpStatus.OK);
    }

    @ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<String> handleUserNameDuplicateException(UserNameExistException exception){
        return new ResponseEntity<>(String.format("%s", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<String> handleNoUserExistWithUserId(NoUserFoundException exception){
        return new ResponseEntity<>(String.format("%s", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
