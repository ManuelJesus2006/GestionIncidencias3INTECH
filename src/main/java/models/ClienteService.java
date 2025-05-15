package models;

import DAO.DAOClienteSQL;
import DAO.DAOIncidenciasSQL;
import DAO.DAOManager;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private DAOClienteSQL daoCliente = new DAOClienteSQL();
    private DAOManager dao = DAOManager.getSingletonInstance();

    public ClienteService() {
    }

    public void guardar(String correo, String clave, String nombre) {
        daoCliente.insert(new Cliente(correo, clave, nombre), dao);
    }
}
