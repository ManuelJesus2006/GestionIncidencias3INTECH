//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package models;

import DAO.DAOClienteSQL;
import DAO.DAOManager;
import DAO.DAOTecnicoSQL;

public class Cliente {
    private String correo;
    private String clave;
    private String nombre;
    private String id;
    private DAOManager dao = DAOManager.getSingletonInstance();
    private DAOClienteSQL daoCliente = new DAOClienteSQL();

    //Constructor normal
    public Cliente(String id, String correo, String clave, String nombre) {
        this.correo = correo;
        this.clave = clave;
        this.nombre = nombre;
        this.id = id;
    }

    public Cliente(String correo, String clave, String nombre){
        this.correo = correo;
        this.clave = clave;
        this.nombre = nombre;
        id = generaID();
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return this.nombre;
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
            tokenGenerado = "CLI" + (int)(Math.random() * 10000);
        }while (existeID(tokenGenerado));
        return tokenGenerado;
    }

    private boolean existeID(String idGenerada) {
        for (Cliente c : daoCliente.readAll(dao)){
            if (c.getId().equals(idGenerada)) return true;
        }
        return false;
    }
}

