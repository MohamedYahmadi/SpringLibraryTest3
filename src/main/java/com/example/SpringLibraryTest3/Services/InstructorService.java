package com.example.SpringLibraryTest3.Services;

import com.example.SpringLibraryTest3.Dto.CreateCourseDto;
import com.example.SpringLibraryTest3.Dto.InstructorLoginDto;
import com.example.SpringLibraryTest3.Dto.InstructorSignUpDto;
import com.example.SpringLibraryTest3.Entities.Courses;
import com.example.SpringLibraryTest3.Entities.Instructor;
import com.example.SpringLibraryTest3.Enums.UserRole;
import com.example.SpringLibraryTest3.Repositories.CourseRepository;
import com.example.SpringLibraryTest3.Repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
    }


    public ResponseEntity<String> signUpInstructor(InstructorSignUpDto signData) {
        Instructor instructor = new Instructor(
                signData.getFirstName(),
                signData.getLastName(),
                signData.getEmail(),
                signData.getPassword(),
                UserRole.valueOf(signData.getRole())
        );
        instructorRepository.save(instructor);
        return ResponseEntity.ok("Account Created successfully");
    }
    public ResponseEntity<String>LoginInstructor(InstructorLoginDto loginData){
        Instructor instructor =instructorRepository.findByEmail(loginData.getEmail());
        if (instructor !=null && instructor.getPassword().equals(loginData.getPassword())){
            return ResponseEntity.ok("login Successfully");

        }else {
            return ResponseEntity.status(401).body("invalid email or password");
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
        }Courses updateCourse =course.get();
        updateCourse.setTitle(courseData.getTitle());
        updateCourse.setDescription(courseData.getDescription());
        updateCourse.setPrice(courseData.getPrice());
        updateCourse.setCategory(courseData.getCategory());
        courseRepository.save(updateCourse);


        return  ResponseEntity.ok(" Course with the id : "+id+" has been updated successfuly");
    }
    public ResponseEntity<String>deleteCourse(int id){
if (!courseRepository.existsById(id)){
return ResponseEntity.status(404).body(" Course with the id : "+id+" does not exist");
}
courseRepository.deleteById(id);
        return ResponseEntity.ok(" Course with the id : "+id+" has been deleted successfuly");
    }
   public ResponseEntity<Instructor> getInstructorProfile(int instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if (instructorOptional.isPresent()) {
            return ResponseEntity.ok(instructorOptional.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}