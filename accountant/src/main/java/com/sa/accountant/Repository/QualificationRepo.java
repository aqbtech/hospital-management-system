package com.sa.accountant.Repository;


import com.sa.accountant.Entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepo extends JpaRepository<Qualification, Long> {
}
