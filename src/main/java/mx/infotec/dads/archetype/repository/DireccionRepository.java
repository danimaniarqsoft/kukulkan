package mx.infotec.dads.archetype.repository;

import mx.infotec.dads.archetype.domain.Direccion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Direccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DireccionRepository extends JpaRepository<Direccion,Long> {

}
