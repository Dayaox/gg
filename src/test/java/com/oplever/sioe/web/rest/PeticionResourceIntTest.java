package com.oplever.sioe.web.rest;

import com.oplever.sioe.OpleSioeApp;

import com.oplever.sioe.domain.Peticion;
import com.oplever.sioe.domain.Usuario;
import com.oplever.sioe.domain.Peticionario;
import com.oplever.sioe.repository.PeticionRepository;
import com.oplever.sioe.service.PeticionService;
import com.oplever.sioe.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PeticionResource REST controller.
 *
 * @see PeticionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpleSioeApp.class)
public class PeticionResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_SOLICITANTE = "BBBBBBBBBB";

    private static final String DEFAULT_PATERNO_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_PATERNO_SOLICITANTE = "BBBBBBBBBB";

    private static final String DEFAULT_MATERNO_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_MATERNO_SOLICITANTE = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_CARGO_SOLICITANTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_SOLICITANTE = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_SOLICITANTE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTO_CERTIFICAR = "AAAAAAAAAA";
    private static final String UPDATED_ACTO_CERTIFICAR = "BBBBBBBBBB";

    private static final String DEFAULT_OFICIO = "AAAAAAAAAA";
    private static final String UPDATED_OFICIO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_RESPONSABLE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_RESPONSABLE = "BBBBBBBBBB";

    @Autowired
    private PeticionRepository peticionRepository;

    @Autowired
    private PeticionService peticionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPeticionMockMvc;

    private Peticion peticion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PeticionResource peticionResource = new PeticionResource(peticionService);
        this.restPeticionMockMvc = MockMvcBuilders.standaloneSetup(peticionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Peticion createEntity(EntityManager em) {
        Peticion peticion = new Peticion()
            .numero(DEFAULT_NUMERO)
            .nombre_solicitante(DEFAULT_NOMBRE_SOLICITANTE)
            .paterno_solicitante(DEFAULT_PATERNO_SOLICITANTE)
            .materno_solicitante(DEFAULT_MATERNO_SOLICITANTE)
            .cargo_solicitante(DEFAULT_CARGO_SOLICITANTE)
            .direccion_solicitante(DEFAULT_DIRECCION_SOLICITANTE)
            .acto_certificar(DEFAULT_ACTO_CERTIFICAR)
            .oficio(DEFAULT_OFICIO)
            .nombre_responsable(DEFAULT_NOMBRE_RESPONSABLE);
        // Add required entity
        Usuario usuario = UsuarioResourceIntTest.createEntity(em);
        em.persist(usuario);
        em.flush();
        peticion.setUsuario(usuario);
        // Add required entity
        Peticionario peticionario = PeticionarioResourceIntTest.createEntity(em);
        em.persist(peticionario);
        em.flush();
        peticion.setPeticionario(peticionario);
        return peticion;
    }

    @Before
    public void initTest() {
        peticion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeticion() throws Exception {
        int databaseSizeBeforeCreate = peticionRepository.findAll().size();

        // Create the Peticion
        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isCreated());

        // Validate the Peticion in the database
        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeCreate + 1);
        Peticion testPeticion = peticionList.get(peticionList.size() - 1);
        assertThat(testPeticion.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testPeticion.getNombre_solicitante()).isEqualTo(DEFAULT_NOMBRE_SOLICITANTE);
        assertThat(testPeticion.getPaterno_solicitante()).isEqualTo(DEFAULT_PATERNO_SOLICITANTE);
        assertThat(testPeticion.getMaterno_solicitante()).isEqualTo(DEFAULT_MATERNO_SOLICITANTE);
        assertThat(testPeticion.getCargo_solicitante()).isEqualTo(DEFAULT_CARGO_SOLICITANTE);
        assertThat(testPeticion.getDireccion_solicitante()).isEqualTo(DEFAULT_DIRECCION_SOLICITANTE);
        assertThat(testPeticion.getActo_certificar()).isEqualTo(DEFAULT_ACTO_CERTIFICAR);
        assertThat(testPeticion.getOficio()).isEqualTo(DEFAULT_OFICIO);
        assertThat(testPeticion.getNombre_responsable()).isEqualTo(DEFAULT_NOMBRE_RESPONSABLE);
    }

    @Test
    @Transactional
    public void createPeticionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peticionRepository.findAll().size();

        // Create the Peticion with an existing ID
        peticion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setNumero(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombre_solicitanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setNombre_solicitante(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaterno_solicitanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setPaterno_solicitante(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaterno_solicitanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setMaterno_solicitante(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCargo_solicitanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setCargo_solicitante(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccion_solicitanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setDireccion_solicitante(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActo_certificarIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setActo_certificar(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOficioIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setOficio(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombre_responsableIsRequired() throws Exception {
        int databaseSizeBeforeTest = peticionRepository.findAll().size();
        // set the field null
        peticion.setNombre_responsable(null);

        // Create the Peticion, which fails.

        restPeticionMockMvc.perform(post("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isBadRequest());

        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeticions() throws Exception {
        // Initialize the database
        peticionRepository.saveAndFlush(peticion);

        // Get all the peticionList
        restPeticionMockMvc.perform(get("/api/peticions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peticion.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].nombre_solicitante").value(hasItem(DEFAULT_NOMBRE_SOLICITANTE.toString())))
            .andExpect(jsonPath("$.[*].paterno_solicitante").value(hasItem(DEFAULT_PATERNO_SOLICITANTE.toString())))
            .andExpect(jsonPath("$.[*].materno_solicitante").value(hasItem(DEFAULT_MATERNO_SOLICITANTE.toString())))
            .andExpect(jsonPath("$.[*].cargo_solicitante").value(hasItem(DEFAULT_CARGO_SOLICITANTE.toString())))
            .andExpect(jsonPath("$.[*].direccion_solicitante").value(hasItem(DEFAULT_DIRECCION_SOLICITANTE.toString())))
            .andExpect(jsonPath("$.[*].acto_certificar").value(hasItem(DEFAULT_ACTO_CERTIFICAR.toString())))
            .andExpect(jsonPath("$.[*].oficio").value(hasItem(DEFAULT_OFICIO.toString())))
            .andExpect(jsonPath("$.[*].nombre_responsable").value(hasItem(DEFAULT_NOMBRE_RESPONSABLE.toString())));
    }

    @Test
    @Transactional
    public void getPeticion() throws Exception {
        // Initialize the database
        peticionRepository.saveAndFlush(peticion);

        // Get the peticion
        restPeticionMockMvc.perform(get("/api/peticions/{id}", peticion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(peticion.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.nombre_solicitante").value(DEFAULT_NOMBRE_SOLICITANTE.toString()))
            .andExpect(jsonPath("$.paterno_solicitante").value(DEFAULT_PATERNO_SOLICITANTE.toString()))
            .andExpect(jsonPath("$.materno_solicitante").value(DEFAULT_MATERNO_SOLICITANTE.toString()))
            .andExpect(jsonPath("$.cargo_solicitante").value(DEFAULT_CARGO_SOLICITANTE.toString()))
            .andExpect(jsonPath("$.direccion_solicitante").value(DEFAULT_DIRECCION_SOLICITANTE.toString()))
            .andExpect(jsonPath("$.acto_certificar").value(DEFAULT_ACTO_CERTIFICAR.toString()))
            .andExpect(jsonPath("$.oficio").value(DEFAULT_OFICIO.toString()))
            .andExpect(jsonPath("$.nombre_responsable").value(DEFAULT_NOMBRE_RESPONSABLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPeticion() throws Exception {
        // Get the peticion
        restPeticionMockMvc.perform(get("/api/peticions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeticion() throws Exception {
        // Initialize the database
        peticionService.save(peticion);

        int databaseSizeBeforeUpdate = peticionRepository.findAll().size();

        // Update the peticion
        Peticion updatedPeticion = peticionRepository.findOne(peticion.getId());
        updatedPeticion
            .numero(UPDATED_NUMERO)
            .nombre_solicitante(UPDATED_NOMBRE_SOLICITANTE)
            .paterno_solicitante(UPDATED_PATERNO_SOLICITANTE)
            .materno_solicitante(UPDATED_MATERNO_SOLICITANTE)
            .cargo_solicitante(UPDATED_CARGO_SOLICITANTE)
            .direccion_solicitante(UPDATED_DIRECCION_SOLICITANTE)
            .acto_certificar(UPDATED_ACTO_CERTIFICAR)
            .oficio(UPDATED_OFICIO)
            .nombre_responsable(UPDATED_NOMBRE_RESPONSABLE);

        restPeticionMockMvc.perform(put("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeticion)))
            .andExpect(status().isOk());

        // Validate the Peticion in the database
        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeUpdate);
        Peticion testPeticion = peticionList.get(peticionList.size() - 1);
        assertThat(testPeticion.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testPeticion.getNombre_solicitante()).isEqualTo(UPDATED_NOMBRE_SOLICITANTE);
        assertThat(testPeticion.getPaterno_solicitante()).isEqualTo(UPDATED_PATERNO_SOLICITANTE);
        assertThat(testPeticion.getMaterno_solicitante()).isEqualTo(UPDATED_MATERNO_SOLICITANTE);
        assertThat(testPeticion.getCargo_solicitante()).isEqualTo(UPDATED_CARGO_SOLICITANTE);
        assertThat(testPeticion.getDireccion_solicitante()).isEqualTo(UPDATED_DIRECCION_SOLICITANTE);
        assertThat(testPeticion.getActo_certificar()).isEqualTo(UPDATED_ACTO_CERTIFICAR);
        assertThat(testPeticion.getOficio()).isEqualTo(UPDATED_OFICIO);
        assertThat(testPeticion.getNombre_responsable()).isEqualTo(UPDATED_NOMBRE_RESPONSABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingPeticion() throws Exception {
        int databaseSizeBeforeUpdate = peticionRepository.findAll().size();

        // Create the Peticion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPeticionMockMvc.perform(put("/api/peticions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(peticion)))
            .andExpect(status().isCreated());

        // Validate the Peticion in the database
        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePeticion() throws Exception {
        // Initialize the database
        peticionService.save(peticion);

        int databaseSizeBeforeDelete = peticionRepository.findAll().size();

        // Get the peticion
        restPeticionMockMvc.perform(delete("/api/peticions/{id}", peticion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Peticion> peticionList = peticionRepository.findAll();
        assertThat(peticionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Peticion.class);
    }
}
