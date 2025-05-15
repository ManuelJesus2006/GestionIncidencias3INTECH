package models;

import DAO.DAOManager;
import DAO.DAOTecnicoSQL;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class TecnicoService {
    private DAOTecnicoSQL daoTecnico = new DAOTecnicoSQL();
    private DAOManager dao = DAOManager.getSingletonInstance();

    public TecnicoService() {
    }

    public void guardar(String correo, String clave, String nombre) {
        daoTecnico.insert(new Tecnico(correo, clave, nombre), dao);
    }

    public ArrayList<Tecnico> getAllTecnicos() {
        return this.daoTecnico.readAll(this.dao);
    }
}