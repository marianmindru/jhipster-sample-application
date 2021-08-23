package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PolicyRepository;
import com.mycompany.myapp.service.PolicyService;
import com.mycompany.myapp.service.dto.PolicyDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Policy}.
 */
@RestController
@RequestMapping("/api")
public class PolicyResource {

    private final Logger log = LoggerFactory.getLogger(PolicyResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationPolicy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyService policyService;

    private final PolicyRepository policyRepository;

    public PolicyResource(PolicyService policyService, PolicyRepository policyRepository) {
        this.policyService = policyService;
        this.policyRepository = policyRepository;
    }

    /**
     * {@code POST  /policies} : Create a new policy.
     *
     * @param policyDTO the policyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyDTO, or with status {@code 400 (Bad Request)} if the policy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policies")
    public ResponseEntity<PolicyDTO> createPolicy(@Valid @RequestBody PolicyDTO policyDTO) throws URISyntaxException {
        log.debug("REST request to save Policy : {}", policyDTO);
        if (policyDTO.getId() != null) {
            throw new BadRequestAlertException("A new policy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyDTO result = policyService.save(policyDTO);
        return ResponseEntity
            .created(new URI("/api/policies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policies/:id} : Updates an existing policy.
     *
     * @param id the id of the policyDTO to save.
     * @param policyDTO the policyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyDTO,
     * or with status {@code 400 (Bad Request)} if the policyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policies/{id}")
    public ResponseEntity<PolicyDTO> updatePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PolicyDTO policyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Policy : {}, {}", id, policyDTO);
        if (policyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PolicyDTO result = policyService.save(policyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /policies/:id} : Partial updates given fields of an existing policy, field will ignore if it is null
     *
     * @param id the id of the policyDTO to save.
     * @param policyDTO the policyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyDTO,
     * or with status {@code 400 (Bad Request)} if the policyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the policyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the policyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/policies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PolicyDTO> partialUpdatePolicy(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PolicyDTO policyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Policy partially : {}, {}", id, policyDTO);
        if (policyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PolicyDTO> result = policyService.partialUpdate(policyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /policies} : get all the policies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policies in body.
     */
    @GetMapping("/policies")
    public List<PolicyDTO> getAllPolicies() {
        log.debug("REST request to get all Policies");
        return policyService.findAll();
    }

    /**
     * {@code GET  /policies/:id} : get the "id" policy.
     *
     * @param id the id of the policyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policies/{id}")
    public ResponseEntity<PolicyDTO> getPolicy(@PathVariable Long id) {
        log.debug("REST request to get Policy : {}", id);
        Optional<PolicyDTO> policyDTO = policyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(policyDTO);
    }

    /**
     * {@code DELETE  /policies/:id} : delete the "id" policy.
     *
     * @param id the id of the policyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policies/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        log.debug("REST request to delete Policy : {}", id);
        policyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
