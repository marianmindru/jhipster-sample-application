package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Transaction;
import com.mycompany.myapp.domain.enumeration.StatusPlata;
import com.mycompany.myapp.repository.TransactionRepository;
import com.mycompany.myapp.service.dto.TransactionDTO;
import com.mycompany.myapp.service.mapper.TransactionMapper;
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
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionResourceIT {

    private static final String DEFAULT_COD_OFERTA = "AAAAAAAAAA";
    private static final String UPDATED_COD_OFERTA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMAR_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_NUMAR_POLITA = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE_POLITA = "AAAAAAAAAA";
    private static final String UPDATED_SERIE_POLITA = "BBBBBBBBBB";

    private static final Double DEFAULT_SUMA_PLATITA = 0D;
    private static final Double UPDATED_SUMA_PLATITA = 1D;

    private static final StatusPlata DEFAULT_STATUS_PLATA = StatusPlata.NEW;
    private static final StatusPlata UPDATED_STATUS_PLATA = StatusPlata.WAITING;

    private static final LocalDate DEFAULT_DATA_PLATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PLATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VALUTA = "AAAAAAAAAA";
    private static final String UPDATED_VALUTA = "BBBBBBBBBB";

    private static final String DEFAULT_REFERINTA_ING = "AAAAAAAAAA";
    private static final String UPDATED_REFERINTA_ING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .codOferta(DEFAULT_COD_OFERTA)
            .numarPolita(DEFAULT_NUMAR_POLITA)
            .seriePolita(DEFAULT_SERIE_POLITA)
            .sumaPlatita(DEFAULT_SUMA_PLATITA)
            .statusPlata(DEFAULT_STATUS_PLATA)
            .dataPlata(DEFAULT_DATA_PLATA)
            .valuta(DEFAULT_VALUTA)
            .referintaIng(DEFAULT_REFERINTA_ING);
        return transaction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .codOferta(UPDATED_COD_OFERTA)
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .sumaPlatita(UPDATED_SUMA_PLATITA)
            .statusPlata(UPDATED_STATUS_PLATA)
            .dataPlata(UPDATED_DATA_PLATA)
            .valuta(UPDATED_VALUTA)
            .referintaIng(UPDATED_REFERINTA_ING);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);
        restTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testTransaction.getNumarPolita()).isEqualTo(DEFAULT_NUMAR_POLITA);
        assertThat(testTransaction.getSeriePolita()).isEqualTo(DEFAULT_SERIE_POLITA);
        assertThat(testTransaction.getSumaPlatita()).isEqualTo(DEFAULT_SUMA_PLATITA);
        assertThat(testTransaction.getStatusPlata()).isEqualTo(DEFAULT_STATUS_PLATA);
        assertThat(testTransaction.getDataPlata()).isEqualTo(DEFAULT_DATA_PLATA);
        assertThat(testTransaction.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testTransaction.getReferintaIng()).isEqualTo(DEFAULT_REFERINTA_ING);
    }

    @Test
    @Transactional
    void createTransactionWithExistingId() throws Exception {
        // Create the Transaction with an existing ID
        transaction.setId(1L);
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodOfertaIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setCodOferta(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        restTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReferintaIngIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setReferintaIng(null);

        // Create the Transaction, which fails.
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        restTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].codOferta").value(hasItem(DEFAULT_COD_OFERTA)))
            .andExpect(jsonPath("$.[*].numarPolita").value(hasItem(DEFAULT_NUMAR_POLITA)))
            .andExpect(jsonPath("$.[*].seriePolita").value(hasItem(DEFAULT_SERIE_POLITA)))
            .andExpect(jsonPath("$.[*].sumaPlatita").value(hasItem(DEFAULT_SUMA_PLATITA.doubleValue())))
            .andExpect(jsonPath("$.[*].statusPlata").value(hasItem(DEFAULT_STATUS_PLATA.toString())))
            .andExpect(jsonPath("$.[*].dataPlata").value(hasItem(DEFAULT_DATA_PLATA.toString())))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA)))
            .andExpect(jsonPath("$.[*].referintaIng").value(hasItem(DEFAULT_REFERINTA_ING)));
    }

    @Test
    @Transactional
    void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.codOferta").value(DEFAULT_COD_OFERTA))
            .andExpect(jsonPath("$.numarPolita").value(DEFAULT_NUMAR_POLITA))
            .andExpect(jsonPath("$.seriePolita").value(DEFAULT_SERIE_POLITA))
            .andExpect(jsonPath("$.sumaPlatita").value(DEFAULT_SUMA_PLATITA.doubleValue()))
            .andExpect(jsonPath("$.statusPlata").value(DEFAULT_STATUS_PLATA.toString()))
            .andExpect(jsonPath("$.dataPlata").value(DEFAULT_DATA_PLATA.toString()))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA))
            .andExpect(jsonPath("$.referintaIng").value(DEFAULT_REFERINTA_ING));
    }

    @Test
    @Transactional
    void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .codOferta(UPDATED_COD_OFERTA)
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .sumaPlatita(UPDATED_SUMA_PLATITA)
            .statusPlata(UPDATED_STATUS_PLATA)
            .dataPlata(UPDATED_DATA_PLATA)
            .valuta(UPDATED_VALUTA)
            .referintaIng(UPDATED_REFERINTA_ING);
        TransactionDTO transactionDTO = transactionMapper.toDto(updatedTransaction);

        restTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testTransaction.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testTransaction.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testTransaction.getSumaPlatita()).isEqualTo(UPDATED_SUMA_PLATITA);
        assertThat(testTransaction.getStatusPlata()).isEqualTo(UPDATED_STATUS_PLATA);
        assertThat(testTransaction.getDataPlata()).isEqualTo(UPDATED_DATA_PLATA);
        assertThat(testTransaction.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testTransaction.getReferintaIng()).isEqualTo(UPDATED_REFERINTA_ING);
    }

    @Test
    @Transactional
    void putNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();
        transaction.setId(count.incrementAndGet());

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();
        transaction.setId(count.incrementAndGet());

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();
        transaction.setId(count.incrementAndGet());

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionWithPatch() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction using partial update
        Transaction partialUpdatedTransaction = new Transaction();
        partialUpdatedTransaction.setId(transaction.getId());

        partialUpdatedTransaction.numarPolita(UPDATED_NUMAR_POLITA);

        restTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaction))
            )
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getCodOferta()).isEqualTo(DEFAULT_COD_OFERTA);
        assertThat(testTransaction.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testTransaction.getSeriePolita()).isEqualTo(DEFAULT_SERIE_POLITA);
        assertThat(testTransaction.getSumaPlatita()).isEqualTo(DEFAULT_SUMA_PLATITA);
        assertThat(testTransaction.getStatusPlata()).isEqualTo(DEFAULT_STATUS_PLATA);
        assertThat(testTransaction.getDataPlata()).isEqualTo(DEFAULT_DATA_PLATA);
        assertThat(testTransaction.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testTransaction.getReferintaIng()).isEqualTo(DEFAULT_REFERINTA_ING);
    }

    @Test
    @Transactional
    void fullUpdateTransactionWithPatch() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction using partial update
        Transaction partialUpdatedTransaction = new Transaction();
        partialUpdatedTransaction.setId(transaction.getId());

        partialUpdatedTransaction
            .codOferta(UPDATED_COD_OFERTA)
            .numarPolita(UPDATED_NUMAR_POLITA)
            .seriePolita(UPDATED_SERIE_POLITA)
            .sumaPlatita(UPDATED_SUMA_PLATITA)
            .statusPlata(UPDATED_STATUS_PLATA)
            .dataPlata(UPDATED_DATA_PLATA)
            .valuta(UPDATED_VALUTA)
            .referintaIng(UPDATED_REFERINTA_ING);

        restTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransaction.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransaction))
            )
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getCodOferta()).isEqualTo(UPDATED_COD_OFERTA);
        assertThat(testTransaction.getNumarPolita()).isEqualTo(UPDATED_NUMAR_POLITA);
        assertThat(testTransaction.getSeriePolita()).isEqualTo(UPDATED_SERIE_POLITA);
        assertThat(testTransaction.getSumaPlatita()).isEqualTo(UPDATED_SUMA_PLATITA);
        assertThat(testTransaction.getStatusPlata()).isEqualTo(UPDATED_STATUS_PLATA);
        assertThat(testTransaction.getDataPlata()).isEqualTo(UPDATED_DATA_PLATA);
        assertThat(testTransaction.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testTransaction.getReferintaIng()).isEqualTo(UPDATED_REFERINTA_ING);
    }

    @Test
    @Transactional
    void patchNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();
        transaction.setId(count.incrementAndGet());

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();
        transaction.setId(count.incrementAndGet());

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();
        transaction.setId(count.incrementAndGet());

        // Create the Transaction
        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transactionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, transaction.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
