package mx.infotec.dads.archetype.web.rest;

import mx.infotec.dads.archetype.KukulkanApp;

import mx.infotec.dads.archetype.domain.Trabajo;
import mx.infotec.dads.archetype.repository.TrabajoRepository;
import mx.infotec.dads.archetype.service.TrabajoService;
import mx.infotec.dads.archetype.web.rest.errors.ExceptionTranslator;

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
 * Test class for the TrabajoResource REST controller.
 *
 * @see TrabajoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KukulkanApp.class)
public class TrabajoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private TrabajoService trabajoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrabajoMockMvc;

    private Trabajo trabajo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TrabajoResource trabajoResource = new TrabajoResource(trabajoService);
        this.restTrabajoMockMvc = MockMvcBuilders.standaloneSetup(trabajoResource)
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
    public static Trabajo createEntity(EntityManager em) {
        Trabajo trabajo = new Trabajo()
            .name(DEFAULT_NAME);
        return trabajo;
    }

    @Before
    public void initTest() {
        trabajo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrabajo() throws Exception {
        int databaseSizeBeforeCreate = trabajoRepository.findAll().size();

        // Create the Trabajo
        restTrabajoMockMvc.perform(post("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajo)))
            .andExpect(status().isCreated());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeCreate + 1);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTrabajoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trabajoRepository.findAll().size();

        // Create the Trabajo with an existing ID
        trabajo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrabajoMockMvc.perform(post("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrabajos() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList
        restTrabajoMockMvc.perform(get("/api/trabajos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabajo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTrabajo() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get the trabajo
        restTrabajoMockMvc.perform(get("/api/trabajos/{id}", trabajo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trabajo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTrabajo() throws Exception {
        // Get the trabajo
        restTrabajoMockMvc.perform(get("/api/trabajos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrabajo() throws Exception {
        // Initialize the database
        trabajoService.save(trabajo);

        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();

        // Update the trabajo
        Trabajo updatedTrabajo = trabajoRepository.findOne(trabajo.getId());
        updatedTrabajo
            .name(UPDATED_NAME);

        restTrabajoMockMvc.perform(put("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrabajo)))
            .andExpect(status().isOk());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();

        // Create the Trabajo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrabajoMockMvc.perform(put("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajo)))
            .andExpect(status().isCreated());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrabajo() throws Exception {
        // Initialize the database
        trabajoService.save(trabajo);

        int databaseSizeBeforeDelete = trabajoRepository.findAll().size();

        // Get the trabajo
        restTrabajoMockMvc.perform(delete("/api/trabajos/{id}", trabajo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trabajo.class);
        Trabajo trabajo1 = new Trabajo();
        trabajo1.setId(1L);
        Trabajo trabajo2 = new Trabajo();
        trabajo2.setId(trabajo1.getId());
        assertThat(trabajo1).isEqualTo(trabajo2);
        trabajo2.setId(2L);
        assertThat(trabajo1).isNotEqualTo(trabajo2);
        trabajo1.setId(null);
        assertThat(trabajo1).isNotEqualTo(trabajo2);
    }
}
