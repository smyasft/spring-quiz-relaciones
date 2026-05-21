package com.quiz.relaciones.repositorios;

import com.quiz.relaciones.entidades.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Club.
 * Extiende JpaRepository para obtener CRUD completo automaticamente.
 */
@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    /** Busca un club por nombre exacto (ignorando mayusculas/minusculas) */
    Optional<Club> findByNombreIgnoreCase(String nombre);

    /** Busca todos los clubes de una ciudad */
    List<Club> findByCiudadIgnoreCase(String ciudad);

    /** Busca clubes afiliados a una asociacion por su ID */
    List<Club> findByAsociacionId(Long asociacionId);

    /** Busca clubes que participan en una competicion por su ID */
    @Query("SELECT c FROM Club c JOIN c.competiciones comp WHERE comp.id = :competicionId")
    List<Club> findByCompeticionId(@Param("competicionId") Long competicionId);

    /** Verifica si ya existe un club con ese nombre */
    boolean existsByNombreIgnoreCase(String nombre);
}
