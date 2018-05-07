package mx.infotec.dads.archetype.repository;

import mx.infotec.dads.archetype.domain.Trabajo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Trabajo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo,Long> {

    @Query("select distinct trabajo from Trabajo trabajo left join fetch trabajo.tareas")
    List<Trabajo> findAllWithEagerRelationships();

    @Query("select trabajo from Trabajo trabajo left join fetch trabajo.tareas where trabajo.id =:id")
    Trabajo findOneWithEagerRelationships(@Param("id") Long id);

}
