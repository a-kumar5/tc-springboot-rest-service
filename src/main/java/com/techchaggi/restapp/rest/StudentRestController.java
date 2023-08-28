package com.techchaggi.restapp.rest;

import com.techchaggi.restapp.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    // define @PostConstruct to load the student data ... only once
    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();
        theStudents.add(new Student("Bruce", "Wayne"));
        theStudents.add(new Student("Clark", "Kent"));
        theStudents.add(new Student("Alfred", "Penneyworth"));
    }

    // define a endpoint for "/students" - return a list of students

    @GetMapping("/students")
    public List<Student> getStudents() {
        return theStudents;
    }

    // define a endpoint for "/students/{studentId}" - retrieve student by id
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        // check the studentId against the list size
        if (studentId >= 9999){
            throw new StudentOutOfBoundException("Student id out of bound - " + studentId);
        }
        else if ((studentId >= theStudents.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }
        return theStudents.get(studentId);
    }

    // Add an exception handler using @ExceptionHandler

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
        // create a StudentErrorResponse

        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        //return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleOutOfBoundException(StudentOutOfBoundException exc){
        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(402);
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.nanoTime());

        return new ResponseEntity<>(error, HttpStatusCode.valueOf(402));
    }
}









