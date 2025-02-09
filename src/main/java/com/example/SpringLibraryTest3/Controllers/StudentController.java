package com.example.SpringLibraryTest3.Controllers;

import com.example.SpringLibraryTest3.Dto.AddbalanceDto;
import com.example.SpringLibraryTest3.Dto.CourseDto;
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
    @GetMapping("/all-courses")
    public ResponseEntity<List<CourseDto>> getAllCourse() {
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
    @GetMapping("/course/{title}")
    public ResponseEntity<Courses> getCourseByName(@PathVariable String title) {
        return studentService.getCourseByName(title);
    }
  @GetMapping("/courses-category/{category}")
    public ResponseEntity<List<CourseDto>> getCourseByCategory(@PathVariable String category) {
        return studentService.getCourseByCategory(category);
    }
    @GetMapping("/courses/price-range")
    public ResponseEntity<List<CourseDto>> getCoursesByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return studentService.getCoursesByPriceRange(minPrice, maxPrice);
    }
    @PostMapping("/buyCourse/{studentId}/{courseId}")
    public ResponseEntity<String> buyCourse(@PathVariable int studentId, @PathVariable int courseId) {
        return studentService.buyCourse(studentId, courseId);
    }




}