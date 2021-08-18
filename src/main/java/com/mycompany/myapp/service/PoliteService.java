package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PoliteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Polite}.
 */
public interface PoliteService {
    /**
     * Save a polite.
     *
     * @param politeDTO the entity to save.
     * @return the persisted entity.
     */
    PoliteDTO save(PoliteDTO politeDTO);

    /**
     * Partially updates a polite.
     *
     * @param politeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PoliteDTO> partialUpdate(PoliteDTO politeDTO);

    /**
     * Get all the polites.
     *
     * @return the list of entities.
     */
    List<PoliteDTO> findAll();
    /**
     * Get all the PoliteDTO where Tranzactii is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PoliteDTO> findAllWhereTranzactiiIsNull();

    /**
     * Get the "id" polite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PoliteDTO> findOne(Long id);

    /**
     * Delete the "id" polite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
