package com.example.SpringLibraryTest3.Services;

import com.example.SpringLibraryTest3.Dto.*;
import com.example.SpringLibraryTest3.Entities.Coupons;
import com.example.SpringLibraryTest3.Entities.Courses;
import com.example.SpringLibraryTest3.Entities.Student;
import com.example.SpringLibraryTest3.Enums.UserRole;
import com.example.SpringLibraryTest3.Repositories.CouponRepository;
import com.example.SpringLibraryTest3.Repositories.CourseRepository;
import com.example.SpringLibraryTest3.Repositories.StudentRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CouponRepository couponRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, CouponRepository couponRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.couponRepository = couponRepository;
    }

    public ResponseEntity<String> signUpStudent(StudentSignUpDto signData) {

        Student student = studentRepository.findByEmail(signData.getEmail());

        if (student != null) {
            return ResponseEntity.status(400).body("Email already in use");
        }

        Student newStudent = new Student(
                signData.getFirstName(),
                signData.getLastName(),
                signData.getEmail(),
                signData.getPassword(),
                UserRole.Student
        );

        studentRepository.save(newStudent);

        return ResponseEntity.ok("Account Created successfully");
    }
    public ResponseEntity<String>loginStudent (StudentLoginDto loginData){
        Student student=studentRepository.findByEmail(loginData.getEmail());
        if (student !=null && student.getPassword().equals(loginData.getPassword())){
            return ResponseEntity.ok("login Successfully");

        }else {
            return ResponseEntity.status(401).body("invalid email or password");
        }
    }



    public ResponseEntity<List<CourseDto>> getAllCourse(){

        List<Courses> courses = courseRepository.findAll();

        List<CourseDto> coursesResponse = new ArrayList<>();

        courses.forEach(course -> {

            CourseDto courseData = new CourseDto(
                    course.getId(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getPrice(),
                    course.getCategory(),
                    course.getInstructor().getFirstName(),
                    course.getInstructor().getLastName(),
                    course.getInstructor().getEmail()
            );

            coursesResponse.add(courseData);
        });

        return ResponseEntity.ok(coursesResponse);

    }

   public ResponseEntity<String> addAccountBalance(AddbalanceDto balanceData) {
        Optional<Student> studentOptional = studentRepository.findById(balanceData.getStudentId());
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setAccountBalance(student.getAccountBalance() + balanceData.getAmount());
            studentRepository.save(student);
            return ResponseEntity.ok("Amount added to balance successfully");
        } else {
            return ResponseEntity.status(404).body("Student not found");
        }
    }


    public ResponseEntity<Student> getStudentProfile(int studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            return ResponseEntity.ok(studentOptional.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<Courses> getCourseByName(String title) {
        Optional<Courses> coursesOptional = courseRepository.findByTitle(title);
        if (coursesOptional.isPresent()) {
            return ResponseEntity.ok(coursesOptional.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public ResponseEntity<List<CourseDto>> getCourseByCategory(String category) {
        List<Courses> courses = courseRepository.findByCategory(category);
        List<CourseDto> courseResponse = new ArrayList<>();
        courses.forEach(course -> {
            CourseDto courseData = new CourseDto(
                    course.getId(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getPrice(),
                    course.getCategory(),
                    course.getInstructor().getFirstName(),
                    course.getInstructor().getLastName(),
                    course.getInstructor().getEmail()
            );
            courseResponse.add(courseData);
        });

        return ResponseEntity.ok(courseResponse);
    }

    public ResponseEntity<List<CourseDto>> getCoursesByPriceRange(double minPrice, double maxPrice) {
        List<Courses> courses = courseRepository.findByPriceBetween(minPrice, maxPrice);
        List<CourseDto> courseResponse = new ArrayList<>();
        courses.forEach(course -> {
            CourseDto courseData = new CourseDto(
                    course.getId(),
                    course.getTitle(),
                    course.getDescription(),
                    course.getPrice(),
                    course.getCategory(),
                    course.getInstructor().getFirstName(),
                    course.getInstructor().getLastName(),
                    course.getInstructor().getEmail()
            );
            courseResponse.add(courseData);
        });
        return ResponseEntity.ok(courseResponse);
    }
    @Transactional
    public ResponseEntity<String> buyCourse(int studentId, int courseId, String code) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Courses> courseOptional = courseRepository.findById(courseId);

        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Student not found");
        }

        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Course not found");
        }

        Student student = studentOptional.get();
        Courses course = courseOptional.get();

        double price = course.getPrice();

        if (code != null && !code.isEmpty()) {
            Optional<Coupons> couponOptional = couponRepository.findByCodeAndCourses(code, course);
            if (couponOptional.isPresent()) {
                Coupons coupon = couponOptional.get();
                price = price - (price * coupon.getDiscountPercentage() / 100);
            } else {
                return ResponseEntity.status(400).body("Invalid coupon code");
            }
        }

        if (student.getAccountBalance() < price) {
            return ResponseEntity.status(400).body("Insufficient balance to buy the course");
        }

        student.setAccountBalance(student.getAccountBalance() - price);
        student.getCoursesBought().add(course);
        studentRepository.save(student);

        return ResponseEntity.ok("Course purchased successfully");
    }

    }

