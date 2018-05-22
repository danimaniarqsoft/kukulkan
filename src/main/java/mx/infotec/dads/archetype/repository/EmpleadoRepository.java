package mx.infotec.dads.archetype.repository;

import mx.infotec.dads.archetype.domain.Empleado;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Empleado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

    @Query("select distinct empleado from Empleado empleado left join fetch empleado.tasks")
    List<Empleado> findAllWithEagerRelationships();

    @Query("select empleado from Empleado empleado left join fetch empleado.tasks where empleado.id =:id")
    Empleado findOneWithEagerRelationships(@Param("id") Long id);

}
