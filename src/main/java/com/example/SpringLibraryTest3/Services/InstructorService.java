package com.example.SpringLibraryTest3.Services;

import com.example.SpringLibraryTest3.Dto.CreateCourseDto;
import com.example.SpringLibraryTest3.Dto.InstructorLoginDto;
import com.example.SpringLibraryTest3.Dto.InstructorProfileDto;
import com.example.SpringLibraryTest3.Dto.InstructorSignUpDto;
import com.example.SpringLibraryTest3.Entities.Courses;
import com.example.SpringLibraryTest3.Entities.Instructor;
import com.example.SpringLibraryTest3.Entities.Student;
import com.example.SpringLibraryTest3.Enums.UserRole;
import com.example.SpringLibraryTest3.Repositories.CourseRepository;
import com.example.SpringLibraryTest3.Repositories.InstructorRepository;
import com.example.SpringLibraryTest3.Repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }


    public ResponseEntity<String> signUpInstructor(InstructorSignUpDto signData) {

        Instructor instructorOptional = instructorRepository.findByEmail(signData.getEmail());

        if (instructorOptional != null) {
            return ResponseEntity.ok("Account already created");
        }

        Instructor instructor = new Instructor(
                signData.getFirstName(),
                signData.getLastName(),
                signData.getEmail(),
                signData.getPassword(),
                UserRole.Instructor
        );

        instructorRepository.save(instructor);
        return ResponseEntity.ok("Account Created successfully");
    }
    public ResponseEntity<String>LoginInstructor(InstructorLoginDto loginData){
        Instructor instructor =instructorRepository.findByEmail(loginData.getEmail());
        if (instructor !=null && instructor.getPassword().equals(loginData.getPassword())){
            return ResponseEntity.ok("login Successfully");

        }else {
            return ResponseEntity.status(403).body("invalid email or password");
        }
    }
   public ResponseEntity<String> CreateCourse(CreateCourseDto courseData, int instructorId) {
       Optional<Instructor> instructor = instructorRepository.findById(instructorId);
       if (instructor.isEmpty()) {
           return ResponseEntity.status(404).body("Instructor with the id: " + instructorId + " not found");
       }
       Courses courses = new Courses(
               courseData.getTitle(),
               courseData.getDescription(),
               courseData.getPrice(),
               courseData.getCategory()
       );
       courses.setInstructor(instructor.get());
       courseRepository.save(courses);
       return ResponseEntity.ok("Course created successfully");
   }

    public ResponseEntity<String>UpdateCourse( int id ,CreateCourseDto courseData){
        Optional<Courses> course =courseRepository.findById(id);
        if (course.isEmpty()){
            return  ResponseEntity.status(404).body(" Course with the id : "+id+" not found");
        }
        Courses updateCourse =course.get();
        updateCourse.setTitle(courseData.getTitle());
        updateCourse.setDescription(courseData.getDescription());
        updateCourse.setPrice(courseData.getPrice());
        updateCourse.setCategory(courseData.getCategory());
        courseRepository.save(updateCourse);

        return  ResponseEntity.ok(" Course with the id : "+id+" has been updated successfuly");
    }

    @Transactional
    public ResponseEntity<String>deleteCourse(int id){

        Optional<Courses> courses = courseRepository.findById(id);

        if (courses.isEmpty()) return ResponseEntity.status(404).body("Course not found");

        double price = courses.get().getPrice();

        courseRepository.delete(courses.get());

        for (Student student : courses.get().getStudents()) {
            student.setAccountBalance(student.getAccountBalance() + price);
            studentRepository.save(student);
        }

        return ResponseEntity.ok(" Course with the id : "+id+" has been deleted successfuly");
    }

   public ResponseEntity<InstructorProfileDto> getInstructorProfile(int instructorId) {
       Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
       if (instructorOptional.isPresent()) {

           InstructorProfileDto profile = new InstructorProfileDto(
                   instructorOptional.get().getId(),
                   instructorOptional.get().getFirstName(),
                   instructorOptional.get().getLastName(),
                   instructorOptional.get().getEmail(),
                   instructorOptional.get().getBio()
           );

           return ResponseEntity.ok(profile);
       } else {
           return ResponseEntity.status(404).body(null);
       }
   }

   public ResponseEntity<String> getCourseSales(int courseId) {
        Optional<Courses> courses = courseRepository.findById(courseId);

        if (courses.isEmpty()) {
            return ResponseEntity.status(404).body("course not found");
        }

        int sales = courses.get().getStudents().size();

        return ResponseEntity.status(200).body("Sales : " + sales);

   }

}