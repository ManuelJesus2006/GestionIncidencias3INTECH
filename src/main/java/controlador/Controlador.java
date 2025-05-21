package controlador;

import DAO.DAOAdminSQL;
import DAO.DAOManager;
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
    private final DAOManager dao = DAOManager.getSingletonInstance();


    @Autowired
    public Controlador(AuthService authService, TecnicoService tecnicoService, ClienteService clienteService, IncidenciaService incidenciaService) {
        this.authService = authService;
        this.tecnicoService = tecnicoService;
        this.clienteService = clienteService;
        this.incidenciaService = incidenciaService;
        mockAdmin();
    }

    private void mockAdmin() {
        DAOAdminSQL daoAdmin = new DAOAdminSQL();
        if (daoAdmin.readAdmin(dao) == null){
            Admin admin = new Admin("ADM1111","root@root","1234root","ROOT");
            daoAdmin.insertAdmin(dao, admin);
        }
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

    //ADMIN --> GESTIÓN DE USUARIOS
    @GetMapping("/admin/gestion-usuarios")
    public String mostrarMenuGestionUsuarios(Model model) {
        return "admin-menu-gestion-usuarios"; // Nueva vista para elegir acción
    }

    @GetMapping("/admin/gestion-usuarios/registrar")
    public String mostrarFormularioRegistro(@RequestParam(value = "tipo", required = false) String tipo, Model model) {
        if (tipo != null && !tipo.equals("cliente") && !tipo.equals("tecnico")) {
            model.addAttribute("error", "Tipo de usuario no válido");
            return "admin-menu-gestion-usuarios";
        }
        model.addAttribute("tipo", tipo);
        return "admin-introduccion-datos"; // Formulario para registrar
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
            model.addAttribute("mensaje", "Técnico registrado correctamente");
        } else if (tipo.equals("cliente")) {
            clienteService.guardar(correo, clave, nombre);
            model.addAttribute("mensaje", "Cliente registrado correctamente");
        } else {
            model.addAttribute("error", "Tipo de usuario no válido");
            return "admin-menu-gestion-usuarios";
        }
        return "registro-exitoso";
    }

    @GetMapping("/admin/gestion-usuarios/baja")
    public String mostrarFormularioBaja(Model model) {
        ArrayList<Object> todosUsuarios = new ArrayList<>();
        todosUsuarios.addAll(tecnicoService.getAllTecnicos());
        todosUsuarios.addAll(clienteService.getAllClientes());
        model.addAttribute("usuarios", todosUsuarios);
        return "admin-baja-usuario";
    }

    @PostMapping("/admin/gestion-usuarios/baja/ejecutar")
    public String darDeBajaUsuario(
            @RequestParam("usuarioId") String usuarioId, // Cambiamos String a Long
            Model model
    ) {
        boolean bajaExitosa = false;
        // Intentamos primero con Tecnico
        if (tecnicoService.existeTecnico(usuarioId)) {
            bajaExitosa = tecnicoService.darDeBaja(usuarioId);
            if (bajaExitosa) {
                model.addAttribute("mensaje", "Técnico dado de baja correctamente");
            }
        } else if (clienteService.existeCliente(usuarioId)) { // Si no es técnico, intentamos con Cliente
            bajaExitosa = clienteService.darDeBaja(usuarioId);
            if (bajaExitosa) {
                model.addAttribute("mensaje", "Cliente dado de baja correctamente");
            }
        }

        if (!bajaExitosa) {
            model.addAttribute("error", "No se pudo dar de baja al usuario con ID: " + usuarioId);
        }

        // Recargamos la lista de usuarios para la vista
        ArrayList<Object> todosUsuarios = new ArrayList<>();
        todosUsuarios.addAll(tecnicoService.getAllTecnicos());
        todosUsuarios.addAll(clienteService.getAllClientes());
        model.addAttribute("usuarios", todosUsuarios);

        return "admin-baja-usuario";
    }

    @GetMapping("/admin/gestion-usuarios/modificar")
    public String mostrarFormularioModificar(Model model) {
        ArrayList<Object> todosUsuarios = new ArrayList<>();
        todosUsuarios.addAll(tecnicoService.getAllTecnicos());
        todosUsuarios.addAll(clienteService.getAllClientes());
        model.addAttribute("usuarios", todosUsuarios);
        model.addAttribute("usuarioAModificar", null);
        return "admin-modificar-usuario"; // Modificamos esta vista
    }

    @PostMapping("/admin/gestion-usuarios/modificar/seleccionar")
    public String seleccionarUsuarioAModificar(
            @RequestParam("usuarioId") String usuarioId,
            Model model
    ) {
        Object usuarioAModificar = null;
        String tipo = null;

        if (tecnicoService.existeTecnico(usuarioId)) {
            usuarioAModificar = tecnicoService.obtenerPorId(usuarioId);
            tipo = "tecnico";
        } else if (clienteService.existeCliente(usuarioId)) {
            usuarioAModificar = clienteService.obtenerPorId(usuarioId);
            tipo = "cliente";
        } else {
            model.addAttribute("error", "No se encontró el usuario con ID: " + usuarioId);
            ArrayList<Object> todosUsuarios = new ArrayList<>();
            todosUsuarios.addAll(tecnicoService.getAllTecnicos());
            todosUsuarios.addAll(clienteService.getAllClientes());
            model.addAttribute("usuarios", todosUsuarios);
            return "admin-modificar-usuario"; // Volvemos a la lista de selección con error
        }

        model.addAttribute("usuarioAModificar", usuarioAModificar);
        model.addAttribute("tipo", tipo);
        model.addAttribute("usuarioSeleccionadoId", usuarioId);
        return "admin-formulario-modificar-usuario"; // Mostramos el formulario de modificación
    }

    @PostMapping("/admin/gestion-usuarios/modificar/ejecutar")
    public String ejecutarModificacionUsuario(
            @RequestParam("tipo") String tipo,
            @RequestParam("usuarioId") String usuarioId,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "correo", required = false) String correo,
            @RequestParam(value = "clave", required = false) String clave,
            Model model
    ) {
        boolean modificacionExitosa = false;

        if (tipo.equals("tecnico")) {
            modificacionExitosa = tecnicoService.modificar(usuarioId, nombre, correo, clave);
            if (modificacionExitosa) {
                model.addAttribute("mensaje", "Técnico modificado correctamente");
            } else {
                model.addAttribute("error", "No se pudo modificar al técnico con ID: " + usuarioId);
                return "admin-formulario-modificar-usuario"; // Volver al formulario en caso de error
            }
        }
        if (tipo.equals("cliente")) {
            modificacionExitosa = clienteService.modificar(usuarioId, nombre, correo, clave);
            if (modificacionExitosa) {
                model.addAttribute("mensaje", "Cliente modificado correctamente");
            } else {
                model.addAttribute("error", "No se pudo modificar al cliente con ID: " + usuarioId);
                return "admin-formulario-modificar-usuario"; // Volver al formulario en caso de error
            }
        }
        if (!tipo.equals("tecnico") && !tipo.equals("cliente")){
            model.addAttribute("error", "Tipo de usuario para modificar no válido");
            return "admin-formulario-modificar-usuario";
        }

        if (modificacionExitosa) {
            return "registro-exitoso"; // Redirigir a la página de éxito
        } else {
            return "admin-formulario-modificar-usuario"; // Volver al formulario en caso de error
        }
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
            @RequestParam("tecnicoid") String tecnicoId,
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
            return "asignacion-incidencia-exitosa";
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

    //CLIENTE --> CONSULTAR ESTADO INCIDENCIA CONCRETA
    @GetMapping("/cliente/consultar-incidencia")
    public String mostraFormularioIncidenciaConcreta(Model model){
        return "cliente-formulario-introducir-id";
    }

    @PostMapping("/cliente/consultar-incidencia/id")
    public String buscarIncidenciaConcreta(
            @RequestParam("idIncidencia") int idIncidencia,
            HttpSession session,
            Model model
            ){
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        Cliente cliente = (Cliente) usuarioAutenticado;
        Incidencia incidencia = incidenciaService.getIncidenciaByIdCliente(idIncidencia, cliente);
        model.addAttribute("incidencia", incidencia);
        return "cliente-mostrar-estado-incidencia";
    }

    //TECNICO --> VER INCIDENCIAS ASIGNADAS
    @GetMapping("/tecnico/incidencias-asignadas")
    public String verIncidenciasAsignadas(HttpSession session, Model model){
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || userRole == null) {
            return "redirect:/";
        }

        Tecnico tecnico = (Tecnico) usuarioAutenticado;
        ArrayList<Incidencia> incidenciasAsignadasTecnico = incidenciaService.getIncidenciasAsignadas(tecnico);

        model.addAttribute("incidencias", incidenciasAsignadasTecnico);
        return "tecnico-incidencias-asignadas";
    }

    //TECNICO --> MARCAR INCIDENCIA COMO RESUELTA
    @GetMapping("/tecnico/marcar-resuelta/elegir-incidencia")
    public String elegirIncidenciaParaResuelta(HttpSession session, Model model) {
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"TECNICO".equals(userRole)) {
            return "redirect:/";
        }

        Tecnico tecnico = (Tecnico) usuarioAutenticado;

        ArrayList<Incidencia> incidenciasPendientes = incidenciaService.getIncidenciasAsignadasSinResolver(tecnico);

        model.addAttribute("incidencias", incidenciasPendientes);

        return "tecnico-elegir-incidencia-marcar-resuelta";
    }

    @GetMapping("/tecnico/incidencia/marcar-resuelta")
    public String mostrarFormularioDescripcionResolucion(
            @RequestParam("incidenciaId") Integer incidenciaId,
            HttpSession session,
            Model model) {

        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"TECNICO".equals(userRole) ) {
            return "redirect:/";
        }

        Incidencia incidencia = incidenciaService.getIncidenciaById(incidenciaId);

        if (incidencia == null) {
            return "redirect:/tecnico/incidencias-asignadas";
        }

        model.addAttribute("incidencia", incidencia);

        return "tecnico-introducir-descripcion-resolucion";
    }

    @PostMapping("/tecnico/incidencia/marcar-resuelta/descripcion-resolucion")
    public String marcarComoResueltoYDescripcionResolucion(
            @RequestParam("incidenciaId") Integer incidenciaId,
            @RequestParam("descripcionResolucion") String descripcionResolucion,
            HttpSession session,
            Model model){
        Incidencia incidencia = incidenciaService.getIncidenciaById(incidenciaId);
        incidenciaService.setNuevoEstado(incidencia, 2, descripcionResolucion);
        return "tecnico-cambio-estado-exitoso";
    }

    //TECNICO --> CAMBIAR ESTADO
    @GetMapping("/tecnico/cambiar-estado/elegir-incidencia")
    public String elegirIncidenciaParaCambioEstado(HttpSession session, Model model) {
        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"TECNICO".equals(userRole)) {
            return "redirect:/";
        }

        Tecnico tecnico = (Tecnico) usuarioAutenticado;

        ArrayList<Incidencia> incidenciasPendientes = incidenciaService.getIncidenciasAsignadasSinResolver(tecnico);

        model.addAttribute("incidencias", incidenciasPendientes);

        return "tecnico-elegir-incidencia-cambiar-estado";
    }

    @GetMapping("/tecnico/incidencia/nuevo-estado")
    public String mostrarFormularioNuevoEstado(
            @RequestParam("incidenciaId") Integer incidenciaId,
            HttpSession session,
            Model model) {

        Object usuarioAutenticado = session.getAttribute("usuarioAutenticado");
        String userRole = (String) session.getAttribute("userRole");

        if (usuarioAutenticado == null || !"TECNICO".equals(userRole) ) {
            return "redirect:/";
        }

        Incidencia incidencia = incidenciaService.getIncidenciaById(incidenciaId);

        if (incidencia == null) {
            return "redirect:/tecnico/incidencias-asignadas";
        }

        model.addAttribute("incidencia", incidencia);

        return "tecnico-formulario-cambiar-estado";
    }

    @PostMapping("/tecnico/incidencia/cambiar-estado")
    public String marcarCambioYDescripcionResolucion(
            @RequestParam("incidenciaId") Integer incidenciaId,
            @RequestParam(value = "descripcionResolucion", required = false) String descripcionResolucion,
            @RequestParam("estado") Integer estado,
            HttpSession session,
            Model model){
        if (estado != 2) descripcionResolucion = null;
        Incidencia incidencia = incidenciaService.getIncidenciaById(incidenciaId);
        incidenciaService.setNuevoEstado(incidencia, estado, descripcionResolucion);
        return "tecnico-cambio-estado-exitoso";
    }
}
