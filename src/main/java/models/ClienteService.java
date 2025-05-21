package models;

import DAO.DAOClienteSQL;
import DAO.DAOIncidenciasSQL;
import DAO.DAOManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClienteService {
    private DAOClienteSQL daoCliente = new DAOClienteSQL();
    private DAOManager dao = DAOManager.getSingletonInstance();

    public void guardar(String correo, String clave, String nombre) {
        daoCliente.insert(new Cliente(correo, clave, nombre), dao);
    }

    public Cliente obtenerPorId(String usuarioId) {
        for (Cliente c : daoCliente.readAll(dao)){
            if (c.getId().equals(usuarioId)) return c;
        }
        return null;
    }

    public ArrayList<Cliente> getAllClientes() {
        return daoCliente.readAll(dao);
    }

    public boolean darDeBaja(String usuarioId) {
        return daoCliente.delete(obtenerPorId(usuarioId), dao);
    }

    public boolean modificar(String usuarioId, String nombre, String correo, String clave) {
        if (clave.isEmpty()) clave = obtenerPorId(usuarioId).getClave();
        return daoCliente.update(obtenerPorId(usuarioId), nombre, correo, clave, dao);
    }

    public boolean existeCliente(String usuarioId) {
        for (Cliente c : getAllClientes()){
            if (c.getId().equals(usuarioId)) return true;
        }
        return false;
    }
}
