package DAO;

import java.util.ArrayList;
import models.Cliente;
import models.Tecnico;

public interface DAOTecnico {
    ArrayList<Tecnico> readAll(DAOManager dao);

    boolean insert(Tecnico tecnico, DAOManager dao);

    boolean delete(Tecnico tecnico, DAOManager dao);

    boolean update(Tecnico tecnico, String nombre, String correo, String clave, DAOManager dao);
}
