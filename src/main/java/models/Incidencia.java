package models;

import DAO.DAOClienteSQL;
import DAO.DAOIncidenciasSQL;
import DAO.DAOManager;
import DAO.DAOTecnicoSQL;
import Utils.Utils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class Incidencia {
    private int id;
    private String contenido;
    private int estado;
    private String descripcionResolucion;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaCreacion;
    private Cliente cliente;
    private Tecnico tecnico;
    private DAOManager dao = DAOManager.getSingletonInstance();
    private DAOTecnicoSQL daoTecnico = new DAOTecnicoSQL();
    private DAOClienteSQL daoCliente = new DAOClienteSQL();
    private DAOIncidenciasSQL daoIncidencia = new DAOIncidenciasSQL();

    public Incidencia(int id, String contenido, int estado, LocalDate fechaCreacion, String id_cliente, String id_tecnico, String descripcionResolucion) {
        this.id = id;
        this.contenido = contenido;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.descripcionResolucion = descripcionResolucion;
        cliente = buscaClienteByID(id_cliente);
        tecnico = buscaTecnicoByID(id_tecnico);
    }

    public Incidencia(String contenido, Cliente cliente){
        id = generaID();
        estado = 0;
        tecnico = null;
        descripcionResolucion = null;
        fechaCreacion = LocalDate.now();
        this.contenido = contenido;
        this.cliente = cliente;
    }

    //Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcionResolucion() {
        return descripcionResolucion;
    }

    public void setDescripcionResolucion(String descripcionResolucion) {
        this.descripcionResolucion = descripcionResolucion;
    }

    //Constructor
    private int generaID() {
        int aleatorio;
        do {
            aleatorio = (int)(Math.random() * 10000);
        }while (existeID(aleatorio));
        return aleatorio;
    }

    private boolean existeID(int aleatorio) {
        for (Incidencia i : daoIncidencia.readAll(dao)){
            if (i.getId() == aleatorio) return true;
        }
        return false;
    }

    private Tecnico buscaTecnicoByID(String idTecnico) {
        for (Tecnico tec : daoTecnico.readAll(dao)){
            if (tec.getId().equals(idTecnico)) return tec;
        }
        return null;
    }

    private Cliente buscaClienteByID(String idCliente) {
        for (Cliente c : daoCliente.readAll(dao)){
            if (c.getId().equals(idCliente)) return c;
        }
        return null;
    }
}
