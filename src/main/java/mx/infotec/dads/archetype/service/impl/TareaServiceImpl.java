package mx.infotec.dads.archetype.service.impl;

import mx.infotec.dads.archetype.service.TareaService;
import mx.infotec.dads.archetype.domain.Tarea;
import mx.infotec.dads.archetype.repository.TareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Tarea.
 */
@Service
@Transactional
public class TareaServiceImpl implements TareaService{

    private final Logger log = LoggerFactory.getLogger(TareaServiceImpl.class);

    private final TareaRepository tareaRepository;

    public TareaServiceImpl(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    /**
     * Save a tarea.
     *
     * @param tarea the entity to save
     * @return the persisted entity
     */
    @Override
    public Tarea save(Tarea tarea) {
        log.debug("Request to save Tarea : {}", tarea);
        return tareaRepository.save(tarea);
    }

    /**
     *  Get all the tareas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tarea> findAll(Pageable pageable) {
        log.debug("Request to get all Tareas");
        return tareaRepository.findAll(pageable);
    }

    /**
     *  Get one tarea by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Tarea findOne(Long id) {
        log.debug("Request to get Tarea : {}", id);
        return tareaRepository.findOne(id);
    }

    /**
     *  Delete the  tarea by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarea : {}", id);
        tareaRepository.delete(id);
    }
}
