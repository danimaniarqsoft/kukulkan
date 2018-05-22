package mx.infotec.dads.archetype.service.impl;

import mx.infotec.dads.archetype.service.TrabajoService;
import mx.infotec.dads.archetype.domain.Trabajo;
import mx.infotec.dads.archetype.repository.TrabajoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Trabajo.
 */
@Service
@Transactional
public class TrabajoServiceImpl implements TrabajoService{

    private final Logger log = LoggerFactory.getLogger(TrabajoServiceImpl.class);

    private final TrabajoRepository trabajoRepository;

    public TrabajoServiceImpl(TrabajoRepository trabajoRepository) {
        this.trabajoRepository = trabajoRepository;
    }

    /**
     * Save a trabajo.
     *
     * @param trabajo the entity to save
     * @return the persisted entity
     */
    @Override
    public Trabajo save(Trabajo trabajo) {
        log.debug("Request to save Trabajo : {}", trabajo);
        return trabajoRepository.save(trabajo);
    }

    /**
     *  Get all the trabajos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trabajo> findAll(Pageable pageable) {
        log.debug("Request to get all Trabajos");
        return trabajoRepository.findAll(pageable);
    }

    /**
     *  Get one trabajo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Trabajo findOne(Long id) {
        log.debug("Request to get Trabajo : {}", id);
        return trabajoRepository.findOne(id);
    }

    /**
     *  Delete the  trabajo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trabajo : {}", id);
        trabajoRepository.delete(id);
    }
}
