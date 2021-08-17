package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Oferte;
import com.mycompany.myapp.domain.enumeration.StareOferta;
import com.mycompany.myapp.repository.OferteRepository;
import com.mycompany.myapp.service.dto.OferteDTO;
import com.mycompany.myapp.service.mapper.OferteMapper;
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
 * Integration tests for the {@link OferteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OferteResourceIT {

    private static final String DEFAULT_COD_OFERTA = "AAAAAAAAAA";
    private static final String UPDATED_COD_OFERTA = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_START_POLITA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_START_POLITA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StareOferta DEFAULT_STARE_OFERTA = StareOferta.ACTIV;
    private static final StareOferta UPDATED_STARE_OFERTA = StareOferta.INACTIV;

    private static final String DEFAULT_ID_PRODUS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PRODUS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final String DEFAULT_SUMA_PLATA = "AAAAAAAAAA";
    private static final String UPDATED_SUMA_PLATA = "BBBBBBBBBB";

    private static final String DEFAULT_ID_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_ID_AGENT = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_USER = 1L;
    private static final Long UPDATED_ID_USER = 2L;

    private static final String ENTITY_API_URL = "/api/ofertes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OferteRepository oferteRepository;

    @Autowired
    private OferteMapper oferteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOferteMockMvc;

    private Oferte oferte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oferte createEntity(EntityManager em) {
        Oferte oferte = new Oferte()
            .codOferta(DEFAULT_COD_OFERTA)
            .dataStartPolita(DEFAULT_DATA_START_POLITA)
            .dataEnd(DEFAULT_DATA_END)
            .stareOferta(DEFAULT_STARE_OFERTA)
            .idProdus(DEFAULT_ID_PRODUS)
            .valuta(DEFAULT_VALUTA)
            .sumaPlata(DEFAULT_SUMA_PLATA)
            .idAgent(DEFAULT_ID_AGENT)
            .idUser(DEFAULT_ID_USER);
        return oferte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oferte createUpdatedEntity(EntityManager em) {
        Oferte oferte = new Oferte()
            .codOferta(UPDATED_COD_OFERTA)
            .dataStartPolita(UPDATED_DATA_START_POLITA)
            .dataEnd(UPDATED_DATA_END)
            .stareOferta(UPDATED_STARE_OFERTA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT)
            .idUser(UPDATED_ID_USER);
        return oferte;
    }

    @BeforeEach
    public void initTest() {
        oferte = createEntity(em);
    }

    @Test
    @Transactional
    void createOferte() throws Exception {
        int databaseSizeBeforeCreate = oferteRepository.findAll().size();
        // Create the Oferte
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);
        restOferteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oferteDTO)))
            .andExpect(status().isCreated());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeCreate + 1);
        Oferte testOferte = oferteList.get(oferteList.size() - 1);
        assertThat(testOferte.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testOferte.getDataStartPolita()).isEqualTo(DEFAULT_DATA_START_POLITA);
        assertThat(testOferte.getDataEnd()).isEqualTo(DEFAULT_DATA_END);
        assertThat(testOferte.getStareOferta()).isEqualTo(DEFAULT_STARE_OFERTA);
        assertThat(testOferte.getIdProdus()).isEqualTo(DEFAULT_ID_PRODUS);
        assertThat(testOferte.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testOferte.getSumaPlata()).isEqualTo(DEFAULT_SUMA_PLATA);
        assertThat(testOferte.getIdAgent()).isEqualTo(DEFAULT_ID_AGENT);
        assertThat(testOferte.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    }

    @Test
    @Transactional
    void createOferteWithExistingId() throws Exception {
        // Create the Oferte with an existing ID
        oferte.setId(1L);
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);

        int databaseSizeBeforeCreate = oferteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOferteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oferteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOfertes() throws Exception {
        // Initialize the database
        oferteRepository.saveAndFlush(oferte);

        // Get all the oferteList
        restOferteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oferte.getId().intValue())))
            .andExpect(jsonPath("$.[*].codOferta").value(hasItem(DEFAULT_COD_OFERTA)))
            .andExpect(jsonPath("$.[*].dataStartPolita").value(hasItem(DEFAULT_DATA_START_POLITA.toString())))
            .andExpect(jsonPath("$.[*].dataEnd").value(hasItem(DEFAULT_DATA_END.toString())))
            .andExpect(jsonPath("$.[*].stareOferta").value(hasItem(DEFAULT_STARE_OFERTA.toString())))
            .andExpect(jsonPath("$.[*].idProdus").value(hasItem(DEFAULT_ID_PRODUS)))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)))
            .andExpect(jsonPath("$.[*].sumaPlata").value(hasItem(DEFAULT_SUMA_PLATA)))
            .andExpect(jsonPath("$.[*].idAgent").value(hasItem(DEFAULT_ID_AGENT)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER.intValue())));
    }

    @Test
    @Transactional
    void getOferte() throws Exception {
        // Initialize the database
        oferteRepository.saveAndFlush(oferte);

        // Get the oferte
        restOferteMockMvc
            .perform(get(ENTITY_API_URL_ID, oferte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oferte.getId().intValue()))
            .andExpect(jsonPath("$.codOferta").value(DEFAULT_COD_OFERTA))
            .andExpect(jsonPath("$.dataStartPolita").value(DEFAULT_DATA_START_POLITA.toString()))
            .andExpect(jsonPath("$.dataEnd").value(DEFAULT_DATA_END.toString()))
            .andExpect(jsonPath("$.stareOferta").value(DEFAULT_STARE_OFERTA.toString()))
            .andExpect(jsonPath("$.idProdus").value(DEFAULT_ID_PRODUS))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA))
            .andExpect(jsonPath("$.sumaPlata").value(DEFAULT_SUMA_PLATA))
            .andExpect(jsonPath("$.idAgent").value(DEFAULT_ID_AGENT))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOferte() throws Exception {
        // Get the oferte
        restOferteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOferte() throws Exception {
        // Initialize the database
        oferteRepository.saveAndFlush(oferte);

        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();

        // Update the oferte
        Oferte updatedOferte = oferteRepository.findById(oferte.getId()).get();
        // Disconnect from session so that the updates on updatedOferte are not directly saved in db
        em.detach(updatedOferte);
        updatedOferte
            .codOferta(UPDATED_COD_OFERTA)
            .dataStartPolita(UPDATED_DATA_START_POLITA)
            .dataEnd(UPDATED_DATA_END)
            .stareOferta(UPDATED_STARE_OFERTA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT)
            .idUser(UPDATED_ID_USER);
        OferteDTO oferteDTO = oferteMapper.toDto(updatedOferte);

        restOferteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oferteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oferteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
        Oferte testOferte = oferteList.get(oferteList.size() - 1);
        assertThat(testOferte.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testOferte.getDataStartPolita()).isEqualTo(UPDATED_DATA_START_POLITA);
        assertThat(testOferte.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testOferte.getStareOferta()).isEqualTo(UPDATED_STARE_OFERTA);
        assertThat(testOferte.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testOferte.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testOferte.getSumaPlata()).isEqualTo(UPDATED_SUMA_PLATA);
        assertThat(testOferte.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testOferte.getIdUser()).isEqualTo(UPDATED_ID_USER);
    }

    @Test
    @Transactional
    void putNonExistingOferte() throws Exception {
        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();
        oferte.setId(count.incrementAndGet());

        // Create the Oferte
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOferteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oferteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oferteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOferte() throws Exception {
        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();
        oferte.setId(count.incrementAndGet());

        // Create the Oferte
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOferteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oferteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOferte() throws Exception {
        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();
        oferte.setId(count.incrementAndGet());

        // Create the Oferte
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOferteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oferteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOferteWithPatch() throws Exception {
        // Initialize the database
        oferteRepository.saveAndFlush(oferte);

        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();

        // Update the oferte using partial update
        Oferte partialUpdatedOferte = new Oferte();
        partialUpdatedOferte.setId(oferte.getId());

        partialUpdatedOferte
            .dataStartPolita(UPDATED_DATA_START_POLITA)
            .dataEnd(UPDATED_DATA_END)
            .stareOferta(UPDATED_STARE_OFERTA)
            .idProdus(UPDATED_ID_PRODUS)
            .idAgent(UPDATED_ID_AGENT);

        restOferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOferte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOferte))
            )
            .andExpect(status().isOk());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
        Oferte testOferte = oferteList.get(oferteList.size() - 1);
        assertThat(testOferte.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testOferte.getDataStartPolita()).isEqualTo(UPDATED_DATA_START_POLITA);
        assertThat(testOferte.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testOferte.getStareOferta()).isEqualTo(UPDATED_STARE_OFERTA);
        assertThat(testOferte.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testOferte.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testOferte.getSumaPlata()).isEqualTo(DEFAULT_SUMA_PLATA);
        assertThat(testOferte.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testOferte.getIdUser()).isEqualTo(DEFAULT_ID_USER);
    }

    @Test
    @Transactional
    void fullUpdateOferteWithPatch() throws Exception {
        // Initialize the database
        oferteRepository.saveAndFlush(oferte);

        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();

        // Update the oferte using partial update
        Oferte partialUpdatedOferte = new Oferte();
        partialUpdatedOferte.setId(oferte.getId());

        partialUpdatedOferte
            .codOferta(UPDATED_COD_OFERTA)
            .dataStartPolita(UPDATED_DATA_START_POLITA)
            .dataEnd(UPDATED_DATA_END)
            .stareOferta(UPDATED_STARE_OFERTA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT)
            .idUser(UPDATED_ID_USER);

        restOferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOferte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOferte))
            )
            .andExpect(status().isOk());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
        Oferte testOferte = oferteList.get(oferteList.size() - 1);
        assertThat(testOferte.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testOferte.getDataStartPolita()).isEqualTo(UPDATED_DATA_START_POLITA);
        assertThat(testOferte.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testOferte.getStareOferta()).isEqualTo(UPDATED_STARE_OFERTA);
        assertThat(testOferte.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testOferte.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testOferte.getSumaPlata()).isEqualTo(UPDATED_SUMA_PLATA);
        assertThat(testOferte.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
        assertThat(testOferte.getIdUser()).isEqualTo(UPDATED_ID_USER);
    }

    @Test
    @Transactional
    void patchNonExistingOferte() throws Exception {
        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();
        oferte.setId(count.incrementAndGet());

        // Create the Oferte
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oferteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oferteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOferte() throws Exception {
        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();
        oferte.setId(count.incrementAndGet());

        // Create the Oferte
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOferteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oferteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOferte() throws Exception {
        int databaseSizeBeforeUpdate = oferteRepository.findAll().size();
        oferte.setId(count.incrementAndGet());

        // Create the Oferte
        OferteDTO oferteDTO = oferteMapper.toDto(oferte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOferteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(oferteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Oferte in the database
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOferte() throws Exception {
        // Initialize the database
        oferteRepository.saveAndFlush(oferte);

        int databaseSizeBeforeDelete = oferteRepository.findAll().size();

        // Delete the oferte
        restOferteMockMvc
            .perform(delete(ENTITY_API_URL_ID, oferte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Oferte> oferteList = oferteRepository.findAll();
        assertThat(oferteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
