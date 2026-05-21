package com.quiz.relaciones.repositorios;

import com.quiz.relaciones.entidades.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Entrenador.
 */
@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {

    /** Busca entrenadores por apellido */
    List<Entrenador> findByApellidoIgnoreCase(String apellido);

    /** Busca entrenadores por nacionalidad */
    List<Entrenador> findByNacionalidadIgnoreCase(String nacionalidad);

    /** Busca un entrenador por nombre y apellido */
    Optional<Entrenador> findByNombreIgnoreCaseAndApellidoIgnoreCase(String nombre, String apellido);

    /** Busca entrenadores menores o iguales a una edad determinada */
    List<Entrenador> findByEdadLessThanEqual(int edad);
}
