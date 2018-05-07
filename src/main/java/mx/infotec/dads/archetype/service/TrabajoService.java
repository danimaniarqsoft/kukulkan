package mx.infotec.dads.archetype.service;

import mx.infotec.dads.archetype.domain.Trabajo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Trabajo.
 */
public interface TrabajoService {

    /**
     * Save a trabajo.
     *
     * @param trabajo the entity to save
     * @return the persisted entity
     */
    Trabajo save(Trabajo trabajo);

    /**
     *  Get all the trabajos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Trabajo> findAll(Pageable pageable);

    /**
     *  Get the "id" trabajo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Trabajo findOne(Long id);

    /**
     *  Delete the "id" trabajo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
