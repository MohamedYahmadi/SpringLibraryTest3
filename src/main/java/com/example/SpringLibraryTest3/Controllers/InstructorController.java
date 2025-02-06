package com.example.SpringLibraryTest3.Controllers;

import com.example.SpringLibraryTest3.Dto.CreateCourseDto;
import com.example.SpringLibraryTest3.Dto.InstructorLoginDto;
import com.example.SpringLibraryTest3.Dto.InstructorSignUpDto;
import com.example.SpringLibraryTest3.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping("/Signup")
    public ResponseEntity<String> signUpInstructor(@RequestBody InstructorSignUpDto signData) {
        return instructorService.signUpInstructor(signData);
    }

    @PostMapping("/Login")
    public ResponseEntity<String> LoginInstructor(@RequestBody InstructorLoginDto loginData) {
        return instructorService.LoginInstructor(loginData);
    }
    @PostMapping("/createCourse")
    public ResponseEntity<String>CreateCourse(@RequestBody CreateCourseDto courseData){
        return instructorService.CreateCourse(courseData);
    }
    @PutMapping ("updateCourse/{id}")
    public ResponseEntity<String>UpdateCourse(@PathVariable("id") int id,@RequestBody CreateCourseDto courseData){
        return instructorService.UpdateCourse(id,courseData);

    }
    @DeleteMapping("deleteCourse/{id}")
    public ResponseEntity<String>deleteCourse(@PathVariable("id") int id ){
        return instructorService.deleteCourse(id);

    }
}