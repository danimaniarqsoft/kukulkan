package mx.infotec.dads.archetype.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.infotec.dads.archetype.domain.Tarea;
import mx.infotec.dads.archetype.service.TareaService;
import mx.infotec.dads.archetype.web.rest.util.HeaderUtil;
import mx.infotec.dads.archetype.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tarea.
 */
@RestController
@RequestMapping("/api")
public class TareaResource {

    private final Logger log = LoggerFactory.getLogger(TareaResource.class);

    private static final String ENTITY_NAME = "tarea";

    private final TareaService tareaService;

    public TareaResource(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    /**
     * POST  /tareas : Create a new tarea.
     *
     * @param tarea the tarea to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tarea, or with status 400 (Bad Request) if the tarea has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tareas")
    @Timed
    public ResponseEntity<Tarea> createTarea(@RequestBody Tarea tarea) throws URISyntaxException {
        log.debug("REST request to save Tarea : {}", tarea);
        if (tarea.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tarea cannot already have an ID")).body(null);
        }
        Tarea result = tareaService.save(tarea);
        return ResponseEntity.created(new URI("/api/tareas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tareas : Updates an existing tarea.
     *
     * @param tarea the tarea to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tarea,
     * or with status 400 (Bad Request) if the tarea is not valid,
     * or with status 500 (Internal Server Error) if the tarea couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tareas")
    @Timed
    public ResponseEntity<Tarea> updateTarea(@RequestBody Tarea tarea) throws URISyntaxException {
        log.debug("REST request to update Tarea : {}", tarea);
        if (tarea.getId() == null) {
            return createTarea(tarea);
        }
        Tarea result = tareaService.save(tarea);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tarea.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tareas : get all the tareas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tareas in body
     */
    @GetMapping("/tareas")
    @Timed
    public ResponseEntity<List<Tarea>> getAllTareas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Tareas");
        Page<Tarea> page = tareaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tareas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tareas/:id : get the "id" tarea.
     *
     * @param id the id of the tarea to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tarea, or with status 404 (Not Found)
     */
    @GetMapping("/tareas/{id}")
    @Timed
    public ResponseEntity<Tarea> getTarea(@PathVariable Long id) {
        log.debug("REST request to get Tarea : {}", id);
        Tarea tarea = tareaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tarea));
    }

    /**
     * DELETE  /tareas/:id : delete the "id" tarea.
     *
     * @param id the id of the tarea to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tareas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTarea(@PathVariable Long id) {
        log.debug("REST request to delete Tarea : {}", id);
        tareaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
