package mx.infotec.dads.archetype.repository;

import mx.infotec.dads.archetype.domain.Departamento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Departamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento,Long> {

}
