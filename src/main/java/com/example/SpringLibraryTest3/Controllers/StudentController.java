package com.example.SpringLibraryTest3.Controllers;

import com.example.SpringLibraryTest3.Dto.AddbalanceDto;
import com.example.SpringLibraryTest3.Dto.StudentLoginDto;
import com.example.SpringLibraryTest3.Dto.StudentSignUpDto;
import com.example.SpringLibraryTest3.Entities.Courses;
import com.example.SpringLibraryTest3.Entities.Student;
import com.example.SpringLibraryTest3.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/Signup")
    public ResponseEntity<String> signUpStudent(@RequestBody StudentSignUpDto signData) {
        return studentService.signUpStudent(signData);
    }

    @PostMapping("/Login")
    public ResponseEntity<String> loginStudent(@RequestBody StudentLoginDto loginData) {
        return studentService.loginStudent(loginData);
    }
    @GetMapping("/allCourses")
    public List<Courses> getAllCourse() {
        return studentService.getAllCourse();
    }

    @PostMapping("addAccountBalance")
    public ResponseEntity<String> addAccountBalance(@RequestBody AddbalanceDto balanceData) {
        return studentService.addAccountBalance(balanceData);
    }
    @GetMapping("/profile/{studentId}")
    public ResponseEntity<Student>getStudentProfile(@PathVariable int studentId){
        return studentService.getStudentProfile(studentId);
    }

}