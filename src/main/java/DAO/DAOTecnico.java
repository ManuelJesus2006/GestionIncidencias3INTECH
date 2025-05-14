package DAO;

import java.util.ArrayList;
import models.Cliente;
import models.Tecnico;

public interface DAOTecnico {
    ArrayList<Tecnico> readAll(DAOManager dao);

    boolean insert(Cliente cliente, DAOManager dao);

    boolean delete(Cliente cliente, DAOManager dao);

    boolean update(Cliente cliente, DAOManager dao);
}
