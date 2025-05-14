package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Cliente;

public class DAOClienteSQL implements DAOCliente {
    public DAOClienteSQL() {
    }

    public ArrayList<Cliente> readAll(DAOManager dao) {
        ArrayList<Cliente> lista = new ArrayList();
        String sentencia = "select * from Clientes";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Cliente cliente = new Cliente(rs.getInt("id"), rs.getString("correo"), rs.getString("clave"), rs.getString("nombre"));
                    lista.add(cliente);
                }

                dao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

    public boolean insert(Cliente cliente, DAOManager dao) {
        return false;
    }

    public boolean delete(Cliente cliente, DAOManager dao) {
        return false;
    }

    public boolean update(Cliente cliente, DAOManager dao) {
        return false;
    }
}
