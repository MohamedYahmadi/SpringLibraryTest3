package com.example.SpringLibraryTest3.Controllers;

import com.example.SpringLibraryTest3.Dto.*;
import com.example.SpringLibraryTest3.Entities.Instructor;
import com.example.SpringLibraryTest3.Entities.Student;
import com.example.SpringLibraryTest3.Services.InstructorService;
import org.apache.catalina.User;
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

    @PostMapping("/create-course/{instructorId}")
    public ResponseEntity<String> createCourse(@RequestBody CreateCourseDto courseDto, @PathVariable("instructorId") int instructorId) {
        return instructorService.CreateCourse(courseDto, instructorId);
    }

    @PutMapping("update-course/{id}")
    public ResponseEntity<String> UpdateCourse(@PathVariable("id") int id, @RequestBody CreateCourseDto courseData) {
        return instructorService.UpdateCourse(id, courseData);

    }

    @DeleteMapping("delete-course/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") int id) {
        return instructorService.deleteCourse(id);

    }

    @GetMapping("/profile/{instructorId}")
    public ResponseEntity<InstructorProfileDto> getInstructorProfile(@PathVariable int instructorId) {
        return instructorService.getInstructorProfile(instructorId);
    }

    @GetMapping("sales/{courseId}")
    public ResponseEntity<String> getCourseSales(@PathVariable("courseId") int id) {
        return instructorService.getCourseSales(id);
    }

    @PostMapping("/create-coupon/{instructorId}")
    public ResponseEntity<String> createCoupon(@RequestBody CouponDto couponDto, @PathVariable int instructorId) {
        return instructorService.createCoupon(couponDto, instructorId);
    }



    @DeleteMapping("/delete-coupon/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable int couponId) {
        return instructorService.deleteCoupon(couponId);
    }

}