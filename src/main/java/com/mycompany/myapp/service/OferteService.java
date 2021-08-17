package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.OferteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Oferte}.
 */
public interface OferteService {
    /**
     * Save a oferte.
     *
     * @param oferteDTO the entity to save.
     * @return the persisted entity.
     */
    OferteDTO save(OferteDTO oferteDTO);

    /**
     * Partially updates a oferte.
     *
     * @param oferteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OferteDTO> partialUpdate(OferteDTO oferteDTO);

    /**
     * Get all the ofertes.
     *
     * @return the list of entities.
     */
    List<OferteDTO> findAll();
    /**
     * Get all the OferteDTO where T is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<OferteDTO> findAllWhereTIsNull();

    /**
     * Get the "id" oferte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OferteDTO> findOne(Long id);

    /**
     * Delete the "id" oferte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
