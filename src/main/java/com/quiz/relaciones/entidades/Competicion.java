package com.quiz.relaciones.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Entidad Competicion
 * UNA Competicion puede tener MUCHOS Clubes participantes.
 * Relacion: @ManyToMany con Club (la tabla intermedia "clubes_competiciones" es generada por JPA)
 */
@Entity
@Table(name = "competiciones")
public class Competicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la competicion es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Min(value = 0, message = "El monto del premio no puede ser negativo")
    @Column(nullable = false)
    private int montoPremio;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaFin;

    // -------------------------
    //  Constructores
    // -------------------------
    public Competicion() {}

    public Competicion(String nombre, int montoPremio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.montoPremio = montoPremio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // -------------------------
    //  Getters y Setters
    // -------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getMontoPremio() { return montoPremio; }
    public void setMontoPremio(int montoPremio) { this.montoPremio = montoPremio; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    @Override
    public String toString() {
        return "Competicion{id=" + id + ", nombre='" + nombre + "', montoPremio=" + montoPremio + "}";
    }
}
