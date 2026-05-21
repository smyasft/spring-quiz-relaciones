package com.quiz.relaciones.repositorios;

import com.quiz.relaciones.entidades.Competicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Competicion.
 */
@Repository
public interface CompeticionRepository extends JpaRepository<Competicion, Long> {

    /** Busca una competicion por nombre */
    Optional<Competicion> findByNombreIgnoreCase(String nombre);

    /** Busca competiciones activas en una fecha dada */
    List<Competicion> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            LocalDate fecha, LocalDate fecha2);

    /** Busca competiciones con monto de premio mayor o igual al indicado */
    List<Competicion> findByMontoPremioGreaterThanEqual(int montoPremio);

    /** Busca competiciones en las que participa un club especifico */
    @Query("SELECT comp FROM Club cl JOIN cl.competiciones comp WHERE cl.id = :clubId")
    List<Competicion> findByClubId(Long clubId);

    /** Verifica si ya existe una competicion con ese nombre */
    boolean existsByNombreIgnoreCase(String nombre);
}
