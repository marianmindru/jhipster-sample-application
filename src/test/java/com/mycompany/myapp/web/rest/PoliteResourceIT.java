package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Polite;
import com.mycompany.myapp.repository.PoliteRepository;
import com.mycompany.myapp.service.dto.PoliteDTO;
import com.mycompany.myapp.service.mapper.PoliteMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PoliteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PoliteResourceIT {

    private static final String DEFAULT_NUMAR_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_NUMAR_POLITA = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_POLITA = "BBBBBBBBBB";

    private static final String DEFAULT_COD_OFERTA = "AAAAAAAAAA";
    private static final String UPDATED_COD_OFERTA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PERIOADA = 1;
    private static final Integer UPDATED_PERIOADA = 2;

    private static final String DEFAULT_ID_PRODUS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PRODUS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final String DEFAULT_SUMA_PLATA = "AAAAAAAAAA";
    private static final String UPDATED_SUMA_PLATA = "BBBBBBBBBB";

    private static final String DEFAULT_ID_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_ID_AGENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_USER = 1;
    private static final Integer UPDATED_ID_USER = 2;

    private static final String ENTITY_API_URL = "/api/polites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PoliteRepository politeRepository;

    @Autowired
    private PoliteMapper politeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoliteMockMvc;

    private Polite polite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Polite createEntity(EntityManager em) {
        Polite polite = new Polite()
            .numarPolita(DEFAULT_NUMAR_POLITA)
            .seriePolita(DEFAULT_SERIE_POLITA)
            .codOferta(DEFAULT_COD_OFERTA)
            .dataStart(DEFAULT_DATA_START)
            .dataEnd(DEFAULT_DATA_END)
            .perioada(DEFAULT_PERIOADA)
            .idProdus(DEFAULT_ID_PRODUS)
            .valuta(DEFAULT_VALUTA)
            .sumaPlata(DEFAULT_SUMA_PLATA)
            .idAgent(DEFAULT_ID_AGENT)
            .idUser(DEFAULT_ID_USER);
        return polite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Polite createUpdatedEntity(EntityManager em) {
        Polite polite = new Polite()
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .codOferta(UPDATED_COD_OFERTA)
            .dataStart(UPDATED_DATA_START)
            .dataEnd(UPDATED_DATA_END)
            .perioada(UPDATED_PERIOADA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT)
            .idUser(UPDATED_ID_USER);
        return polite;
    }

    @BeforeEach
    public void initTest() {
        polite = createEntity(em);
    }

    @Test
    @Transactional
    void createPolite() throws Exception {
        int databaseSizeBeforeCreate = politeRepository.findAll().size();
        // Create the Polite
        PoliteDTO politeDTO = politeMapper.toDto(polite);
        restPoliteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(politeDTO)))
            .andExpect(status().isCreated());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeCreate + 1);
        Polite testPolite = politeList.get(politeList.size() - 1);
        assertThat(testPolite.getNumarPolita()).isEqualTo(DEFAULT_NUMAR_POLITA);
        assertThat(testPolite.getSeriePolita()).isEqualTo(DEFAULT_SERIE_POLITA);
        assertThat(testPolite.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testPolite.getDataStart()).isEqualTo(DEFAULT_DATA_START);
        assertThat(testPolite.getDataEnd()).isEqualTo(DEFAULT_DATA_END);
        assertThat(testPolite.getPerioada()).isEqualTo(DEFAULT_PERIOADA);
        assertThat(testPolite.getIdProdus()).isEqualTo(DEFAULT_ID_PRODUS);
        assertThat(testPolite.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testPolite.getSumaPlata()).isEqualTo(DEFAULT_SUMA_PLATA);
        assertThat(testPolite.getIdAgent()).isEqualTo(DEFAULT_ID_AGENT);
        assertThat(testPolite.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    }

    @Test
    @Transactional
    void createPoliteWithExistingId() throws Exception {
        // Create the Polite with an existing ID
        polite.setId(1L);
        PoliteDTO politeDTO = politeMapper.toDto(polite);

        int databaseSizeBeforeCreate = politeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoliteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(politeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPolites() throws Exception {
        // Initialize the database
        politeRepository.saveAndFlush(polite);

        // Get all the politeList
        restPoliteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(polite.getId().intValue())))
            .andExpect(jsonPath("$.[*].numarPolita").value(hasItem(DEFAULT_NUMAR_POLITA)))
            .andExpect(jsonPath("$.[*].seriePolita").value(hasItem(DEFAULT_SERIE_POLITA)))
            .andExpect(jsonPath("$.[*].codOferta").value(hasItem(DEFAULT_COD_OFERTA)))
            .andExpect(jsonPath("$.[*].dataStart").value(hasItem(DEFAULT_DATA_START.toString())))
            .andExpect(jsonPath("$.[*].dataEnd").value(hasItem(DEFAULT_DATA_END.toString())))
            .andExpect(jsonPath("$.[*].perioada").value(hasItem(DEFAULT_PERIOADA)))
            .andExpect(jsonPath("$.[*].idProdus").value(hasItem(DEFAULT_ID_PRODUS)))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)))
            .andExpect(jsonPath("$.[*].sumaPlata").value(hasItem(DEFAULT_SUMA_PLATA)))
            .andExpect(jsonPath("$.[*].idAgent").value(hasItem(DEFAULT_ID_AGENT)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER)));
    }

    @Test
    @Transactional
    void getPolite() throws Exception {
        // Initialize the database
        politeRepository.saveAndFlush(polite);

        // Get the polite
        restPoliteMockMvc
            .perform(get(ENTITY_API_URL_ID, polite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(polite.getId().intValue()))
            .andExpect(jsonPath("$.numarPolita").value(DEFAULT_NUMAR_POLITA))
            .andExpect(jsonPath("$.seriePolita").value(DEFAULT_SERIE_POLITA))
            .andExpect(jsonPath("$.codOferta").value(DEFAULT_COD_OFERTA))
            .andExpect(jsonPath("$.dataStart").value(DEFAULT_DATA_START.toString()))
            .andExpect(jsonPath("$.dataEnd").value(DEFAULT_DATA_END.toString()))
            .andExpect(jsonPath("$.perioada").value(DEFAULT_PERIOADA))
            .andExpect(jsonPath("$.idProdus").value(DEFAULT_ID_PRODUS))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA))
            .andExpect(jsonPath("$.sumaPlata").value(DEFAULT_SUMA_PLATA))
            .andExpect(jsonPath("$.idAgent").value(DEFAULT_ID_AGENT))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER));
    }

    @Test
    @Transactional
    void getNonExistingPolite() throws Exception {
        // Get the polite
        restPoliteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPolite() throws Exception {
        // Initialize the database
        politeRepository.saveAndFlush(polite);

        int databaseSizeBeforeUpdate = politeRepository.findAll().size();

        // Update the polite
        Polite updatedPolite = politeRepository.findById(polite.getId()).get();
        // Disconnect from session so that the updates on updatedPolite are not directly saved in db
        em.detach(updatedPolite);
        updatedPolite
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .codOferta(UPDATED_COD_OFERTA)
            .dataStart(UPDATED_DATA_START)
            .dataEnd(UPDATED_DATA_END)
            .perioada(UPDATED_PERIOADA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT)
            .idUser(UPDATED_ID_USER);
        PoliteDTO politeDTO = politeMapper.toDto(updatedPolite);

        restPoliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, politeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(politeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
        Polite testPolite = politeList.get(politeList.size() - 1);
        assertThat(testPolite.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testPolite.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testPolite.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testPolite.getDataStart()).isEqualTo(UPDATED_DATA_START);
        assertThat(testPolite.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testPolite.getPerioada()).isEqualTo(UPDATED_PERIOADA);
        assertThat(testPolite.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testPolite.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testPolite.getSumaPlata()).isEqualTo(UPDATED_SUMA_PLATA);
        assertThat(testPolite.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testPolite.getIdUser()).isEqualTo(UPDATED_ID_USER);
    }

    @Test
    @Transactional
    void putNonExistingPolite() throws Exception {
        int databaseSizeBeforeUpdate = politeRepository.findAll().size();
        polite.setId(count.incrementAndGet());

        // Create the Polite
        PoliteDTO politeDTO = politeMapper.toDto(polite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, politeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(politeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPolite() throws Exception {
        int databaseSizeBeforeUpdate = politeRepository.findAll().size();
        polite.setId(count.incrementAndGet());

        // Create the Polite
        PoliteDTO politeDTO = politeMapper.toDto(polite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(politeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPolite() throws Exception {
        int databaseSizeBeforeUpdate = politeRepository.findAll().size();
        polite.setId(count.incrementAndGet());

        // Create the Polite
        PoliteDTO politeDTO = politeMapper.toDto(polite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoliteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(politeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePoliteWithPatch() throws Exception {
        // Initialize the database
        politeRepository.saveAndFlush(polite);

        int databaseSizeBeforeUpdate = politeRepository.findAll().size();

        // Update the polite using partial update
        Polite partialUpdatedPolite = new Polite();
        partialUpdatedPolite.setId(polite.getId());

        partialUpdatedPolite
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .perioada(UPDATED_PERIOADA)
            .idAgent(UPDATED_ID_AGENT);

        restPoliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolite))
            )
            .andExpect(status().isOk());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
        Polite testPolite = politeList.get(politeList.size() - 1);
        assertThat(testPolite.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testPolite.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testPolite.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testPolite.getDataStart()).isEqualTo(DEFAULT_DATA_START);
        assertThat(testPolite.getDataEnd()).isEqualTo(DEFAULT_DATA_END);
        assertThat(testPolite.getPerioada()).isEqualTo(UPDATED_PERIOADA);
        assertThat(testPolite.getIdProdus()).isEqualTo(DEFAULT_ID_PRODUS);
        assertThat(testPolite.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testPolite.getSumaPlata()).isEqualTo(DEFAULT_SUMA_PLATA);
        assertThat(testPolite.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testPolite.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    }

    @Test
    @Transactional
    void fullUpdatePoliteWithPatch() throws Exception {
        // Initialize the database
        politeRepository.saveAndFlush(polite);

        int databaseSizeBeforeUpdate = politeRepository.findAll().size();

        // Update the polite using partial update
        Polite partialUpdatedPolite = new Polite();
        partialUpdatedPolite.setId(polite.getId());

        partialUpdatedPolite
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .codOferta(UPDATED_COD_OFERTA)
            .dataStart(UPDATED_DATA_START)
            .dataEnd(UPDATED_DATA_END)
            .perioada(UPDATED_PERIOADA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT)
            .idUser(UPDATED_ID_USER);

        restPoliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolite))
            )
            .andExpect(status().isOk());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
        Polite testPolite = politeList.get(politeList.size() - 1);
        assertThat(testPolite.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testPolite.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testPolite.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testPolite.getDataStart()).isEqualTo(UPDATED_DATA_START);
        assertThat(testPolite.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testPolite.getPerioada()).isEqualTo(UPDATED_PERIOADA);
        assertThat(testPolite.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testPolite.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testPolite.getSumaPlata()).isEqualTo(UPDATED_SUMA_PLATA);
        assertThat(testPolite.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testPolite.getIdUser()).isEqualTo(UPDATED_ID_USER);
    }

    @Test
    @Transactional
    void patchNonExistingPolite() throws Exception {
        int databaseSizeBeforeUpdate = politeRepository.findAll().size();
        polite.setId(count.incrementAndGet());

        // Create the Polite
        PoliteDTO politeDTO = politeMapper.toDto(polite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, politeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(politeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPolite() throws Exception {
        int databaseSizeBeforeUpdate = politeRepository.findAll().size();
        polite.setId(count.incrementAndGet());

        // Create the Polite
        PoliteDTO politeDTO = politeMapper.toDto(polite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(politeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPolite() throws Exception {
        int databaseSizeBeforeUpdate = politeRepository.findAll().size();
        polite.setId(count.incrementAndGet());

        // Create the Polite
        PoliteDTO politeDTO = politeMapper.toDto(polite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPoliteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(politeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Polite in the database
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePolite() throws Exception {
        // Initialize the database
        politeRepository.saveAndFlush(polite);

        int databaseSizeBeforeDelete = politeRepository.findAll().size();

        // Delete the polite
        restPoliteMockMvc
            .perform(delete(ENTITY_API_URL_ID, polite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Polite> politeList = politeRepository.findAll();
        assertThat(politeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
