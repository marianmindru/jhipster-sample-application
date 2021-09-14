package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.enumeration.StareOferta;
import com.mycompany.myapp.repository.OfferRepository;
import com.mycompany.myapp.service.dto.OfferDTO;
import com.mycompany.myapp.service.mapper.OfferMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link OfferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OfferResourceIT {

    private static final String DEFAULT_COD_OFERTA = "AAAAAAAAAA";
    private static final String UPDATED_COD_OFERTA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_START_POLITA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_START_POLITA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_END = LocalDate.now(ZoneId.systemDefault());

    private static final StareOferta DEFAULT_STARE_OFERTA = StareOferta.ACTIV;
    private static final StareOferta UPDATED_STARE_OFERTA = StareOferta.INACTIV;

    private static final String DEFAULT_ID_PRODUS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PRODUS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SUMA_PLATA = new BigDecimal(0);
    private static final BigDecimal UPDATED_SUMA_PLATA = new BigDecimal(1);

    private static final String DEFAULT_ID_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_ID_AGENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/offers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
            .codOferta(DEFAULT_COD_OFERTA)
            .dataStartPolita(DEFAULT_DATA_START_POLITA)
            .dataEnd(DEFAULT_DATA_END)
            .stareOferta(DEFAULT_STARE_OFERTA)
            .idProdus(DEFAULT_ID_PRODUS)
            .valuta(DEFAULT_VALUTA)
            .sumaPlata(DEFAULT_SUMA_PLATA)
            .idAgent(DEFAULT_ID_AGENT);
        return offer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer()
            .codOferta(UPDATED_COD_OFERTA)
            .dataStartPolita(UPDATED_DATA_START_POLITA)
            .dataEnd(UPDATED_DATA_END)
            .stareOferta(UPDATED_STARE_OFERTA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();
        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testOffer.getDataStartPolita()).isEqualTo(DEFAULT_DATA_START_POLITA);
        assertThat(testOffer.getDataEnd()).isEqualTo(DEFAULT_DATA_END);
        assertThat(testOffer.getStareOferta()).isEqualTo(DEFAULT_STARE_OFERTA);
        assertThat(testOffer.getIdProdus()).isEqualTo(DEFAULT_ID_PRODUS);
        assertThat(testOffer.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testOffer.getSumaPlata()).isEqualByComparingTo(DEFAULT_SUMA_PLATA);
        assertThat(testOffer.getIdAgent()).isEqualTo(DEFAULT_ID_AGENT);
    }

    @Test
    @Transactional
    void createOfferWithExistingId() throws Exception {
        // Create the Offer with an existing ID
        offer.setId(1L);
        OfferDTO offerDTO = offerMapper.toDto(offer);

        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodOfertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setCodOferta(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].codOferta").value(hasItem(DEFAULT_COD_OFERTA)))
            .andExpect(jsonPath("$.[*].dataStartPolita").value(hasItem(DEFAULT_DATA_START_POLITA.toString())))
            .andExpect(jsonPath("$.[*].dataEnd").value(hasItem(DEFAULT_DATA_END.toString())))
            .andExpect(jsonPath("$.[*].stareOferta").value(hasItem(DEFAULT_STARE_OFERTA.toString())))
            .andExpect(jsonPath("$.[*].idProdus").value(hasItem(DEFAULT_ID_PRODUS)))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)))
            .andExpect(jsonPath("$.[*].sumaPlata").value(hasItem(sameNumber(DEFAULT_SUMA_PLATA))))
            .andExpect(jsonPath("$.[*].idAgent").value(hasItem(DEFAULT_ID_AGENT)));
    }

    @Test
    @Transactional
    void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc
            .perform(get(ENTITY_API_URL_ID, offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.codOferta").value(DEFAULT_COD_OFERTA))
            .andExpect(jsonPath("$.dataStartPolita").value(DEFAULT_DATA_START_POLITA.toString()))
            .andExpect(jsonPath("$.dataEnd").value(DEFAULT_DATA_END.toString()))
            .andExpect(jsonPath("$.stareOferta").value(DEFAULT_STARE_OFERTA.toString()))
            .andExpect(jsonPath("$.idProdus").value(DEFAULT_ID_PRODUS))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA))
            .andExpect(jsonPath("$.sumaPlata").value(sameNumber(DEFAULT_SUMA_PLATA)))
            .andExpect(jsonPath("$.idAgent").value(DEFAULT_ID_AGENT));
    }

    @Test
    @Transactional
    void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).get();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer
            .codOferta(UPDATED_COD_OFERTA)
            .dataStartPolita(UPDATED_DATA_START_POLITA)
            .dataEnd(UPDATED_DATA_END)
            .stareOferta(UPDATED_STARE_OFERTA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT);
        OfferDTO offerDTO = offerMapper.toDto(updatedOffer);

        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testOffer.getDataStartPolita()).isEqualTo(UPDATED_DATA_START_POLITA);
        assertThat(testOffer.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testOffer.getStareOferta()).isEqualTo(UPDATED_STARE_OFERTA);
        assertThat(testOffer.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testOffer.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testOffer.getSumaPlata()).isEqualTo(UPDATED_SUMA_PLATA);
        assertThat(testOffer.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void putNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer.idAgent(UPDATED_ID_AGENT);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testOffer.getDataStartPolita()).isEqualTo(DEFAULT_DATA_START_POLITA);
        assertThat(testOffer.getDataEnd()).isEqualTo(DEFAULT_DATA_END);
        assertThat(testOffer.getStareOferta()).isEqualTo(DEFAULT_STARE_OFERTA);
        assertThat(testOffer.getIdProdus()).isEqualTo(DEFAULT_ID_PRODUS);
        assertThat(testOffer.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testOffer.getSumaPlata()).isEqualByComparingTo(DEFAULT_SUMA_PLATA);
        assertThat(testOffer.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void fullUpdateOfferWithPatch() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer using partial update
        Offer partialUpdatedOffer = new Offer();
        partialUpdatedOffer.setId(offer.getId());

        partialUpdatedOffer
            .codOferta(UPDATED_COD_OFERTA)
            .dataStartPolita(UPDATED_DATA_START_POLITA)
            .dataEnd(UPDATED_DATA_END)
            .stareOferta(UPDATED_STARE_OFERTA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT);

        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffer))
            )
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testOffer.getDataStartPolita()).isEqualTo(UPDATED_DATA_START_POLITA);
        assertThat(testOffer.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testOffer.getStareOferta()).isEqualTo(UPDATED_STARE_OFERTA);
        assertThat(testOffer.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testOffer.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testOffer.getSumaPlata()).isEqualByComparingTo(UPDATED_SUMA_PLATA);
        assertThat(testOffer.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void patchNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();
        offer.setId(count.incrementAndGet());

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc
            .perform(delete(ENTITY_API_URL_ID, offer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
