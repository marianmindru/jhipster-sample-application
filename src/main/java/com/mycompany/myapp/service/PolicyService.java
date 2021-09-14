package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PolicyDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Policy}.
 */
public interface PolicyService {
    /**
     * Save a policy.
     *
     * @param policyDTO the entity to save.
     * @return the persisted entity.
     */
    PolicyDTO save(PolicyDTO policyDTO);

    /**
     * Partially updates a policy.
     *
     * @param policyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PolicyDTO> partialUpdate(PolicyDTO policyDTO);

    /**
     * Get all the policies.
     *
     * @return the list of entities.
     */
    List<PolicyDTO> findAll();

    /**
     * Get the "id" policy.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PolicyDTO> findOne(Long id);

    /**
     * Delete the "id" policy.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
