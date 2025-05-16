package models;

import DAO.DAOManager;
import DAO.DAOTecnicoSQL;

public class Tecnico {
    private String correo;
    private String clave;
    private String nombre;
    private String id;
    private DAOManager dao = DAOManager.getSingletonInstance();
    private DAOTecnicoSQL daoTecnico = new DAOTecnicoSQL();

    public Tecnico(String id, String correo, String clave, String nombre) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Metodos
    private String generaID() {
        String tokenGenerado;
        do {
            tokenGenerado = "TEC" + (int)(Math.random() * 10000);
        }while (existeID(tokenGenerado));
        return tokenGenerado;
    }

    private boolean existeID(String idGenerada) {
        for (Tecnico tec : daoTecnico.readAll(dao)){
            if (tec.getId().equals(idGenerada)) return true;
        }
        return false;
    }
}
