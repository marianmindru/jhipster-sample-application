package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PoliteRepository;
import com.mycompany.myapp.service.PoliteService;
import com.mycompany.myapp.service.dto.PoliteDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Polite}.
 */
@RestController
@RequestMapping("/api")
public class PoliteResource {

    private final Logger log = LoggerFactory.getLogger(PoliteResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationPolite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoliteService politeService;

    private final PoliteRepository politeRepository;

    public PoliteResource(PoliteService politeService, PoliteRepository politeRepository) {
        this.politeService = politeService;
        this.politeRepository = politeRepository;
    }

    /**
     * {@code POST  /polites} : Create a new polite.
     *
     * @param politeDTO the politeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new politeDTO, or with status {@code 400 (Bad Request)} if the polite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/polites")
    public ResponseEntity<PoliteDTO> createPolite(@Valid @RequestBody PoliteDTO politeDTO) throws URISyntaxException {
        log.debug("REST request to save Polite : {}", politeDTO);
        if (politeDTO.getId() != null) {
            throw new BadRequestAlertException("A new polite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoliteDTO result = politeService.save(politeDTO);
        return ResponseEntity
            .created(new URI("/api/polites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /polites/:id} : Updates an existing polite.
     *
     * @param id the id of the politeDTO to save.
     * @param politeDTO the politeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated politeDTO,
     * or with status {@code 400 (Bad Request)} if the politeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the politeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/polites/{id}")
    public ResponseEntity<PoliteDTO> updatePolite(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PoliteDTO politeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Polite : {}, {}", id, politeDTO);
        if (politeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, politeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!politeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PoliteDTO result = politeService.save(politeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, politeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /polites/:id} : Partial updates given fields of an existing polite, field will ignore if it is null
     *
     * @param id the id of the politeDTO to save.
     * @param politeDTO the politeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated politeDTO,
     * or with status {@code 400 (Bad Request)} if the politeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the politeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the politeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/polites/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PoliteDTO> partialUpdatePolite(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PoliteDTO politeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Polite partially : {}, {}", id, politeDTO);
        if (politeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, politeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!politeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PoliteDTO> result = politeService.partialUpdate(politeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, politeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /polites} : get all the polites.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of polites in body.
     */
    @GetMapping("/polites")
    public List<PoliteDTO> getAllPolites() {
        log.debug("REST request to get all Polites");
        return politeService.findAll();
    }

    /**
     * {@code GET  /polites/:id} : get the "id" polite.
     *
     * @param id the id of the politeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the politeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/polites/{id}")
    public ResponseEntity<PoliteDTO> getPolite(@PathVariable Long id) {
        log.debug("REST request to get Polite : {}", id);
        Optional<PoliteDTO> politeDTO = politeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(politeDTO);
    }

    /**
     * {@code DELETE  /polites/:id} : delete the "id" polite.
     *
     * @param id the id of the politeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/polites/{id}")
    public ResponseEntity<Void> deletePolite(@PathVariable Long id) {
        log.debug("REST request to delete Polite : {}", id);
        politeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
