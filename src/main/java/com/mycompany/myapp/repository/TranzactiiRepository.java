package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Tranzactii;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tranzactii entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranzactiiRepository extends JpaRepository<Tranzactii, Long> {}
