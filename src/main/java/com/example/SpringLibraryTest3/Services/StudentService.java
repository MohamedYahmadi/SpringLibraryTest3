package com.example.SpringLibraryTest3.Services;

import com.example.SpringLibraryTest3.Dto.*;
import com.example.SpringLibraryTest3.Entities.Courses;
import com.example.SpringLibraryTest3.Entities.Student;
import com.example.SpringLibraryTest3.Enums.UserRole;
import com.example.SpringLibraryTest3.Repositories.CourseRepository;
import com.example.SpringLibraryTest3.Repositories.StudentRepository;
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

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
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
    public ResponseEntity<String>loginStudent (StudentLoginDto loingData){
        Student student=studentRepository.findByEmail(loingData.getEmail());
        if (student !=null && student.getPassword().equals(loingData.getPassword())){
            return ResponseEntity.ok("login Successfuly");

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
    public ResponseEntity<Courses>getCourseByName(String title){
            Optional<Courses>coursesOptional=courseRepository.findByTitle(title);
            if (coursesOptional.isPresent()){
                return ResponseEntity.ok(coursesOptional.get());

            }else{
                return ResponseEntity.status(404).body(null);
            }
    }

}