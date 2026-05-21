package com.quiz.relaciones.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad Asociacion
 * Una Asociacion puede tener MUCHOS Clubes afiliados.
 * Relacion: @OneToMany (desde Club hacia Asociacion es @ManyToOne)
 */
@Entity
@Table(name = "asociaciones")
public class Asociacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la asociacion es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El pais es obligatorio")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String pais;

    @NotBlank(message = "El presidente es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String presidente;

    // -------------------------
    //  Constructores
    // -------------------------
    public Asociacion() {}

    public Asociacion(String nombre, String pais, String presidente) {
        this.nombre = nombre;
        this.pais = pais;
        this.presidente = presidente;
    }

    // -------------------------
    //  Getters y Setters
    // -------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getPresidente() { return presidente; }
    public void setPresidente(String presidente) { this.presidente = presidente; }

    @Override
    public String toString() {
        return "Asociacion{id=" + id + ", nombre='" + nombre + "', pais='" + pais + "'}";
    }
}
