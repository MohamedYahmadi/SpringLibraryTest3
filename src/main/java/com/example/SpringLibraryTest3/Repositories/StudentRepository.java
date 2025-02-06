package com.example.SpringLibraryTest3.Repositories;

import com.example.SpringLibraryTest3.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByEmail(String email);
}

