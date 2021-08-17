package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Polite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Polite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoliteRepository extends JpaRepository<Polite, Long> {}
