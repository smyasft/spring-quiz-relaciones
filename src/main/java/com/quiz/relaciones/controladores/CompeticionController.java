package com.quiz.relaciones.controladores;

import com.quiz.relaciones.repositorios.CompeticionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/competiciones")
public class CompeticionController {

    private final CompeticionRepository competicionRepository;

    public CompeticionController(CompeticionRepository competicionRepository) {
        this.competicionRepository = competicionRepository;
    }

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("competiciones", competicionRepository.findAll());
        return "competiciones/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("competicion", competicionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competicion no encontrada: " + id)));
        return "competiciones/detalle";
    }
}
