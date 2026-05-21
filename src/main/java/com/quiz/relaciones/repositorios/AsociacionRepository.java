package com.quiz.relaciones.repositorios;

import com.quiz.relaciones.entidades.Asociacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Asociacion.
 */
@Repository
public interface AsociacionRepository extends JpaRepository<Asociacion, Long> {

    /** Busca una asociacion por nombre exacto */
    Optional<Asociacion> findByNombreIgnoreCase(String nombre);

    /** Busca asociaciones por pais */
    List<Asociacion> findByPaisIgnoreCase(String pais);

    /** Verifica si existe una asociacion con ese nombre */
    boolean existsByNombreIgnoreCase(String nombre);
}
