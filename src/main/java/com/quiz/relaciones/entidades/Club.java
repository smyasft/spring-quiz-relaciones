package com.quiz.relaciones.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Club — entidad central del dominio.
 *
 * RELACIONES:
 * ┌───────────────────────────────────────────────────────────┐
 * │  @OneToOne   →  Entrenador   (FK: entrenador_id en clubes)│
 * │  @OneToMany  →  Jugador      (FK: id_club en jugadores)   │
 * │  @ManyToOne  →  Asociacion   (FK: asociacion_id en clubes)│
 * │  @ManyToMany →  Competicion  (tabla intermedia generada)  │
 * └───────────────────────────────────────────────────────────┘
 */
@Entity
@Table(name = "clubes")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del club es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String ciudad;

    @Min(value = 1800)
    @Column(nullable = false)
    private int anioFundacion;

    // =========================================================
    //  RELACION 1: @OneToOne  ←→  Entrenador
    //  Un Club tiene UN solo Entrenador.
    //  Club es el PROPIETARIO → FK "entrenador_id" vive en clubes.
    //  FetchType.LAZY: se carga solo cuando se accede al atributo.
    // =========================================================
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "entrenador_id", nullable = true)
    private Entrenador entrenador;

    // =========================================================
    //  RELACION 2: @OneToMany  ←→  Jugador
    //  Un Club tiene MUCHOS Jugadores.
    //  @JoinColumn evita tabla intermedia → FK "id_club" en jugadores.
    //  FetchType.LAZY (por defecto en colecciones).
    // =========================================================
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_club")
    private List<Jugador> jugadores = new ArrayList<>();

    // =========================================================
    //  RELACION 3: @ManyToOne  ←→  Asociacion
    //  MUCHOS Clubes pertenecen a UNA Asociacion.
    //  FK "asociacion_id" vive en clubes.
    //  FetchType.LAZY recomendado para evitar problema N+1.
    // =========================================================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asociacion_id", nullable = true)
    private Asociacion asociacion;

    // =========================================================
    //  RELACION 4: @ManyToMany  ←→  Competicion
    //  Un Club puede participar en MUCHAS Competiciones.
    //  Una Competicion puede tener MUCHOS Clubes.
    //  JPA crea tabla intermedia: "clubes_competiciones".
    // =========================================================
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "clubes_competiciones",
        joinColumns        = @JoinColumn(name = "club_id"),
        inverseJoinColumns = @JoinColumn(name = "competicion_id")
    )
    private List<Competicion> competiciones = new ArrayList<>();

    // -------------------------
    //  Constructores
    // -------------------------
    public Club() {}

    public Club(String nombre, String ciudad, int anioFundacion) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.anioFundacion = anioFundacion;
    }

    // =========================================================
    //  Metodos utilitarios para manejar relaciones
    // =========================================================

    /** Agrega un jugador al plantel y mantiene consistencia */
    public void agregarJugador(Jugador jugador) {
        this.jugadores.add(jugador);
    }

    /** Inscribe el club en una competicion */
    public void inscribirCompeticion(Competicion competicion) {
        this.competiciones.add(competicion);
    }

    /** Retira el club de una competicion */
    public void retirarCompeticion(Competicion competicion) {
        this.competiciones.remove(competicion);
    }

    // -------------------------
    //  Getters y Setters
    // -------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public int getAnioFundacion() { return anioFundacion; }
    public void setAnioFundacion(int anioFundacion) { this.anioFundacion = anioFundacion; }

    public Entrenador getEntrenador() { return entrenador; }
    public void setEntrenador(Entrenador entrenador) { this.entrenador = entrenador; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public Asociacion getAsociacion() { return asociacion; }
    public void setAsociacion(Asociacion asociacion) { this.asociacion = asociacion; }

    public List<Competicion> getCompeticiones() { return competiciones; }
    public void setCompeticiones(List<Competicion> competiciones) { this.competiciones = competiciones; }

    @Override
    public String toString() {
        return "Club{id=" + id + ", nombre='" + nombre + "', ciudad='" + ciudad + "', anioFundacion=" + anioFundacion + "}";
    }
}
