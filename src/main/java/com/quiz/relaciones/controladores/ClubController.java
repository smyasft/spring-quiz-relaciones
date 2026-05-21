package com.quiz.relaciones.controladores;

import com.quiz.relaciones.entidades.*;
import com.quiz.relaciones.repositorios.*;
import com.quiz.relaciones.entidades.Jugador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clubes")
public class ClubController {

    private final ClubRepository        clubRepository;
    private final EntrenadorRepository  entrenadorRepository;
    private final AsociacionRepository  asociacionRepository;
    private final CompeticionRepository competicionRepository;
    private final JugadorRepository     jugadorRepository;

    public ClubController(ClubRepository clubRepository,
                          EntrenadorRepository entrenadorRepository,
                          AsociacionRepository asociacionRepository,
                          CompeticionRepository competicionRepository,
                          JugadorRepository jugadorRepository) {
        this.clubRepository        = clubRepository;
        this.entrenadorRepository  = entrenadorRepository;
        this.asociacionRepository  = asociacionRepository;
        this.competicionRepository = competicionRepository;
        this.jugadorRepository     = jugadorRepository;
    }

    // ── Lista ──────────────────────────────────────────────
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("clubes", clubRepository.findAll());
        return "clubes/lista";
    }

    // ── Detalle ────────────────────────────────────────────
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club no encontrado: " + id));
        model.addAttribute("club", club);
        model.addAttribute("nuevoJugador", new Jugador());
        return "clubes/detalle";
    }

    // ── Formulario nuevo club ──────────────────────────────
    @GetMapping("/nuevo")
    public String formNuevo(Model model) {
        cargarSelects(model);
        model.addAttribute("club", new Club());
        model.addAttribute("titulo", "Nuevo Club");
        return "clubes/form";
    }

    // ── Formulario editar club ─────────────────────────────
    @GetMapping("/editar/{id}")
    public String formEditar(@PathVariable Long id, Model model) {
        cargarSelects(model);
        model.addAttribute("club", clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club no encontrado: " + id)));
        model.addAttribute("titulo", "Editar Club");
        model.addAttribute("jugadoresDisponibles", jugadorRepository.findJugadoresDisponibles());
        return "clubes/form";
    }

    // ── Guardar (nuevo y editar) — recibe IDs de FK por separado ──
    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(required = false) Long id,
            @RequestParam String nombre,
            @RequestParam String ciudad,
            @RequestParam int anioFundacion,
            @RequestParam(required = false) Long entrenadorId,
            @RequestParam(required = false) Long asociacionId,
            @RequestParam(required = false) List<Long> competicionIds,
            @RequestParam(required = false) List<Long> jugadoresIds,
            RedirectAttributes ra) {

        // Reutilizar existente o crear nuevo
        Club club = (id != null) ? clubRepository.findById(id).orElse(new Club()) : new Club();
        club.setNombre(nombre);
        club.setCiudad(ciudad);
        club.setAnioFundacion(anioFundacion);

        // @OneToOne — asignar entrenador
        if (entrenadorId != null) {
            club.setEntrenador(entrenadorRepository.findById(entrenadorId).orElse(null));
        } else {
            club.setEntrenador(null);
        }

        // @ManyToOne — asignar asociacion
        if (asociacionId != null) {
            club.setAsociacion(asociacionRepository.findById(asociacionId).orElse(null));
        } else {
            club.setAsociacion(null);
        }

        // @ManyToMany — asignar competiciones
        club.getCompeticiones().clear();
        if (competicionIds != null) {
            for (Long cid : competicionIds) {
                competicionRepository.findById(cid).ifPresent(club::inscribirCompeticion);
            }
        }

        // Asignar jugadores disponibles seleccionados
        if (jugadoresIds != null) {
            for (Long jid : jugadoresIds) {
                jugadorRepository.findById(jid).ifPresent(club::agregarJugador);
            }
        }

        Club guardado = clubRepository.save(club);
        ra.addFlashAttribute("exito", id == null
                ? "Club creado. Ahora puedes agregar jugadores al plantel."
                : "Club actualizado correctamente.");
        return "redirect:/clubes/" + guardado.getId();
    }

    // ── Agregar jugador directamente desde detalle del club ─
    @PostMapping("/{clubId}/jugadores/agregar")
    public String agregarJugador(
            @PathVariable Long clubId,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam int numero,
            @RequestParam String posicion,
            RedirectAttributes ra) {

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club no encontrado: " + clubId));

        Jugador j = new Jugador(nombre, apellido, numero, posicion);
        club.agregarJugador(j);
        clubRepository.save(club);

        ra.addFlashAttribute("exito", "Jugador agregado correctamente.");
        return "redirect:/clubes/" + clubId;
    }

    // ── Asignar jugadores disponibles (desde editar) ──────────
    @PostMapping("/{clubId}/jugadores/asignar-varios")
    public String asignarVarios(
            @PathVariable Long clubId,
            @RequestParam(required = false) List<Long> jugadoresIds,
            RedirectAttributes ra) {

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club no encontrado"));

        if (jugadoresIds != null) {
            for (Long jid : jugadoresIds) {
                jugadorRepository.findById(jid).ifPresent(club::agregarJugador);
            }
            clubRepository.save(club);
            ra.addFlashAttribute("exito", jugadoresIds.size() + " jugador(es) asignado(s) correctamente.");
        }
        return "redirect:/clubes/editar/" + clubId;
    }

    // ── Eliminar jugador de un club ─────────────────────────
    @GetMapping("/{clubId}/jugadores/eliminar/{jugadorId}")
    public String eliminarJugador(@PathVariable Long clubId,
                                  @PathVariable Long jugadorId,
                                  RedirectAttributes ra) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club no encontrado"));
        club.getJugadores().removeIf(j -> j.getId().equals(jugadorId));
        clubRepository.save(club);
        ra.addFlashAttribute("exito", "Jugador eliminado.");
        return "redirect:/clubes/" + clubId;
    }

    // ── Eliminar club ───────────────────────────────────────
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        clubRepository.deleteById(id);
        ra.addFlashAttribute("exito", "Club eliminado.");
        return "redirect:/clubes";
    }

    // ── Helper ─────────────────────────────────────────────
    private void cargarSelects(Model model) {
        model.addAttribute("entrenadores",  entrenadorRepository.findAll());
        model.addAttribute("asociaciones",  asociacionRepository.findAll());
        model.addAttribute("competiciones", competicionRepository.findAll());
    }
}
