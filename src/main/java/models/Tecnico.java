package models;

import DAO.DAOManager;
import DAO.DAOTecnicoSQL;

public class Tecnico {
    private String correo;
    private String clave;
    private String nombre;
    private int id;
    private DAOManager dao = DAOManager.getSingletonInstance();
    private DAOTecnicoSQL daoTecnico = new DAOTecnicoSQL();

    public Tecnico(int id, String correo, String clave, String nombre) {
        this.correo = correo;
        this.clave = clave;
        this.nombre = nombre;
        this.id = id;
    }

    public Tecnico(String correo, String clave, String nombre){
        this.correo = correo;
        this.clave = clave;
        this.nombre = nombre;
        id = generaID();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Metodos
    private int generaID() {
        int aleatorio;
        do {
            aleatorio = (int)(Math.random() * 10000);
        }while (existeID(aleatorio));
        return aleatorio;
    }

    private boolean existeID(int aleatorio) {
        for (Tecnico tec : daoTecnico.readAll(dao)){
            if (tec.getId() == aleatorio) return true;
        }
        return false;
    }
}
