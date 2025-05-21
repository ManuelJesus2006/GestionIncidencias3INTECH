package models;

import DAO.DAOManager;
import DAO.DAOTecnicoSQL;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class TecnicoService {
    private DAOTecnicoSQL daoTecnico = new DAOTecnicoSQL();
    private DAOManager dao = DAOManager.getSingletonInstance();

    public void guardar(String correo, String clave, String nombre) {
        daoTecnico.insert(new Tecnico(correo, clave, nombre), dao);
    }

    public ArrayList<Tecnico> getAllTecnicos() {
        return daoTecnico.readAll(dao);
    }

    public Tecnico obtenerPorId(String usuarioId) {
        for (Tecnico tec : daoTecnico.readAll(dao)){
            if (tec.getId().equals(usuarioId)) return tec;
        }
        return null;
    }

    public boolean darDeBaja(String usuarioId) {
        return daoTecnico.delete(obtenerPorId(usuarioId), dao);
    }

    public boolean modificar(String usuarioId, String nombre, String correo, String clave) {
        if (clave.isEmpty()) clave = obtenerPorId(usuarioId).getClave();
        return daoTecnico.update(obtenerPorId(usuarioId), nombre, correo, clave, dao);
    }

    public boolean existeTecnico(String usuarioId) {
        for (Tecnico tec : getAllTecnicos()){
            if (tec.getId().equals(usuarioId)) return true;
        }
        return false;
    }
}