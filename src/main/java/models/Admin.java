package models;

public class Admin {
    private String correo;
    private String clave;
    private String nombre;
    private int id;

    public Admin(int id, String correo, String clave, String nombre) {
        this.correo = correo;
        this.clave = clave;
        this.nombre = nombre;
        this.id = id;
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
}
