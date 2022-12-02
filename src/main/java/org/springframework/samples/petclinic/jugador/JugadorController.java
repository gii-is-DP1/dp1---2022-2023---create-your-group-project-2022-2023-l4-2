package org.springframework.samples.petclinic.jugador;


import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.samples.petclinic.estadistica.Logro;
import org.springframework.samples.petclinic.estadistica.LogroService;
import org.springframework.samples.petclinic.partida.FaccionType;
import org.springframework.samples.petclinic.partida.Participacion;
import org.springframework.samples.petclinic.partida.Partida;
import org.springframework.samples.petclinic.partida.PartidaService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

public static final String JUGADORES_LISTING = "jugadores/jugadoresList";
public static final String JUGADOR_CREATE = "jugadores/jugadorCreate";
public static final String JUGADOR_PERFIL = "jugadores/jugadorPerfil";
public static final String JUGADOR_HISTORIAL = "jugadores/partidasDelJugador";
public static final String JUGADOR_LOGROS = "jugadores/logrosDelJugador";
public static final String JUGADOR_EDITAR_PERFIL = "jugadores/editPerfil";
public static final String JUGADOR_LISTA_AMIGOS = "jugadores/amigosList";
public static final String JUGADOR_SEARCH = "jugadores/jugadorSearch";
public static final String PARTIDOS_ESPECTAR = "jugadores/listEspectar";
public static final String JUGADOR_AUTO_COMPLETAR = "jugadores/searchFilter";




private JugadorService jugadorService;


@Autowired
public JugadorController(JugadorService jugadorService){
    
    this.jugadorService = jugadorService;

}

@Autowired
public PartidaService partidaService;

@Autowired
private LogroService logroService;

@GetMapping()
public ModelAndView showJugadores() {
    ModelAndView result = new ModelAndView(JUGADORES_LISTING);
    result.addObject("jugadores", jugadorService.getJugadores());
    return result;
}

@GetMapping("/delete/{id}")
public ModelAndView deleteJugador(@PathVariable("id") Integer id){
    jugadorService.deleteJugador(id);
    return new ModelAndView("redirect:/jugadores");
}

@GetMapping("/new")
public String  initCreatinForm(Map<String, Object> model){
    Jugador j = new Jugador();
    model.put("jugador", j);
    return JUGADOR_CREATE;
}

@PostMapping("/new")
public String processCreationForm(@Valid Jugador j, BindingResult br){
    if(br.hasErrors()){
        return JUGADOR_CREATE;
    }else{
        j.setRol(jugadorService.getRoles().get(3));
        this.jugadorService.saveJugador(j);

        return "home";
    }
}
    @GetMapping("/perfil/{username}")
    public ModelAndView showPerfil(@PathVariable("username") String username,  Principal principal){
        ModelAndView res = new ModelAndView(JUGADOR_PERFIL);
        Jugador j = jugadorService.getJugadorByUsername(username);
        if(j==null){
            res = new ModelAndView("redirect:/exception");
        }else{
            res.addObject("jugador", j);
            res.addObject("numPartidasJugadas", j.getPartidasJugadas());
            res.addObject("numPartidasGanadas", j.getPartidasGanadas());
            res.addObject("victoriasComoLeal", j.getVictoriasComoLeal());
            res.addObject("victoriasComoTraidor", j.getVictoriasComoTraidor());
            res.addObject("victoriasComoMercader", j.getVictoriasComoMercader());
            res.addObject("tiempoJugado", j.getTiempoJugado());
            res.addObject("faccionFavorita", j.getFaccionFavorita());
            res.addObject("nombreUsuario", principal.getName());
        }
        return res;
    }

@GetMapping("/perfil/{username}/amigos")
public ModelAndView getAmigosDelJugador(@PathVariable("username") String username) {
    ModelAndView res = new ModelAndView(JUGADOR_LISTA_AMIGOS);
    List<Jugador> amigos = jugadorService.getJugadorByUsername(username).getAmigoDe();
    res.addObject("username", username);
    res.addObject("amigos", amigos);
    return res;
}

@GetMapping("/search")
public ModelAndView getJugadoresSinUsuario(Principal principal) {
    ModelAndView res = new ModelAndView(JUGADOR_SEARCH);
    List<Jugador> jugadores = jugadorService.getJugadores();
    List<Jugador> amigos = jugadorService.getJugadorByUsername(principal.getName()).getAmigoDe();
    res.addObject("jugadores", jugadores);
    res.addObject("amigos", amigos);
    res.addObject("nombreUsuario", principal.getName());
    return res;
}

