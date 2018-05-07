package mx.infotec.dads.archetype.service;

import mx.infotec.dads.archetype.domain.Direccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Direccion.
 */
public interface DireccionService {

    /**
     * Save a direccion.
     *
     * @param direccion the entity to save
     * @return the persisted entity
     */
    Direccion save(Direccion direccion);

    /**
     *  Get all the direccions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Direccion> findAll(Pageable pageable);

    /**
     *  Get the "id" direccion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Direccion findOne(Long id);

    /**
     *  Delete the "id" direccion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
