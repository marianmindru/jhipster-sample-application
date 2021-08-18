package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Tranzactii;
import com.mycompany.myapp.domain.enumeration.StatusPlata;
import com.mycompany.myapp.repository.TranzactiiRepository;
import com.mycompany.myapp.service.dto.TranzactiiDTO;
import com.mycompany.myapp.service.mapper.TranzactiiMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TranzactiiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TranzactiiResourceIT {

    private static final String DEFAULT_COD_OFERTA = "AAAAAAAAAA";
    private static final String UPDATED_COD_OFERTA = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_OFERTA = 1L;
    private static final Long UPDATED_ID_OFERTA = 2L;

    private static final String DEFAULT_NUMAR_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_NUMAR_POLITA = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_POLITA = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_POLITA = 1L;
    private static final Long UPDATED_ID_POLITA = 2L;

    private static final String DEFAULT_SUMA_PLATITA = "AAAAAAAAAA";
    private static final String UPDATED_SUMA_PLATITA = "BBBBBBBBBB";

    private static final StatusPlata DEFAULT_STATUS_PLATA = StatusPlata.NEW;
    private static final StatusPlata UPDATED_STATUS_PLATA = StatusPlata.WAITING;

    private static final LocalDate DEFAULT_DATA_PLATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PLATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_USER = 1L;
    private static final Long UPDATED_ID_USER = 2L;

    private static final String DEFAULT_REFERINTA_ING = "AAAAAAAAAA";
    private static final String UPDATED_REFERINTA_ING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tranzactiis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TranzactiiRepository tranzactiiRepository;

    @Autowired
    private TranzactiiMapper tranzactiiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTranzactiiMockMvc;

    private Tranzactii tranzactii;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tranzactii createEntity(EntityManager em) {
        Tranzactii tranzactii = new Tranzactii()
            .codOferta(DEFAULT_COD_OFERTA)
            .idOferta(DEFAULT_ID_OFERTA)
            .numarPolita(DEFAULT_NUMAR_POLITA)
            .seriePolita(DEFAULT_SERIE_POLITA)
            .idPolita(DEFAULT_ID_POLITA)
            .sumaPlatita(DEFAULT_SUMA_PLATITA)
            .statusPlata(DEFAULT_STATUS_PLATA)
            .dataPlata(DEFAULT_DATA_PLATA)
            .valuta(DEFAULT_VALUTA)
            .idUser(DEFAULT_ID_USER)
            .referintaIng(DEFAULT_REFERINTA_ING);
        return tranzactii;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tranzactii createUpdatedEntity(EntityManager em) {
        Tranzactii tranzactii = new Tranzactii()
            .codOferta(UPDATED_COD_OFERTA)
            .idOferta(UPDATED_ID_OFERTA)
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .idPolita(UPDATED_ID_POLITA)
            .sumaPlatita(UPDATED_SUMA_PLATITA)
            .statusPlata(UPDATED_STATUS_PLATA)
            .dataPlata(UPDATED_DATA_PLATA)
            .valuta(UPDATED_VALUTA)
            .idUser(UPDATED_ID_USER)
            .referintaIng(UPDATED_REFERINTA_ING);
        return tranzactii;
    }

    @BeforeEach
    public void initTest() {
        tranzactii = createEntity(em);
    }

    @Test
    @Transactional
    void createTranzactii() throws Exception {
        int databaseSizeBeforeCreate = tranzactiiRepository.findAll().size();
        // Create the Tranzactii
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);
        restTranzactiiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO)))
            .andExpect(status().isCreated());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeCreate + 1);
        Tranzactii testTranzactii = tranzactiiList.get(tranzactiiList.size() - 1);
        assertThat(testTranzactii.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testTranzactii.getIdOferta()).isEqualTo(DEFAULT_ID_OFERTA);
        assertThat(testTranzactii.getNumarPolita()).isEqualTo(DEFAULT_NUMAR_POLITA);
        assertThat(testTranzactii.getSeriePolita()).isEqualTo(DEFAULT_SERIE_POLITA);
        assertThat(testTranzactii.getIdPolita()).isEqualTo(DEFAULT_ID_POLITA);
        assertThat(testTranzactii.getSumaPlatita()).isEqualTo(DEFAULT_SUMA_PLATITA);
        assertThat(testTranzactii.getStatusPlata()).isEqualTo(DEFAULT_STATUS_PLATA);
        assertThat(testTranzactii.getDataPlata()).isEqualTo(DEFAULT_DATA_PLATA);
        assertThat(testTranzactii.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testTranzactii.getIdUser()).isEqualTo(DEFAULT_ID_USER);
        assertThat(testTranzactii.getReferintaIng()).isEqualTo(DEFAULT_REFERINTA_ING);
    }

    @Test
    @Transactional
    void createTranzactiiWithExistingId() throws Exception {
        // Create the Tranzactii with an existing ID
        tranzactii.setId(1L);
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);

        int databaseSizeBeforeCreate = tranzactiiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranzactiiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTranzactiis() throws Exception {
        // Initialize the database
        tranzactiiRepository.saveAndFlush(tranzactii);

        // Get all the tranzactiiList
        restTranzactiiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tranzactii.getId().intValue())))
            .andExpect(jsonPath("$.[*].codOferta").value(hasItem(DEFAULT_COD_OFERTA)))
            .andExpect(jsonPath("$.[*].idOferta").value(hasItem(DEFAULT_ID_OFERTA.intValue())))
            .andExpect(jsonPath("$.[*].numarPolita").value(hasItem(DEFAULT_NUMAR_POLITA)))
            .andExpect(jsonPath("$.[*].seriePolita").value(hasItem(DEFAULT_SERIE_POLITA)))
            .andExpect(jsonPath("$.[*].idPolita").value(hasItem(DEFAULT_ID_POLITA.intValue())))
            .andExpect(jsonPath("$.[*].sumaPlatita").value(hasItem(DEFAULT_SUMA_PLATITA)))
            .andExpect(jsonPath("$.[*].statusPlata").value(hasItem(DEFAULT_STATUS_PLATA.toString())))
            .andExpect(jsonPath("$.[*].dataPlata").value(hasItem(DEFAULT_DATA_PLATA.toString())))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.intValue())))
            .andExpect(jsonPath("$.[*].referintaIng").value(hasItem(DEFAULT_REFERINTA_ING)));
    }

    @Test
    @Transactional
    void getTranzactii() throws Exception {
        // Initialize the database
        tranzactiiRepository.saveAndFlush(tranzactii);

        // Get the tranzactii
        restTranzactiiMockMvc
            .perform(get(ENTITY_API_URL_ID, tranzactii.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tranzactii.getId().intValue()))
            .andExpect(jsonPath("$.codOferta").value(DEFAULT_COD_OFERTA))
            .andExpect(jsonPath("$.idOferta").value(DEFAULT_ID_OFERTA.intValue()))
            .andExpect(jsonPath("$.numarPolita").value(DEFAULT_NUMAR_POLITA))
            .andExpect(jsonPath("$.seriePolita").value(DEFAULT_SERIE_POLITA))
            .andExpect(jsonPath("$.idPolita").value(DEFAULT_ID_POLITA.intValue()))
            .andExpect(jsonPath("$.sumaPlatita").value(DEFAULT_SUMA_PLATITA))
            .andExpect(jsonPath("$.statusPlata").value(DEFAULT_STATUS_PLATA.toString()))
            .andExpect(jsonPath("$.dataPlata").value(DEFAULT_DATA_PLATA.toString()))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER.intValue()))
            .andExpect(jsonPath("$.referintaIng").value(DEFAULT_REFERINTA_ING));
    }

    @Test
    @Transactional
    void getNonExistingTranzactii() throws Exception {
        // Get the tranzactii
        restTranzactiiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTranzactii() throws Exception {
        // Initialize the database
        tranzactiiRepository.saveAndFlush(tranzactii);

        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();

        // Update the tranzactii
        Tranzactii updatedTranzactii = tranzactiiRepository.findById(tranzactii.getId()).get();
        // Disconnect from session so that the updates on updatedTranzactii are not directly saved in db
        em.detach(updatedTranzactii);
        updatedTranzactii
            .codOferta(UPDATED_COD_OFERTA)
            .idOferta(UPDATED_ID_OFERTA)
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .idPolita(UPDATED_ID_POLITA)
            .sumaPlatita(UPDATED_SUMA_PLATITA)
            .statusPlata(UPDATED_STATUS_PLATA)
            .dataPlata(UPDATED_DATA_PLATA)
            .valuta(UPDATED_VALUTA)
            .idUser(UPDATED_ID_USER)
            .referintaIng(UPDATED_REFERINTA_ING);
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(updatedTranzactii);

        restTranzactiiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tranzactiiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
        Tranzactii testTranzactii = tranzactiiList.get(tranzactiiList.size() - 1);
        assertThat(testTranzactii.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testTranzactii.getIdOferta()).isEqualTo(UPDATED_ID_OFERTA);
        assertThat(testTranzactii.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testTranzactii.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testTranzactii.getIdPolita()).isEqualTo(UPDATED_ID_POLITA);
        assertThat(testTranzactii.getSumaPlatita()).isEqualTo(UPDATED_SUMA_PLATITA);
        assertThat(testTranzactii.getStatusPlata()).isEqualTo(UPDATED_STATUS_PLATA);
        assertThat(testTranzactii.getDataPlata()).isEqualTo(UPDATED_DATA_PLATA);
        assertThat(testTranzactii.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testTranzactii.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testTranzactii.getReferintaIng()).isEqualTo(UPDATED_REFERINTA_ING);
    }

    @Test
    @Transactional
    void putNonExistingTranzactii() throws Exception {
        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();
        tranzactii.setId(count.incrementAndGet());

        // Create the Tranzactii
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranzactiiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tranzactiiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTranzactii() throws Exception {
        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();
        tranzactii.setId(count.incrementAndGet());

        // Create the Tranzactii
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranzactiiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTranzactii() throws Exception {
        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();
        tranzactii.setId(count.incrementAndGet());

        // Create the Tranzactii
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranzactiiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTranzactiiWithPatch() throws Exception {
        // Initialize the database
        tranzactiiRepository.saveAndFlush(tranzactii);

        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();

        // Update the tranzactii using partial update
        Tranzactii partialUpdatedTranzactii = new Tranzactii();
        partialUpdatedTranzactii.setId(tranzactii.getId());

        partialUpdatedTranzactii
            .idOferta(UPDATED_ID_OFERTA)
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .idUser(UPDATED_ID_USER)
            .referintaIng(UPDATED_REFERINTA_ING);

        restTranzactiiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranzactii.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranzactii))
            )
            .andExpect(status().isOk());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
        Tranzactii testTranzactii = tranzactiiList.get(tranzactiiList.size() - 1);
        assertThat(testTranzactii.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testTranzactii.getIdOferta()).isEqualTo(UPDATED_ID_OFERTA);
        assertThat(testTranzactii.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testTranzactii.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testTranzactii.getIdPolita()).isEqualTo(DEFAULT_ID_POLITA);
        assertThat(testTranzactii.getSumaPlatita()).isEqualTo(DEFAULT_SUMA_PLATITA);
        assertThat(testTranzactii.getStatusPlata()).isEqualTo(DEFAULT_STATUS_PLATA);
        assertThat(testTranzactii.getDataPlata()).isEqualTo(DEFAULT_DATA_PLATA);
        assertThat(testTranzactii.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testTranzactii.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testTranzactii.getReferintaIng()).isEqualTo(UPDATED_REFERINTA_ING);
    }

    @Test
    @Transactional
    void fullUpdateTranzactiiWithPatch() throws Exception {
        // Initialize the database
        tranzactiiRepository.saveAndFlush(tranzactii);

        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();

        // Update the tranzactii using partial update
        Tranzactii partialUpdatedTranzactii = new Tranzactii();
        partialUpdatedTranzactii.setId(tranzactii.getId());

        partialUpdatedTranzactii
            .codOferta(UPDATED_COD_OFERTA)
            .idOferta(UPDATED_ID_OFERTA)
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .idPolita(UPDATED_ID_POLITA)
            .sumaPlatita(UPDATED_SUMA_PLATITA)
            .statusPlata(UPDATED_STATUS_PLATA)
            .dataPlata(UPDATED_DATA_PLATA)
            .valuta(UPDATED_VALUTA)
            .idUser(UPDATED_ID_USER)
            .referintaIng(UPDATED_REFERINTA_ING);

        restTranzactiiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranzactii.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranzactii))
            )
            .andExpect(status().isOk());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
        Tranzactii testTranzactii = tranzactiiList.get(tranzactiiList.size() - 1);
        assertThat(testTranzactii.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testTranzactii.getIdOferta()).isEqualTo(UPDATED_ID_OFERTA);
        assertThat(testTranzactii.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testTranzactii.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testTranzactii.getIdPolita()).isEqualTo(UPDATED_ID_POLITA);
        assertThat(testTranzactii.getSumaPlatita()).isEqualTo(UPDATED_SUMA_PLATITA);
        assertThat(testTranzactii.getStatusPlata()).isEqualTo(UPDATED_STATUS_PLATA);
        assertThat(testTranzactii.getDataPlata()).isEqualTo(UPDATED_DATA_PLATA);
        assertThat(testTranzactii.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testTranzactii.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testTranzactii.getReferintaIng()).isEqualTo(UPDATED_REFERINTA_ING);
    }

    @Test
    @Transactional
    void patchNonExistingTranzactii() throws Exception {
        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();
        tranzactii.setId(count.incrementAndGet());

        // Create the Tranzactii
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranzactiiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tranzactiiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTranzactii() throws Exception {
        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();
        tranzactii.setId(count.incrementAndGet());

        // Create the Tranzactii
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranzactiiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTranzactii() throws Exception {
        int databaseSizeBeforeUpdate = tranzactiiRepository.findAll().size();
        tranzactii.setId(count.incrementAndGet());

        // Create the Tranzactii
        TranzactiiDTO tranzactiiDTO = tranzactiiMapper.toDto(tranzactii);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranzactiiMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tranzactiiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tranzactii in the database
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTranzactii() throws Exception {
        // Initialize the database
        tranzactiiRepository.saveAndFlush(tranzactii);

        int databaseSizeBeforeDelete = tranzactiiRepository.findAll().size();

        // Delete the tranzactii
        restTranzactiiMockMvc
            .perform(delete(ENTITY_API_URL_ID, tranzactii.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tranzactii> tranzactiiList = tranzactiiRepository.findAll();
        assertThat(tranzactiiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
