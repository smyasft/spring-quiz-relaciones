package com.quiz.relaciones.controladores;

import com.quiz.relaciones.repositorios.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ClubRepository clubRepository;
    private final JugadorRepository jugadorRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final CompeticionRepository competicionRepository;
    private final AsociacionRepository asociacionRepository;

    public HomeController(ClubRepository clubRepository,
                          JugadorRepository jugadorRepository,
                          EntrenadorRepository entrenadorRepository,
                          CompeticionRepository competicionRepository,
                          AsociacionRepository asociacionRepository) {
        this.clubRepository = clubRepository;
        this.jugadorRepository = jugadorRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.competicionRepository = competicionRepository;
        this.asociacionRepository = asociacionRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalClubes",        clubRepository.count());
        model.addAttribute("totalJugadores",     jugadorRepository.count());
        model.addAttribute("totalEntrenadores",  entrenadorRepository.count());
        model.addAttribute("totalCompeticiones", competicionRepository.count());
        model.addAttribute("totalAsociaciones",  asociacionRepository.count());
        model.addAttribute("clubes",             clubRepository.findAll());
        return "index";
    }
}
