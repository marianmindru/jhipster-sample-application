package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ClientsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Clients}.
 */
public interface ClientsService {
    /**
     * Save a clients.
     *
     * @param clientsDTO the entity to save.
     * @return the persisted entity.
     */
    ClientsDTO save(ClientsDTO clientsDTO);

    /**
     * Partially updates a clients.
     *
     * @param clientsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClientsDTO> partialUpdate(ClientsDTO clientsDTO);

    /**
     * Get all the clients.
     *
     * @return the list of entities.
     */
    List<ClientsDTO> findAll();

    /**
     * Get the "id" clients.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientsDTO> findOne(Long id);

    /**
     * Delete the "id" clients.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
