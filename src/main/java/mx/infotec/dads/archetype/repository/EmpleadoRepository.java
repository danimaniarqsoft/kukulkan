package mx.infotec.dads.archetype.repository;

import mx.infotec.dads.archetype.domain.Empleado;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Empleado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

}