@PostMapping("/search")
public ModelAndView agregarAmigos(Jugador jugador, Principal principal, BindingResult br) {
    if (br.hasErrors()) {
        return new ModelAndView(JUGADOR_SEARCH);
    } else {
        jugadorService.agregarAmigo(jugador, principal.getName());
        return new ModelAndView("redirect:/jugadores/perfil/"+principal.getName()+"/amigos");
    }
}

@GetMapping(value = "/searchFilter")
public ModelAndView processFindForm(String username, String id, Principal principal) {
    ModelAndView mav = new ModelAndView(JUGADOR_AUTO_COMPLETAR);
    mav.addObject("username", username);
    mav.addObject("nombreUsuario", principal.getName());
    if(id!=null){
        Jugador j = jugadorService.getJugadorById(Integer.parseInt(id)).get();
        jugadorService.agregarAmigo(j, principal.getName());
        return new ModelAndView("redirect:/jugadores/perfil/"+principal.getName()+"/amigos");
    }
    if (username == null || username == "") return mav.addObject("error", "No se han encontrado datos");
    Collection<Jugador> jugadores = this.jugadorService.getJugadoresByUsername(username);
    List<Jugador> amigos = jugadorService.getJugadorByUsername(principal.getName()).getAmigoDe();
    if (jugadores.isEmpty()) {
        mav.addObject("error", "No se han encontrado datos");
        return mav;
    }
    mav.addObject("jugadores", jugadores);
    mav.addObject("amigos", amigos);
    return mav;
}

@GetMapping("/perfil/{username1}/amigos/delete/{username2}")
public ModelAndView deleteAmigo(@PathVariable("username1") String username1,@PathVariable("username2") String username2) throws Exception {
    jugadorService.deleteAmigo(username1, username2);
    return new ModelAndView("redirect:/jugadores/perfil/{username1}/amigos");
}

@GetMapping("/partidas/{username}")
public ModelAndView getPartidasDelJugador(@PathVariable("username") String username) {
    ModelAndView res = new ModelAndView(JUGADOR_HISTORIAL);
    List<Partida> aux = jugadorService.getJugadorByUsername(username).getPartidas();
    res.addObject(username);
    res.addObject("historial", aux);
    return res;
}

@GetMapping("/logros/{username}")
public ModelAndView getLogrosDelJugador(@PathVariable("username") String username) {
    ModelAndView res = new ModelAndView(JUGADOR_LOGROS);
    List<Logro> aux = logroService.getLogros();
    Jugador j = jugadorService.getJugadorByUsername(username);
    res.addObject(username);
    res.addObject("logros", aux);
    res.addObject("partidasJugadas", j.getPartidasJugadas());
    res.addObject("partidasGanadas", j.getPartidasGanadas());
    res.addObject("victoriasLeal", j.getVictoriasComoLeal());
    res.addObject("victoriasTraidor", j.getVictoriasComoTraidor());
    res.addObject("victoriasMercader", j.getVictoriasComoMercader());
    return res;
}

    @GetMapping("/editPerfil/{username}")
    public ModelAndView editPerfilJugador(@PathVariable("username") String username,Principal principal) {
        ModelAndView result = new ModelAndView(JUGADOR_EDITAR_PERFIL);
        Jugador jugador = jugadorService.getJugadorByUsername(username);
        result.addObject(username);
        result.addObject("jugador", jugador);
        result.addObject("nombreUsuario",principal.getName());
        return result;
    }

    @PostMapping("/editPerfil/{username}")
    public ModelAndView savePerfilJugador(Jugador jugador, BindingResult br, @PathVariable("username") String username) {
        if (!br.hasErrors()) {
            jugadorService.editJugador(jugador);
        } else {
            return new ModelAndView(JUGADOR_EDITAR_PERFIL);
        }
    return new ModelAndView("redirect:/jugadores/perfil/{username}");
}

@GetMapping("/espectar/{username}")
public ModelAndView listaPartidasEspectear(@PathVariable("username") String username, Principal principal){
    ModelAndView res = new ModelAndView(PARTIDOS_ESPECTAR);
    Jugador j = jugadorService.getJugadorByUsername(principal.getName());
    List<Partida> aux = jugadorService.getPartidasActivasAmigos(j);
    res.addObject("nombreUsuario",principal.getName());
    res.addObject("partidasAmigos", aux);
    return res;
}

}
