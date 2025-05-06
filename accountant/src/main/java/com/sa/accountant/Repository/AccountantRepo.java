package com.se.user.Accountant.Repository;

import com.se.user.Accountant.Entity.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountantRepo extends JpaRepository<Accountant, String> {
    Optional<Accountant> findByUsername(String s);
}
