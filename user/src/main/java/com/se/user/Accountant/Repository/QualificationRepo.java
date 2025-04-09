package com.se.user.Accountant.Repository;

import com.se.user.Accountant.Entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepo extends JpaRepository<Qualification, Long> {
}
