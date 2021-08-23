package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TranzactiiRepository;
import com.mycompany.myapp.service.TranzactiiService;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Tranzactii}.
 */
@RestController
@RequestMapping("/api")
public class TranzactiiResource {

    private final Logger log = LoggerFactory.getLogger(TranzactiiResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationTranzactii";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TranzactiiService tranzactiiService;

    private final TranzactiiRepository tranzactiiRepository;

    public TranzactiiResource(TranzactiiService tranzactiiService, TranzactiiRepository tranzactiiRepository) {
        this.tranzactiiService = tranzactiiService;
        this.tranzactiiRepository = tranzactiiRepository;
    }

    /**
     * {@code POST  /tranzactiis} : Create a new tranzactii.
     *
     * @param tranzactiiDTO the tranzactiiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tranzactiiDTO, or with status {@code 400 (Bad Request)} if the tranzactii has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tranzactiis")
    public ResponseEntity<TranzactiiDTO> createTranzactii(@RequestBody TranzactiiDTO tranzactiiDTO) throws URISyntaxException {
        log.debug("REST request to save Tranzactii : {}", tranzactiiDTO);
        if (tranzactiiDTO.getId() != null) {
            throw new BadRequestAlertException("A new tranzactii cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranzactiiDTO result = tranzactiiService.save(tranzactiiDTO);
        return ResponseEntity
            .created(new URI("/api/tranzactiis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tranzactiis/:id} : Updates an existing tranzactii.
     *
     * @param id the id of the tranzactiiDTO to save.
     * @param tranzactiiDTO the tranzactiiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tranzactiiDTO,
     * or with status {@code 400 (Bad Request)} if the tranzactiiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tranzactiiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tranzactiis/{id}")
    public ResponseEntity<TranzactiiDTO> updateTranzactii(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TranzactiiDTO tranzactiiDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tranzactii : {}, {}", id, tranzactiiDTO);
        if (tranzactiiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tranzactiiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tranzactiiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TranzactiiDTO result = tranzactiiService.save(tranzactiiDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tranzactiiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tranzactiis/:id} : Partial updates given fields of an existing tranzactii, field will ignore if it is null
     *
     * @param id the id of the tranzactiiDTO to save.
     * @param tranzactiiDTO the tranzactiiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tranzactiiDTO,
     * or with status {@code 400 (Bad Request)} if the tranzactiiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tranzactiiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tranzactiiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tranzactiis/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TranzactiiDTO> partialUpdateTranzactii(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TranzactiiDTO tranzactiiDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tranzactii partially : {}, {}", id, tranzactiiDTO);
        if (tranzactiiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tranzactiiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tranzactiiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TranzactiiDTO> result = tranzactiiService.partialUpdate(tranzactiiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tranzactiiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tranzactiis} : get all the tranzactiis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tranzactiis in body.
     */
    @GetMapping("/tranzactiis")
    public List<TranzactiiDTO> getAllTranzactiis() {
        log.debug("REST request to get all Tranzactiis");
        return tranzactiiService.findAll();
    }

    /**
     * {@code GET  /tranzactiis/:id} : get the "id" tranzactii.
     *
     * @param id the id of the tranzactiiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tranzactiiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tranzactiis/{id}")
    public ResponseEntity<TranzactiiDTO> getTranzactii(@PathVariable Long id) {
        log.debug("REST request to get Tranzactii : {}", id);
        Optional<TranzactiiDTO> tranzactiiDTO = tranzactiiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tranzactiiDTO);
    }

    /**
     * {@code DELETE  /tranzactiis/:id} : delete the "id" tranzactii.
     *
     * @param id the id of the tranzactiiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tranzactiis/{id}")
    public ResponseEntity<Void> deleteTranzactii(@PathVariable Long id) {
        log.debug("REST request to delete Tranzactii : {}", id);
        tranzactiiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
