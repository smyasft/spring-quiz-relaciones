package com.quiz.relaciones.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidad Entrenador
 * Un Entrenador pertenece a UN solo Club.
 * Relacion: @OneToOne (el propietario de la relacion es Club — tiene la FK)
 */
@Entity
@Table(name = "entrenadores")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String apellido;

    @Min(value = 18, message = "La edad minima es 18")
    @Max(value = 90, message = "La edad maxima es 90")
    @Column(nullable = false)
    private int edad;

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String nacionalidad;

    // -------------------------
    //  Constructores
    // -------------------------
    public Entrenador() {}

    public Entrenador(String nombre, String apellido, int edad, String nacionalidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
    }

    // -------------------------
    //  Getters y Setters
    // -------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    @Override
    public String toString() {
        return "Entrenador{id=" + id + ", nombre='" + nombre + " " + apellido + "', edad=" + edad + "}";
    }
}
