package com.quiz.relaciones.controladores;

import com.quiz.relaciones.entidades.Entrenador;
import com.quiz.relaciones.repositorios.EntrenadorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/entrenadores")
public class EntrenadorController {

    private final EntrenadorRepository entrenadorRepository;

    public EntrenadorController(EntrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        return "entrenadores/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("entrenador", entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado")));
        return "entrenadores/detalle";
    }

    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        model.addAttribute("entrenador", new Entrenador());
        model.addAttribute("titulo", "Nuevo Entrenador");
        return "entrenadores/form";
    }

    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable Long id, Model model) {
        model.addAttribute("entrenador", entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado")));
        model.addAttribute("titulo", "Editar Entrenador");
        return "entrenadores/form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(required = false) Long id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam int edad,
            @RequestParam String nacionalidad,
            RedirectAttributes ra) {

        Entrenador e = (id != null) ? entrenadorRepository.findById(id).orElse(new Entrenador()) : new Entrenador();
        e.setNombre(nombre);
        e.setApellido(apellido);
        e.setEdad(edad);
        e.setNacionalidad(nacionalidad);
        entrenadorRepository.save(e);

        ra.addFlashAttribute("exito", "Entrenador guardado correctamente.");
        return "redirect:/entrenadores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        entrenadorRepository.deleteById(id);
        ra.addFlashAttribute("exito", "Entrenador eliminado.");
        return "redirect:/entrenadores";
    }
}
