package controlador;

import jakarta.servlet.http.HttpSession;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class Controlador {

    private final AuthService authService;
    private final TecnicoService tecnicoService;
    private final ClienteService clienteService;
    private final IncidenciaService incidenciaService;


    @Autowired
    public Controlador(AuthService authService, TecnicoService tecnicoService, ClienteService clienteService, IncidenciaService incidenciaService) {
        this.authService = authService;
        this.tecnicoService = tecnicoService;
        this.clienteService = clienteService;
        this.incidenciaService = incidenciaService;
    }

    @GetMapping("/")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("correo") String correo,
            @RequestParam("clave") String clave,
            HttpSession session,
            Model model
    ) {
        Object usuario = authService.autenticar(correo, clave);

        if (usuario != null) {
            session.setAttribute("usuario", usuario);

            if (usuario instanceof Cliente) {
                session.setAttribute("tipo", "cliente");
            } else if (usuario instanceof Tecnico) {
                session.setAttribute("tipo", "tecnico");
            } else if (usuario instanceof Admin) {
                session.setAttribute("tipo", "admin");
            }

            return "redirect:/inicio";
        }

        model.addAttribute("error", "Correo o clave incorrectos");
        return "login";
    }

    @GetMapping("/inicio")
    public String mostrarInicio(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuario");
        String tipo = (String) session.getAttribute("tipo");

        if (usuario == null || tipo == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("tipo", tipo);
        return "inicio"; // inicio.html, el contenido se adapta según el tipo
    }


    @PostMapping("/admin/gestion-usuarios/registrar")
    public String registrarUsuario(
            HttpSession session,
            Model model,
            @RequestParam("tipo") String tipo,
            @RequestParam("nombre") String nombre,
            @RequestParam("correo") String correo,
            @RequestParam("clave") String clave
    ) {
        if (tipo.equals("tecnico")) {
            // Lógica para guardar un técnico
            // Ejemplo: tecnicoService.guardar(nombre, correo, clave);
        } else if (tipo.equals("cliente")) {
            // Lógica para guardar un cliente
            // Ejemplo: clienteService.guardar(nombre, correo, clave);
        } else {
            model.addAttribute("error", "Tipo de usuario no válido");
            return "menu-usuarios";
        }

        model.addAttribute("mensaje", "Usuario registrado correctamente");
        return "resultado"; // La vista que muestra el resultado del registro
    }


    @GetMapping("/admin/asignar-incidencia")
    public String asignacionIncidencia(HttpSession session, Model model){
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"ADMIN".equals(userRole)) {
            return "redirect:/";
        }

        ArrayList<Incidencia> incidenciasPendientes = incidenciaService.getIncidenciasPendientesAsignar();

        model.addAttribute("incidencias", incidenciasPendientes);

        return "admin-incidencias-a-asignar";
    }

    @PostMapping("/admin/asignar-incidencia/elegirIncidencia")
    public String procesarSeleccionIncidencia(
            @RequestParam("incidenciaId") Integer incidenciaId,
            HttpSession session,
            Model model) {

        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"ADMIN".equals(userRole)) {
            return "redirect:/";
        }

        if (incidenciaId == null) {
            model.addAttribute("error", "No se ha seleccionado ninguna incidencia.");
            return "redirect:/admin/asignar-incidencia";
        }

        return "redirect:/admin/asignar-incidencia/" + incidenciaId + "/elegirTecnico";
    }


    @GetMapping("/admin/asignar-incidencia/{incidenciaId}/elegirTecnico")
    public String mostrarFormularioAsignarTecnico(
            @PathVariable("incidenciaId") Integer incidenciaId,
            HttpSession session,
            Model model) {

        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"ADMIN".equals(userRole)) {
            return "redirect:/";
        }

        if (incidenciaId == null) {
            return "redirect:/admin/asignar-incidencia";
        }

        Incidencia incidencia = incidenciaService.getIncidenciaById(incidenciaId);

        if (incidencia == null) {
            model.addAttribute("error", "Incidencia no encontrada.");
            return "redirect:/admin/asignar-incidencia";
        }

        ArrayList<Tecnico> tecnicos = tecnicoService.getAllTecnicos();

        model.addAttribute("incidencia", incidencia);
        model.addAttribute("tecnicos", tecnicos);

        return "admin-elegir-tecnico";
    }


    @PostMapping("/admin/asignar-incidencia/elegirTecnico")
    public String procesarAsignacionTecnico(
            @RequestParam("incidenciaId") Integer incidenciaId,
            @RequestParam("tecnicoId") Integer tecnicoId,
            HttpSession session,
            Model model) {

        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"ADMIN".equals(userRole)) {
            return "redirect:/";
        }

        if (tecnicoId == null || tecnicoId.equals(0)) {
            Incidencia incidencia = incidenciaService.getIncidenciaById(incidenciaId);
            ArrayList<Tecnico> tecnicos = tecnicoService.getAllTecnicos();
            model.addAttribute("incidencia", incidencia);
            model.addAttribute("tecnicos", tecnicos);
            model.addAttribute("errorAsignacion", "Debes seleccionar un técnico para asignar.");
            return "admin-elegir-tecnico";
        }

        boolean asignacionExitosa = incidenciaService.assignTecnicoToIncidencia(incidenciaId, tecnicoId);

        if (asignacionExitosa) {
            return "redirect:/admin/incidencia/" + incidenciaId;
        } else {
            model.addAttribute("errorAsignacion", "No se pudo asignar la incidencia. Incidencia o técnico no encontrados.");
            Incidencia incidencia = incidenciaService.getIncidenciaById(incidenciaId);
            ArrayList<Tecnico> tecnicos = tecnicoService.getAllTecnicos();
            model.addAttribute("incidencia", incidencia);
            model.addAttribute("tecnicos", tecnicos);
            return "admin-elegir-tecnico";
        }
    }
}