package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.repository.PolicyRepository;
import com.mycompany.myapp.service.dto.PolicyDTO;
import com.mycompany.myapp.service.mapper.PolicyMapper;
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
 * Integration tests for the {@link PolicyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PolicyResourceIT {

    private static final String DEFAULT_NUMAR_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_NUMAR_POLITA = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_POLITA = "BBBBBBBBBB";

    private static final String DEFAULT_COD_OFERTA = "AAAAAAAAAA";
    private static final String UPDATED_COD_OFERTA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_END = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PERIOADA = 1;
    private static final Integer UPDATED_PERIOADA = 2;

    private static final String DEFAULT_ID_PRODUS = "AAAAAAAAAA";
    private static final String UPDATED_ID_PRODUS = "BBBBBBBBBB";

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SUMA_PLATA = new BigDecimal(0);
    private static final BigDecimal UPDATED_SUMA_PLATA = new BigDecimal(1);

    private static final String DEFAULT_ID_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_ID_AGENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/policies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolicyMockMvc;

    private Policy policy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createEntity(EntityManager em) {
        Policy policy = new Policy()
            .numarPolita(DEFAULT_NUMAR_POLITA)
            .seriePolita(DEFAULT_SERIE_POLITA)
            .codOferta(DEFAULT_COD_OFERTA)
            .dataStart(DEFAULT_DATA_START)
            .dataEnd(DEFAULT_DATA_END)
            .perioada(DEFAULT_PERIOADA)
            .idProdus(DEFAULT_ID_PRODUS)
            .valuta(DEFAULT_VALUTA)
            .sumaPlata(DEFAULT_SUMA_PLATA)
            .idAgent(DEFAULT_ID_AGENT);
        return policy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createUpdatedEntity(EntityManager em) {
        Policy policy = new Policy()
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .codOferta(UPDATED_COD_OFERTA)
            .dataStart(UPDATED_DATA_START)
            .dataEnd(UPDATED_DATA_END)
            .perioada(UPDATED_PERIOADA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT);
        return policy;
    }

    @BeforeEach
    public void initTest() {
        policy = createEntity(em);
    }

    @Test
    @Transactional
    void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();
        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);
        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getNumarPolita()).isEqualTo(DEFAULT_NUMAR_POLITA);
        assertThat(testPolicy.getSeriePolita()).isEqualTo(DEFAULT_SERIE_POLITA);
        assertThat(testPolicy.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testPolicy.getDataStart()).isEqualTo(DEFAULT_DATA_START);
        assertThat(testPolicy.getDataEnd()).isEqualTo(DEFAULT_DATA_END);
        assertThat(testPolicy.getPerioada()).isEqualTo(DEFAULT_PERIOADA);
        assertThat(testPolicy.getIdProdus()).isEqualTo(DEFAULT_ID_PRODUS);
        assertThat(testPolicy.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testPolicy.getSumaPlata()).isEqualByComparingTo(DEFAULT_SUMA_PLATA);
        assertThat(testPolicy.getIdAgent()).isEqualTo(DEFAULT_ID_AGENT);
    }

    @Test
    @Transactional
    void createPolicyWithExistingId() throws Exception {
        // Create the Policy with an existing ID
        policy.setId(1L);
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumarPolitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setNumarPolita(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSeriePolitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setSeriePolita(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodOfertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setCodOferta(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPolicies() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].numarPolita").value(hasItem(DEFAULT_NUMAR_POLITA)))
            .andExpect(jsonPath("$.[*].seriePolita").value(hasItem(DEFAULT_SERIE_POLITA)))
            .andExpect(jsonPath("$.[*].codOferta").value(hasItem(DEFAULT_COD_OFERTA)))
            .andExpect(jsonPath("$.[*].dataStart").value(hasItem(DEFAULT_DATA_START.toString())))
            .andExpect(jsonPath("$.[*].dataEnd").value(hasItem(DEFAULT_DATA_END.toString())))
            .andExpect(jsonPath("$.[*].perioada").value(hasItem(DEFAULT_PERIOADA)))
            .andExpect(jsonPath("$.[*].idProdus").value(hasItem(DEFAULT_ID_PRODUS)))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)))
            .andExpect(jsonPath("$.[*].sumaPlata").value(hasItem(sameNumber(DEFAULT_SUMA_PLATA))))
            .andExpect(jsonPath("$.[*].idAgent").value(hasItem(DEFAULT_ID_AGENT)));
    }

    @Test
    @Transactional
    void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL_ID, policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.numarPolita").value(DEFAULT_NUMAR_POLITA))
            .andExpect(jsonPath("$.seriePolita").value(DEFAULT_SERIE_POLITA))
            .andExpect(jsonPath("$.codOferta").value(DEFAULT_COD_OFERTA))
            .andExpect(jsonPath("$.dataStart").value(DEFAULT_DATA_START.toString()))
            .andExpect(jsonPath("$.dataEnd").value(DEFAULT_DATA_END.toString()))
            .andExpect(jsonPath("$.perioada").value(DEFAULT_PERIOADA))
            .andExpect(jsonPath("$.idProdus").value(DEFAULT_ID_PRODUS))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA))
            .andExpect(jsonPath("$.sumaPlata").value(sameNumber(DEFAULT_SUMA_PLATA)))
            .andExpect(jsonPath("$.idAgent").value(DEFAULT_ID_AGENT));
    }

    @Test
    @Transactional
    void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        Policy updatedPolicy = policyRepository.findById(policy.getId()).get();
        // Disconnect from session so that the updates on updatedPolicy are not directly saved in db
        em.detach(updatedPolicy);
        updatedPolicy
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .codOferta(UPDATED_COD_OFERTA)
            .dataStart(UPDATED_DATA_START)
            .dataEnd(UPDATED_DATA_END)
            .perioada(UPDATED_PERIOADA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT);
        PolicyDTO policyDTO = policyMapper.toDto(updatedPolicy);

        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testPolicy.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testPolicy.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testPolicy.getDataStart()).isEqualTo(UPDATED_DATA_START);
        assertThat(testPolicy.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testPolicy.getPerioada()).isEqualTo(UPDATED_PERIOADA);
        assertThat(testPolicy.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testPolicy.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testPolicy.getSumaPlata()).isEqualTo(UPDATED_SUMA_PLATA);
        assertThat(testPolicy.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void putNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePolicyWithPatch() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy using partial update
        Policy partialUpdatedPolicy = new Policy();
        partialUpdatedPolicy.setId(policy.getId());

        partialUpdatedPolicy.codOferta(UPDATED_COD_OFERTA).valuta(UPDATED_VALUTA).idAgent(UPDATED_ID_AGENT);

        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicy))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getNumarPolita()).isEqualTo(DEFAULT_NUMAR_POLITA);
        assertThat(testPolicy.getSeriePolita()).isEqualTo(DEFAULT_SERIE_POLITA);
        assertThat(testPolicy.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testPolicy.getDataStart()).isEqualTo(DEFAULT_DATA_START);
        assertThat(testPolicy.getDataEnd()).isEqualTo(DEFAULT_DATA_END);
        assertThat(testPolicy.getPerioada()).isEqualTo(DEFAULT_PERIOADA);
        assertThat(testPolicy.getIdProdus()).isEqualTo(DEFAULT_ID_PRODUS);
        assertThat(testPolicy.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testPolicy.getSumaPlata()).isEqualByComparingTo(DEFAULT_SUMA_PLATA);
        assertThat(testPolicy.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void fullUpdatePolicyWithPatch() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy using partial update
        Policy partialUpdatedPolicy = new Policy();
        partialUpdatedPolicy.setId(policy.getId());

        partialUpdatedPolicy
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .codOferta(UPDATED_COD_OFERTA)
            .dataStart(UPDATED_DATA_START)
            .dataEnd(UPDATED_DATA_END)
            .perioada(UPDATED_PERIOADA)
            .idProdus(UPDATED_ID_PRODUS)
            .valuta(UPDATED_VALUTA)
            .sumaPlata(UPDATED_SUMA_PLATA)
            .idAgent(UPDATED_ID_AGENT);

        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicy))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testPolicy.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testPolicy.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testPolicy.getDataStart()).isEqualTo(UPDATED_DATA_START);
        assertThat(testPolicy.getDataEnd()).isEqualTo(UPDATED_DATA_END);
        assertThat(testPolicy.getPerioada()).isEqualTo(UPDATED_PERIOADA);
        assertThat(testPolicy.getIdProdus()).isEqualTo(UPDATED_ID_PRODUS);
        assertThat(testPolicy.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testPolicy.getSumaPlata()).isEqualByComparingTo(UPDATED_SUMA_PLATA);
        assertThat(testPolicy.getIdAgent()).isEqualTo(UPDATED_ID_AGENT);
    }

    @Test
    @Transactional
    void patchNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, policyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Delete the policy
        restPolicyMockMvc
            .perform(delete(ENTITY_API_URL_ID, policy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
