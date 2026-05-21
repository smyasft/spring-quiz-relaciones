package com.quiz.relaciones.repositorios;

import com.quiz.relaciones.entidades.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    Optional<Jugador> findByNumero(int numero);
    List<Jugador> findByPosicionIgnoreCase(String posicion);
    List<Jugador> findByApellidoIgnoreCase(String apellido);
    boolean existsByNumero(int numero);

    /** Jugadores que NO pertenecen a ningun club (id_club es null en BD) */
    @Query("SELECT j FROM Jugador j WHERE j.id NOT IN " +
           "(SELECT jj.id FROM Club c JOIN c.jugadores jj)")
    List<Jugador> findJugadoresDisponibles();
}
