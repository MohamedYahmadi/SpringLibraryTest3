package com.example.SpringLibraryTest3.Repositories;

import com.example.SpringLibraryTest3.Entities.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Courses , Integer> {
    Optional<Courses> findByTitle(String title);
}
