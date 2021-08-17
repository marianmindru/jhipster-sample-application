package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TranzactiiDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Tranzactii}.
 */
public interface TranzactiiService {
    /**
     * Save a tranzactii.
     *
     * @param tranzactiiDTO the entity to save.
     * @return the persisted entity.
     */
    TranzactiiDTO save(TranzactiiDTO tranzactiiDTO);

    /**
     * Partially updates a tranzactii.
     *
     * @param tranzactiiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TranzactiiDTO> partialUpdate(TranzactiiDTO tranzactiiDTO);

    /**
     * Get all the tranzactiis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TranzactiiDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tranzactii.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TranzactiiDTO> findOne(Long id);

    /**
     * Delete the "id" tranzactii.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
