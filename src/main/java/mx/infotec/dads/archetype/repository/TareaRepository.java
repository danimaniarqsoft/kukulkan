package mx.infotec.dads.archetype.repository;

import mx.infotec.dads.archetype.domain.Tarea;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tarea entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TareaRepository extends JpaRepository<Tarea,Long> {

}
