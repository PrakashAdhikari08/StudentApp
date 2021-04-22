package com.student.learning.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.learning.domain.Student;
import com.student.learning.exception.CannotCreateJsonPayloadException;
import com.student.learning.exception.NoUserFoundException;
import com.student.learning.exception.UserNameExistException;
import com.student.learning.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@Slf4j
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
    public ResponseEntity<String> addStudent(@Valid @RequestBody Student student){
        log.info(String.format("the student is: %s", toJson(student)));
        studentService.addStudent(student);
        return new ResponseEntity("Student Added", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{studentID}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer studentID){
        log.info(String.format("the student is: %d", studentID));
        studentService.deleteStudent(studentID);
        return new ResponseEntity<String>("Student Deleted", HttpStatus.OK);
    }

    @ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<String> handleUserNameDuplicateException(UserNameExistException exception){
        log.error(String.format("the student is: %s", exception.getMessage()));
        return new ResponseEntity<>(String.format("%s", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleFieldValidationException(MethodArgumentNotValidException exception){
        log.error(String.format("the student is: %s", exception.getMessage()));
        return new ResponseEntity<>(String.format("%s", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<String> handleNoUserExistWithUserId(NoUserFoundException exception){
        log.error(String.format("the student is: %s", exception.getMessage()));
        return new ResponseEntity<>(String.format("%s", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private String toJson(Student student) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
          return objectMapper.writeValueAsString(student);
        } catch (IOException e) {
            throw new CannotCreateJsonPayloadException("Cannot convert the object to String");
        }
    }
}
