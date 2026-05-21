package com.quiz.relaciones.controladores;

import com.quiz.relaciones.repositorios.JugadorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorRepository jugadorRepository;

    public JugadorController(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("jugadores", jugadorRepository.findAll());
        return "jugadores/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("jugador", jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado")));
        return "jugadores/detalle";
    }
}
