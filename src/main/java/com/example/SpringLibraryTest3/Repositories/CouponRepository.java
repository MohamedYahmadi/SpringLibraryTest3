package com.example.SpringLibraryTest3.Repositories;

import com.example.SpringLibraryTest3.Entities.Coupons;
import com.example.SpringLibraryTest3.Entities.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupons, Integer> {
    Coupons findByCode(String code);
    Optional<Coupons> findByCodeAndCourses(String code, Courses course);
}
