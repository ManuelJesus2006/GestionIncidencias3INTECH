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
    private int id;
    private DAOManager dao = DAOManager.getSingletonInstance();
    private DAOClienteSQL daoCliente = new DAOClienteSQL();

    //Constructor normal
    public Cliente(int id, String correo, String clave, String nombre) {
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

    public int getId() {
        return this.id;
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
        for (Cliente c : daoCliente.readAll(dao)){
            if (c.getId() == aleatorio) return true;
        }
        return false;
    }
}

