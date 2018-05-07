package mx.infotec.dads.archetype.service;

import mx.infotec.dads.archetype.domain.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tarea.
 */
public interface TareaService {

    /**
     * Save a tarea.
     *
     * @param tarea the entity to save
     * @return the persisted entity
     */
    Tarea save(Tarea tarea);

    /**
     *  Get all the tareas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tarea> findAll(Pageable pageable);

    /**
     *  Get the "id" tarea.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tarea findOne(Long id);

    /**
     *  Delete the "id" tarea.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
