package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Clients;
import com.mycompany.myapp.domain.enumeration.Asigurat;
import com.mycompany.myapp.repository.ClientsRepository;
import com.mycompany.myapp.service.dto.ClientsDTO;
import com.mycompany.myapp.service.mapper.ClientsMapper;
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
 * Integration tests for the {@link ClientsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientsResourceIT {

    private static final String DEFAULT_CNP_CUI_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CNP_CUI_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_NUME_ASIGURAT = "AAAAAAAAAA";
    private static final String UPDATED_NUME_ASIGURAT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENUME_ASIGURAT = "AAAAAAAAAA";
    private static final String UPDATED_PRENUME_ASIGURAT = "BBBBBBBBBB";

    private static final Asigurat DEFAULT_TIP_ASIGURAT = Asigurat.F;
    private static final Asigurat UPDATED_TIP_ASIGURAT = Asigurat.J;

    private static final String DEFAULT_TELEFON = "AAAAAAAAAA";
    private static final String UPDATED_TELEFON = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_USER_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_USER_IDENTIFIER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientsMockMvc;

    private Clients clients;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clients createEntity(EntityManager em) {
        Clients clients = new Clients()
            .cnpCuiClient(DEFAULT_CNP_CUI_CLIENT)
            .numeAsigurat(DEFAULT_NUME_ASIGURAT)
            .prenumeAsigurat(DEFAULT_PRENUME_ASIGURAT)
            .tipAsigurat(DEFAULT_TIP_ASIGURAT)
            .telefon(DEFAULT_TELEFON)
            .email(DEFAULT_EMAIL)
            .userIdentifier(DEFAULT_USER_IDENTIFIER);
        return clients;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clients createUpdatedEntity(EntityManager em) {
        Clients clients = new Clients()
            .cnpCuiClient(UPDATED_CNP_CUI_CLIENT)
            .numeAsigurat(UPDATED_NUME_ASIGURAT)
            .prenumeAsigurat(UPDATED_PRENUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .userIdentifier(UPDATED_USER_IDENTIFIER);
        return clients;
    }

    @BeforeEach
    public void initTest() {
        clients = createEntity(em);
    }

    @Test
    @Transactional
    void createClients() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();
        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);
        restClientsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate + 1);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getCnpCuiClient()).isEqualTo(DEFAULT_CNP_CUI_CLIENT);
        assertThat(testClients.getNumeAsigurat()).isEqualTo(DEFAULT_NUME_ASIGURAT);
        assertThat(testClients.getPrenumeAsigurat()).isEqualTo(DEFAULT_PRENUME_ASIGURAT);
        assertThat(testClients.getTipAsigurat()).isEqualTo(DEFAULT_TIP_ASIGURAT);
        assertThat(testClients.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testClients.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClients.getUserIdentifier()).isEqualTo(DEFAULT_USER_IDENTIFIER);
    }

    @Test
    @Transactional
    void createClientsWithExistingId() throws Exception {
        // Create the Clients with an existing ID
        clients.setId(1L);
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCnpCuiClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setCnpCuiClient(null);

        // Create the Clients, which fails.
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        restClientsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeAsiguratIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientsRepository.findAll().size();
        // set the field null
        clients.setNumeAsigurat(null);

        // Create the Clients, which fails.
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        restClientsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isBadRequest());

        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get all the clientsList
        restClientsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpCuiClient").value(hasItem(DEFAULT_CNP_CUI_CLIENT)))
            .andExpect(jsonPath("$.[*].numeAsigurat").value(hasItem(DEFAULT_NUME_ASIGURAT)))
            .andExpect(jsonPath("$.[*].prenumeAsigurat").value(hasItem(DEFAULT_PRENUME_ASIGURAT)))
            .andExpect(jsonPath("$.[*].tipAsigurat").value(hasItem(DEFAULT_TIP_ASIGURAT.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].userIdentifier").value(hasItem(DEFAULT_USER_IDENTIFIER)));
    }

    @Test
    @Transactional
    void getClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        // Get the clients
        restClientsMockMvc
            .perform(get(ENTITY_API_URL_ID, clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clients.getId().intValue()))
            .andExpect(jsonPath("$.cnpCuiClient").value(DEFAULT_CNP_CUI_CLIENT))
            .andExpect(jsonPath("$.numeAsigurat").value(DEFAULT_NUME_ASIGURAT))
            .andExpect(jsonPath("$.prenumeAsigurat").value(DEFAULT_PRENUME_ASIGURAT))
            .andExpect(jsonPath("$.tipAsigurat").value(DEFAULT_TIP_ASIGURAT.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.userIdentifier").value(DEFAULT_USER_IDENTIFIER));
    }

    @Test
    @Transactional
    void getNonExistingClients() throws Exception {
        // Get the clients
        restClientsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients
        Clients updatedClients = clientsRepository.findById(clients.getId()).get();
        // Disconnect from session so that the updates on updatedClients are not directly saved in db
        em.detach(updatedClients);
        updatedClients
            .cnpCuiClient(UPDATED_CNP_CUI_CLIENT)
            .numeAsigurat(UPDATED_NUME_ASIGURAT)
            .prenumeAsigurat(UPDATED_PRENUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .userIdentifier(UPDATED_USER_IDENTIFIER);
        ClientsDTO clientsDTO = clientsMapper.toDto(updatedClients);

        restClientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getCnpCuiClient()).isEqualTo(UPDATED_CNP_CUI_CLIENT);
        assertThat(testClients.getNumeAsigurat()).isEqualTo(UPDATED_NUME_ASIGURAT);
        assertThat(testClients.getPrenumeAsigurat()).isEqualTo(UPDATED_PRENUME_ASIGURAT);
        assertThat(testClients.getTipAsigurat()).isEqualTo(UPDATED_TIP_ASIGURAT);
        assertThat(testClients.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testClients.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClients.getUserIdentifier()).isEqualTo(UPDATED_USER_IDENTIFIER);
    }

    @Test
    @Transactional
    void putNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientsWithPatch() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients using partial update
        Clients partialUpdatedClients = new Clients();
        partialUpdatedClients.setId(clients.getId());

        partialUpdatedClients
            .cnpCuiClient(UPDATED_CNP_CUI_CLIENT)
            .prenumeAsigurat(UPDATED_PRENUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .userIdentifier(UPDATED_USER_IDENTIFIER);

        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClients.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClients))
            )
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getCnpCuiClient()).isEqualTo(UPDATED_CNP_CUI_CLIENT);
        assertThat(testClients.getNumeAsigurat()).isEqualTo(DEFAULT_NUME_ASIGURAT);
        assertThat(testClients.getPrenumeAsigurat()).isEqualTo(UPDATED_PRENUME_ASIGURAT);
        assertThat(testClients.getTipAsigurat()).isEqualTo(UPDATED_TIP_ASIGURAT);
        assertThat(testClients.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testClients.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClients.getUserIdentifier()).isEqualTo(UPDATED_USER_IDENTIFIER);
    }

    @Test
    @Transactional
    void fullUpdateClientsWithPatch() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients using partial update
        Clients partialUpdatedClients = new Clients();
        partialUpdatedClients.setId(clients.getId());

        partialUpdatedClients
            .cnpCuiClient(UPDATED_CNP_CUI_CLIENT)
            .numeAsigurat(UPDATED_NUME_ASIGURAT)
            .prenumeAsigurat(UPDATED_PRENUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .userIdentifier(UPDATED_USER_IDENTIFIER);

        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClients.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClients))
            )
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getCnpCuiClient()).isEqualTo(UPDATED_CNP_CUI_CLIENT);
        assertThat(testClients.getNumeAsigurat()).isEqualTo(UPDATED_NUME_ASIGURAT);
        assertThat(testClients.getPrenumeAsigurat()).isEqualTo(UPDATED_PRENUME_ASIGURAT);
        assertThat(testClients.getTipAsigurat()).isEqualTo(UPDATED_TIP_ASIGURAT);
        assertThat(testClients.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testClients.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClients.getUserIdentifier()).isEqualTo(UPDATED_USER_IDENTIFIER);
    }

    @Test
    @Transactional
    void patchNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();
        clients.setId(count.incrementAndGet());

        // Create the Clients
        ClientsDTO clientsDTO = clientsMapper.toDto(clients);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClients() throws Exception {
        // Initialize the database
        clientsRepository.saveAndFlush(clients);

        int databaseSizeBeforeDelete = clientsRepository.findAll().size();

        // Delete the clients
        restClientsMockMvc
            .perform(delete(ENTITY_API_URL_ID, clients.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
