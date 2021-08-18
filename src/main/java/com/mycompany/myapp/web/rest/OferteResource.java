package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.OferteRepository;
import com.mycompany.myapp.service.OferteService;
import com.mycompany.myapp.service.dto.OferteDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Oferte}.
 */
@RestController
@RequestMapping("/api")
public class OferteResource {

    private final Logger log = LoggerFactory.getLogger(OferteResource.class);

    private static final String ENTITY_NAME = "jhipsterSampleApplicationOferte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OferteService oferteService;

    private final OferteRepository oferteRepository;

    public OferteResource(OferteService oferteService, OferteRepository oferteRepository) {
        this.oferteService = oferteService;
        this.oferteRepository = oferteRepository;
    }

    /**
     * {@code POST  /ofertes} : Create a new oferte.
     *
     * @param oferteDTO the oferteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oferteDTO, or with status {@code 400 (Bad Request)} if the oferte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ofertes")
    public ResponseEntity<OferteDTO> createOferte(@RequestBody OferteDTO oferteDTO) throws URISyntaxException {
        log.debug("REST request to save Oferte : {}", oferteDTO);
        if (oferteDTO.getId() != null) {
            throw new BadRequestAlertException("A new oferte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OferteDTO result = oferteService.save(oferteDTO);
        return ResponseEntity
            .created(new URI("/api/ofertes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ofertes/:id} : Updates an existing oferte.
     *
     * @param id the id of the oferteDTO to save.
     * @param oferteDTO the oferteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oferteDTO,
     * or with status {@code 400 (Bad Request)} if the oferteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oferteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ofertes/{id}")
    public ResponseEntity<OferteDTO> updateOferte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OferteDTO oferteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Oferte : {}, {}", id, oferteDTO);
        if (oferteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oferteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oferteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OferteDTO result = oferteService.save(oferteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oferteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ofertes/:id} : Partial updates given fields of an existing oferte, field will ignore if it is null
     *
     * @param id the id of the oferteDTO to save.
     * @param oferteDTO the oferteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oferteDTO,
     * or with status {@code 400 (Bad Request)} if the oferteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the oferteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the oferteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ofertes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OferteDTO> partialUpdateOferte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OferteDTO oferteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Oferte partially : {}, {}", id, oferteDTO);
        if (oferteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oferteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oferteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OferteDTO> result = oferteService.partialUpdate(oferteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oferteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ofertes} : get all the ofertes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ofertes in body.
     */
    @GetMapping("/ofertes")
    public List<OferteDTO> getAllOfertes(@RequestParam(required = false) String filter) {
        if ("tranzactii-is-null".equals(filter)) {
            log.debug("REST request to get all Ofertes where tranzactii is null");
            return oferteService.findAllWhereTranzactiiIsNull();
        }
        log.debug("REST request to get all Ofertes");
        return oferteService.findAll();
    }

    /**
     * {@code GET  /ofertes/:id} : get the "id" oferte.
     *
     * @param id the id of the oferteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oferteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ofertes/{id}")
    public ResponseEntity<OferteDTO> getOferte(@PathVariable Long id) {
        log.debug("REST request to get Oferte : {}", id);
        Optional<OferteDTO> oferteDTO = oferteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oferteDTO);
    }

    /**
     * {@code DELETE  /ofertes/:id} : delete the "id" oferte.
     *
     * @param id the id of the oferteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ofertes/{id}")
    public ResponseEntity<Void> deleteOferte(@PathVariable Long id) {
        log.debug("REST request to delete Oferte : {}", id);
        oferteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
