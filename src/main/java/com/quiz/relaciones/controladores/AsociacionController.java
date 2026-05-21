package com.quiz.relaciones.controladores;

import com.quiz.relaciones.repositorios.AsociacionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asociaciones")
public class AsociacionController {

    private final AsociacionRepository asociacionRepository;

    public AsociacionController(AsociacionRepository asociacionRepository) {
        this.asociacionRepository = asociacionRepository;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        return "asociaciones/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("asociacion", asociacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asociacion no encontrada: " + id)));
        return "asociaciones/detalle";
    }
}
