package com.example.SpringLibraryTest3.Repositories;

import com.example.SpringLibraryTest3.Entities.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupons, Integer> {
    Coupons findByCode(String code);
}
