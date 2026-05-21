package com.quiz.relaciones.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidad Jugador
 * MUCHOS Jugadores pertenecen a UN Club.
 * Relacion: @OneToMany desde Club (la FK "id_club" vive en esta tabla)
 */
@Entity
@Table(name = "jugadores")
public class Jugador {

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

    @Min(value = 1, message = "El numero debe ser mayor o igual a 1")
    @Max(value = 99, message = "El numero no puede superar 99")
    @Column(nullable = false)
    private int numero;

    @NotBlank(message = "La posicion es obligatoria")
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String posicion;

    // -------------------------
    //  Constructores
    // -------------------------
    public Jugador() {}

    public Jugador(String nombre, String apellido, int numero, String posicion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.posicion = posicion;
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

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }

    @Override
    public String toString() {
        return "Jugador{id=" + id + ", nombre='" + nombre + " " + apellido + "', numero=" + numero + ", posicion='" + posicion + "'}";
    }
}
