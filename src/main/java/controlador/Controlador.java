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

    //PAGINA LOGIN
    @GetMapping("/")
    public String mostrarPaginaLogin(HttpSession session) {
        if (session.getAttribute("usuarioAutenticado") != null) {
            return "redirect:/inicio";
        }
        return "login";
    }

    @PostMapping("/login")
    public String iniciarSesionDesdeWeb(
            @RequestParam("correo") String correo,
            @RequestParam("clave") String clave,
            Model model,
            HttpSession session) {

        Object usuarioAutenticado = authService.autenticar(correo, clave);
        String userRole = null;

        if (usuarioAutenticado != null) {
            if (usuarioAutenticado instanceof Cliente) {
                userRole = "CLIENTE";
            } else if (usuarioAutenticado instanceof Tecnico) {
                userRole = "TECNICO";
            } else if (usuarioAutenticado instanceof Admin) {
                userRole = "ADMIN";
            }

            if (userRole != null) {
                session.setAttribute("usuarioAutenticado", usuarioAutenticado);
                session.setAttribute("userRole", userRole);
                return "redirect:/inicio";
            }
        }

        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "login";
    }

    //PAGINA DE INICIO TODOS LOS USUARIOS
    @GetMapping("/inicio")
    public String mostrarInicio(HttpSession session, Model model) {
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || userRole == null) {
            return "redirect:/";
        }

        model.addAttribute("usuario", usuarioAutenticado);
        model.addAttribute("userRole", userRole);

        switch (userRole) {
            case "CLIENTE":
                return "dashboard-cliente";
            case "TECNICO":
                return "dashboard-tecnico";
            case "ADMIN":
                return "dashboard-admin";
            default:
                return "redirect:/";
        }
    }

    //CERRAR SESIÓN EN TODOS LOS USUARIOS
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //ADMIN --> REGISTRAR USUARIOS
    @GetMapping("/admin/gestion-usuarios")
    public String mostrarMenuUsuarios(Model model) {
        return "admin-eleccion-tipo";
    }

    @PostMapping("/admin/gestion-usuarios/tipo")
    public String mostrarFormularioDeRegistro(
            @RequestParam("tipo") String tipo,
            Model model
    ) {
        if (!tipo.equals("cliente") && !tipo.equals("tecnico")) {
            model.addAttribute("error", "Tipo de usuario no válido");
            return "admin-eleccion-tipo";
        }

        model.addAttribute("tipo", tipo); // Pasamos el tipo al siguiente formulario
        return "admin-introduccion-datos";
    }

    @PostMapping("/admin/gestion-usuarios/registro")
    public String registrarUsuario(
            @RequestParam("tipo") String tipo,
            @RequestParam("nombre") String nombre,
            @RequestParam("correo") String correo,
            @RequestParam("clave") String clave,
            Model model
    ) {
        if (tipo.equals("tecnico")) {
            tecnicoService.guardar(correo, clave, nombre);
        } else if (tipo.equals("cliente")) {
            clienteService.guardar(correo, clave, nombre);
        } else {
            model.addAttribute("error", "Tipo de usuario no válido");
            return "menu-usuarios";
        }

        model.addAttribute("mensaje", "Usuario registrado correctamente");
        return "registro-exitoso";
    }

    //ADMIN --> ASIGNAR INCIDENCIA
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

    //ADMIN --> VER TODOS LOS TICKETS(INCIDENCIAS)
    @GetMapping("/admin/verIncidencias")
    public String mostrarPagIncidencias(HttpSession session, Model model){
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || userRole == null) {
            return "redirect:/";
        }

        ArrayList<Incidencia> incidenciasActuales = incidenciaService.getTodasIncidencias();

        model.addAttribute("incidencias", incidenciasActuales);

        return "admin-ver-todas-incidencias";
    }

    //CLIENTE --> CREAR NUEVA INCIDENCIA
    @GetMapping("cliente/nueva-incidencia")
    public String mostrarMenuNuevaIncidencia(Model model){
        return "cliente-formulario-nueva-incidencia";
    }

    @PostMapping("/cliente/nueva-incidencia")
    public String crearNuevaIncidencia(
            HttpSession session,
            Model model,
            @RequestParam("contenido") String contenido){

        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || userRole == null) {
            return "redirect:/";
        }
        Cliente cliente = (Cliente) usuarioAutenticado;

        incidenciaService.nuevaIncidencia(contenido, cliente);

        return "registro-nueva-incidencia-exitoso";
    }

    //CLIENTE --> HISTORIAL INCIDENCIAS PROPIAS
    @GetMapping("cliente/historial-incidencias")
    public String mostrarHistorialIncidencias(HttpSession session, Model model){
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || userRole == null) {
            return "redirect:/";
        }

        Cliente cliente = (Cliente) usuarioAutenticado;
        ArrayList<Incidencia> incidenciasPropiasCliente = incidenciaService.getIncidenciasCliente(cliente);

        model.addAttribute("incidencias", incidenciasPropiasCliente);
        return "cliente-historial-incidencias";
    }
}
