package com.example.SpringLibraryTest3.Repositories;
import com.example.SpringLibraryTest3.Entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    Instructor findByEmail(String email);
}
