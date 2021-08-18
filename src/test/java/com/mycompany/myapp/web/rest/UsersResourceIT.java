package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Users;
import com.mycompany.myapp.domain.enumeration.Asigurat;
import com.mycompany.myapp.repository.UsersRepository;
import com.mycompany.myapp.service.dto.UsersDTO;
import com.mycompany.myapp.service.mapper.UsersMapper;
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
 * Integration tests for the {@link UsersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsersResourceIT {

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

    private static final String DEFAULT_IM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IM_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsersMockMvc;

    private Users users;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createEntity(EntityManager em) {
        Users users = new Users()
            .cnpCuiClient(DEFAULT_CNP_CUI_CLIENT)
            .numeAsigurat(DEFAULT_NUME_ASIGURAT)
            .prenumeAsigurat(DEFAULT_PRENUME_ASIGURAT)
            .tipAsigurat(DEFAULT_TIP_ASIGURAT)
            .telefon(DEFAULT_TELEFON)
            .email(DEFAULT_EMAIL)
            .imCode(DEFAULT_IM_CODE);
        return users;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Users createUpdatedEntity(EntityManager em) {
        Users users = new Users()
            .cnpCuiClient(UPDATED_CNP_CUI_CLIENT)
            .numeAsigurat(UPDATED_NUME_ASIGURAT)
            .prenumeAsigurat(UPDATED_PRENUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .imCode(UPDATED_IM_CODE);
        return users;
    }

    @BeforeEach
    public void initTest() {
        users = createEntity(em);
    }

    @Test
    @Transactional
    void createUsers() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();
        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);
        restUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate + 1);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getCnpCuiClient()).isEqualTo(DEFAULT_CNP_CUI_CLIENT);
        assertThat(testUsers.getNumeAsigurat()).isEqualTo(DEFAULT_NUME_ASIGURAT);
        assertThat(testUsers.getPrenumeAsigurat()).isEqualTo(DEFAULT_PRENUME_ASIGURAT);
        assertThat(testUsers.getTipAsigurat()).isEqualTo(DEFAULT_TIP_ASIGURAT);
        assertThat(testUsers.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsers.getImCode()).isEqualTo(DEFAULT_IM_CODE);
    }

    @Test
    @Transactional
    void createUsersWithExistingId() throws Exception {
        // Create the Users with an existing ID
        users.setId(1L);
        UsersDTO usersDTO = usersMapper.toDto(users);

        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList
        restUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].cnpCuiClient").value(hasItem(DEFAULT_CNP_CUI_CLIENT)))
            .andExpect(jsonPath("$.[*].numeAsigurat").value(hasItem(DEFAULT_NUME_ASIGURAT)))
            .andExpect(jsonPath("$.[*].prenumeAsigurat").value(hasItem(DEFAULT_PRENUME_ASIGURAT)))
            .andExpect(jsonPath("$.[*].tipAsigurat").value(hasItem(DEFAULT_TIP_ASIGURAT.toString())))
            .andExpect(jsonPath("$.[*].telefon").value(hasItem(DEFAULT_TELEFON)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imCode").value(hasItem(DEFAULT_IM_CODE)));
    }

    @Test
    @Transactional
    void getUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get the users
        restUsersMockMvc
            .perform(get(ENTITY_API_URL_ID, users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(users.getId().intValue()))
            .andExpect(jsonPath("$.cnpCuiClient").value(DEFAULT_CNP_CUI_CLIENT))
            .andExpect(jsonPath("$.numeAsigurat").value(DEFAULT_NUME_ASIGURAT))
            .andExpect(jsonPath("$.prenumeAsigurat").value(DEFAULT_PRENUME_ASIGURAT))
            .andExpect(jsonPath("$.tipAsigurat").value(DEFAULT_TIP_ASIGURAT.toString()))
            .andExpect(jsonPath("$.telefon").value(DEFAULT_TELEFON))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imCode").value(DEFAULT_IM_CODE));
    }

    @Test
    @Transactional
    void getNonExistingUsers() throws Exception {
        // Get the users
        restUsersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users
        Users updatedUsers = usersRepository.findById(users.getId()).get();
        // Disconnect from session so that the updates on updatedUsers are not directly saved in db
        em.detach(updatedUsers);
        updatedUsers
            .cnpCuiClient(UPDATED_CNP_CUI_CLIENT)
            .numeAsigurat(UPDATED_NUME_ASIGURAT)
            .prenumeAsigurat(UPDATED_PRENUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .imCode(UPDATED_IM_CODE);
        UsersDTO usersDTO = usersMapper.toDto(updatedUsers);

        restUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getCnpCuiClient()).isEqualTo(UPDATED_CNP_CUI_CLIENT);
        assertThat(testUsers.getNumeAsigurat()).isEqualTo(UPDATED_NUME_ASIGURAT);
        assertThat(testUsers.getPrenumeAsigurat()).isEqualTo(UPDATED_PRENUME_ASIGURAT);
        assertThat(testUsers.getTipAsigurat()).isEqualTo(UPDATED_TIP_ASIGURAT);
        assertThat(testUsers.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsers.getImCode()).isEqualTo(UPDATED_IM_CODE);
    }

    @Test
    @Transactional
    void putNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();
        users.setId(count.incrementAndGet());

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();
        users.setId(count.incrementAndGet());

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();
        users.setId(count.incrementAndGet());

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsersWithPatch() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users using partial update
        Users partialUpdatedUsers = new Users();
        partialUpdatedUsers.setId(users.getId());

        partialUpdatedUsers
            .numeAsigurat(UPDATED_NUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .email(UPDATED_EMAIL)
            .imCode(UPDATED_IM_CODE);

        restUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsers))
            )
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getCnpCuiClient()).isEqualTo(DEFAULT_CNP_CUI_CLIENT);
        assertThat(testUsers.getNumeAsigurat()).isEqualTo(UPDATED_NUME_ASIGURAT);
        assertThat(testUsers.getPrenumeAsigurat()).isEqualTo(DEFAULT_PRENUME_ASIGURAT);
        assertThat(testUsers.getTipAsigurat()).isEqualTo(UPDATED_TIP_ASIGURAT);
        assertThat(testUsers.getTelefon()).isEqualTo(DEFAULT_TELEFON);
        assertThat(testUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsers.getImCode()).isEqualTo(UPDATED_IM_CODE);
    }

    @Test
    @Transactional
    void fullUpdateUsersWithPatch() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users using partial update
        Users partialUpdatedUsers = new Users();
        partialUpdatedUsers.setId(users.getId());

        partialUpdatedUsers
            .cnpCuiClient(UPDATED_CNP_CUI_CLIENT)
            .numeAsigurat(UPDATED_NUME_ASIGURAT)
            .prenumeAsigurat(UPDATED_PRENUME_ASIGURAT)
            .tipAsigurat(UPDATED_TIP_ASIGURAT)
            .telefon(UPDATED_TELEFON)
            .email(UPDATED_EMAIL)
            .imCode(UPDATED_IM_CODE);

        restUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsers))
            )
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getCnpCuiClient()).isEqualTo(UPDATED_CNP_CUI_CLIENT);
        assertThat(testUsers.getNumeAsigurat()).isEqualTo(UPDATED_NUME_ASIGURAT);
        assertThat(testUsers.getPrenumeAsigurat()).isEqualTo(UPDATED_PRENUME_ASIGURAT);
        assertThat(testUsers.getTipAsigurat()).isEqualTo(UPDATED_TIP_ASIGURAT);
        assertThat(testUsers.getTelefon()).isEqualTo(UPDATED_TELEFON);
        assertThat(testUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsers.getImCode()).isEqualTo(UPDATED_IM_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();
        users.setId(count.incrementAndGet());

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();
        users.setId(count.incrementAndGet());

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();
        users.setId(count.incrementAndGet());

        // Create the Users
        UsersDTO usersDTO = usersMapper.toDto(users);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        int databaseSizeBeforeDelete = usersRepository.findAll().size();

        // Delete the users
        restUsersMockMvc
            .perform(delete(ENTITY_API_URL_ID, users.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
