package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Oferte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Oferte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OferteRepository extends JpaRepository<Oferte, Long> {}
