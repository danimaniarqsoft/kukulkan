package mx.infotec.dads.archetype.web.rest;

import mx.infotec.dads.archetype.KukulkanApp;

import mx.infotec.dads.archetype.domain.Persona;
import mx.infotec.dads.archetype.repository.PersonaRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static mx.infotec.dads.archetype.web.rest.TestUtil.sameInstant;
import static mx.infotec.dads.archetype.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonaResource REST controller.
 *
 * @see PersonaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KukulkanApp.class)
public class PersonaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD = 12;
    private static final Integer UPDATED_EDAD = 13;

    private static final Long DEFAULT_NUMERO_CREDENCIAL = 13L;
    private static final Long UPDATED_NUMERO_CREDENCIAL = 14L;

    private static final BigDecimal DEFAULT_SUELDO = new BigDecimal(15);
    private static final BigDecimal UPDATED_SUELDO = new BigDecimal(16);

    private static final Float DEFAULT_IMPUESTO = 12F;
    private static final Float UPDATED_IMPUESTO = 13F;

    private static final Double DEFAULT_IMPUESTO_DETALLE = 11D;
    private static final Double UPDATED_IMPUESTO_DETALLE = 12D;

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final LocalDate DEFAULT_FECHA_LOCAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_LOCAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_FECHA_ZONE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ZONE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(5, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(5000000, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_ANY_BLOB = TestUtil.createByteArray(78, "0");
    private static final byte[] UPDATED_IMAGEN_ANY_BLOB = TestUtil.createByteArray(5000000, "1");
    private static final String DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_ANY_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGEN_BLOB = TestUtil.createByteArray(21, "0");
    private static final byte[] UPDATED_IMAGEN_BLOB = TestUtil.createByteArray(5000000, "1");
    private static final String DEFAULT_IMAGEN_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSTANTE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTANTE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonaMockMvc;

    private Persona persona;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonaResource personaResource = new PersonaResource(personaRepository);
        this.restPersonaMockMvc = MockMvcBuilders.standaloneSetup(personaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .nombre(DEFAULT_NOMBRE)
            .edad(DEFAULT_EDAD)
            .numeroCredencial(DEFAULT_NUMERO_CREDENCIAL)
            .sueldo(DEFAULT_SUELDO)
            .impuesto(DEFAULT_IMPUESTO)
            .impuestoDetalle(DEFAULT_IMPUESTO_DETALLE)
            .activo(DEFAULT_ACTIVO)
            .fechaLocalDate(DEFAULT_FECHA_LOCAL_DATE)
            .fechaZoneDateTime(DEFAULT_FECHA_ZONE_DATE_TIME)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .imagenAnyBlob(DEFAULT_IMAGEN_ANY_BLOB)
            .imagenAnyBlobContentType(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE)
            .imagenBlob(DEFAULT_IMAGEN_BLOB)
            .imagenBlobContentType(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE)
            .desc(DEFAULT_DESC)
            .instante(DEFAULT_INSTANTE);
        return persona;
    }

    @Before
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersona.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testPersona.getNumeroCredencial()).isEqualTo(DEFAULT_NUMERO_CREDENCIAL);
        assertThat(testPersona.getSueldo()).isEqualTo(DEFAULT_SUELDO);
        assertThat(testPersona.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testPersona.getImpuestoDetalle()).isEqualTo(DEFAULT_IMPUESTO_DETALLE);
        assertThat(testPersona.isActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testPersona.getFechaLocalDate()).isEqualTo(DEFAULT_FECHA_LOCAL_DATE);
        assertThat(testPersona.getFechaZoneDateTime()).isEqualTo(DEFAULT_FECHA_ZONE_DATE_TIME);
        assertThat(testPersona.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testPersona.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testPersona.getImagenAnyBlob()).isEqualTo(DEFAULT_IMAGEN_ANY_BLOB);
        assertThat(testPersona.getImagenAnyBlobContentType()).isEqualTo(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE);
        assertThat(testPersona.getImagenBlob()).isEqualTo(DEFAULT_IMAGEN_BLOB);
        assertThat(testPersona.getImagenBlobContentType()).isEqualTo(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE);
        assertThat(testPersona.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testPersona.getInstante()).isEqualTo(DEFAULT_INSTANTE);
    }

    @Test
    @Transactional
    public void createPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona with an existing ID
        persona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setNombre(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEdadIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setEdad(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroCredencialIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setNumeroCredencial(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSueldoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setSueldo(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImpuestoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setImpuesto(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImpuestoDetalleIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setImpuestoDetalle(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setActivo(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaLocalDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setFechaLocalDate(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaZoneDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setFechaZoneDateTime(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImagenIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setImagen(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImagenAnyBlobIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setImagenAnyBlob(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImagenBlobIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setImagenBlob(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setDesc(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstanteIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setInstante(null);

        // Create the Persona, which fails.

        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].numeroCredencial").value(hasItem(DEFAULT_NUMERO_CREDENCIAL.intValue())))
            .andExpect(jsonPath("$.[*].sueldo").value(hasItem(DEFAULT_SUELDO.intValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.doubleValue())))
            .andExpect(jsonPath("$.[*].impuestoDetalle").value(hasItem(DEFAULT_IMPUESTO_DETALLE.doubleValue())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaLocalDate").value(hasItem(DEFAULT_FECHA_LOCAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].fechaZoneDateTime").value(hasItem(sameInstant(DEFAULT_FECHA_ZONE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].imagenAnyBlobContentType").value(hasItem(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenAnyBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_ANY_BLOB))))
            .andExpect(jsonPath("$.[*].imagenBlobContentType").value(hasItem(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenBlob").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN_BLOB))))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].instante").value(hasItem(DEFAULT_INSTANTE.toString())));
    }

    @Test
    @Transactional
    public void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.numeroCredencial").value(DEFAULT_NUMERO_CREDENCIAL.intValue()))
            .andExpect(jsonPath("$.sueldo").value(DEFAULT_SUELDO.intValue()))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.doubleValue()))
            .andExpect(jsonPath("$.impuestoDetalle").value(DEFAULT_IMPUESTO_DETALLE.doubleValue()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.fechaLocalDate").value(DEFAULT_FECHA_LOCAL_DATE.toString()))
            .andExpect(jsonPath("$.fechaZoneDateTime").value(sameInstant(DEFAULT_FECHA_ZONE_DATE_TIME)))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.imagenAnyBlobContentType").value(DEFAULT_IMAGEN_ANY_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenAnyBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_ANY_BLOB)))
            .andExpect(jsonPath("$.imagenBlobContentType").value(DEFAULT_IMAGEN_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenBlob").value(Base64Utils.encodeToString(DEFAULT_IMAGEN_BLOB)))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.instante").value(DEFAULT_INSTANTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findOne(persona.getId());
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .nombre(UPDATED_NOMBRE)
            .edad(UPDATED_EDAD)
            .numeroCredencial(UPDATED_NUMERO_CREDENCIAL)
            .sueldo(UPDATED_SUELDO)
            .impuesto(UPDATED_IMPUESTO)
            .impuestoDetalle(UPDATED_IMPUESTO_DETALLE)
            .activo(UPDATED_ACTIVO)
            .fechaLocalDate(UPDATED_FECHA_LOCAL_DATE)
            .fechaZoneDateTime(UPDATED_FECHA_ZONE_DATE_TIME)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .imagenAnyBlob(UPDATED_IMAGEN_ANY_BLOB)
            .imagenAnyBlobContentType(UPDATED_IMAGEN_ANY_BLOB_CONTENT_TYPE)
            .imagenBlob(UPDATED_IMAGEN_BLOB)
            .imagenBlobContentType(UPDATED_IMAGEN_BLOB_CONTENT_TYPE)
            .desc(UPDATED_DESC)
            .instante(UPDATED_INSTANTE);

        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersona)))
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersona.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testPersona.getNumeroCredencial()).isEqualTo(UPDATED_NUMERO_CREDENCIAL);
        assertThat(testPersona.getSueldo()).isEqualTo(UPDATED_SUELDO);
        assertThat(testPersona.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testPersona.getImpuestoDetalle()).isEqualTo(UPDATED_IMPUESTO_DETALLE);
        assertThat(testPersona.isActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testPersona.getFechaLocalDate()).isEqualTo(UPDATED_FECHA_LOCAL_DATE);
        assertThat(testPersona.getFechaZoneDateTime()).isEqualTo(UPDATED_FECHA_ZONE_DATE_TIME);
        assertThat(testPersona.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testPersona.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testPersona.getImagenAnyBlob()).isEqualTo(UPDATED_IMAGEN_ANY_BLOB);
        assertThat(testPersona.getImagenAnyBlobContentType()).isEqualTo(UPDATED_IMAGEN_ANY_BLOB_CONTENT_TYPE);
        assertThat(testPersona.getImagenBlob()).isEqualTo(UPDATED_IMAGEN_BLOB);
        assertThat(testPersona.getImagenBlobContentType()).isEqualTo(UPDATED_IMAGEN_BLOB_CONTENT_TYPE);
        assertThat(testPersona.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testPersona.getInstante()).isEqualTo(UPDATED_INSTANTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Create the Persona

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);
        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Get the persona
        restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Persona.class);
        Persona persona1 = new Persona();
        persona1.setId(1L);
        Persona persona2 = new Persona();
        persona2.setId(persona1.getId());
        assertThat(persona1).isEqualTo(persona2);
        persona2.setId(2L);
        assertThat(persona1).isNotEqualTo(persona2);
        persona1.setId(null);
        assertThat(persona1).isNotEqualTo(persona2);
    }
}
