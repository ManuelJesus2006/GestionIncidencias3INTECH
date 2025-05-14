package models;

import DAO.DAOIncidenciasSQL;
import DAO.DAOManager;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private DAOIncidenciasSQL daoIncidencias = new DAOIncidenciasSQL();
    private DAOManager dao = DAOManager.getSingletonInstance();

    public ClienteService() {
    }

    public void guardar(String nombre, String correo, String clave) {
    }
}
