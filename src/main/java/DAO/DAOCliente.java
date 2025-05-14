package DAO;

import java.util.ArrayList;
import models.Cliente;

public interface DAOCliente {
    ArrayList<Cliente> readAll(DAOManager dao);

    boolean insert(Cliente cliente, DAOManager dao);

    boolean delete(Cliente cliente, DAOManager dao);

    boolean update(Cliente cliente, DAOManager dao);
}
