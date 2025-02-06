package com.example.SpringLibraryTest3.Services;

import com.example.SpringLibraryTest3.Dto.AddbalanceDto;
import com.example.SpringLibraryTest3.Dto.StudentLoginDto;
import com.example.SpringLibraryTest3.Dto.StudentSignUpDto;
import com.example.SpringLibraryTest3.Entities.Courses;
import com.example.SpringLibraryTest3.Entities.Student;
import com.example.SpringLibraryTest3.Enums.UserRole;
import com.example.SpringLibraryTest3.Repositories.CourseRepository;
import com.example.SpringLibraryTest3.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        Student student = new Student(
                signData.getFirstName(),
                signData.getLastName(),
                signData.getEmail(),
                signData.getPassword(),
                UserRole.valueOf(signData.getRole())

        );
        studentRepository.save(student);
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
    public List<Courses>getAllCourse(){
        return courseRepository.findAll();
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

}