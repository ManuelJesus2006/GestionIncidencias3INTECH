package models;

import DAO.DAOClienteSQL;
import DAO.DAOIncidenciasSQL;
import DAO.DAOManager;
import DAO.DAOTecnicoSQL;

public class Incidencia {
    private int id;
    private String contenido;
    private int estado;
    private Cliente cliente;
    private Tecnico tecnico;
    private DAOManager dao = DAOManager.getSingletonInstance();
    private DAOTecnicoSQL daoTecnico = new DAOTecnicoSQL();
    private DAOClienteSQL daoCliente = new DAOClienteSQL();
    private DAOIncidenciasSQL daoIncidencia = new DAOIncidenciasSQL();

    public Incidencia(int id, String contenido, int estado, int id_cliente, int id_tecnico) {
        this.id = id;
        this.contenido = contenido;
        this.estado = estado;
        cliente = buscaClienteByID(id_cliente);
        tecnico = buscaTecnicoByID(id_tecnico);
    }

    public Incidencia(String contenido, Cliente cliente){
        id = generaID();
        estado = 0;
        tecnico = null;
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

    private Tecnico buscaTecnicoByID(int idTecnico) {
        for (Tecnico tec : daoTecnico.readAll(dao)){
            if (tec.getId() == idTecnico) return tec;
        }
        return null;
    }

    private Cliente buscaClienteByID(int idCliente) {
        for (Cliente c : daoCliente.readAll(dao)){
            if (c.getId() == idCliente) return c;
        }
        return null;
    }
}
