package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.Cliente;

public class DAOClienteSQL implements DAOCliente {
    public ArrayList<Cliente> readAll(DAOManager dao) {
        ArrayList<Cliente> lista = new ArrayList();
        String sentencia = "select * from Clientes";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Cliente cliente = new Cliente(rs.getString("id"), rs.getString("correo"), rs.getString("clave"), rs.getString("nombre"));
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
        try{
            dao.open();
            String sentencia = "INSERT INTO `Clientes` (`id`, `correo`, `clave`, `nombre`) VALUES (" +
                    "'" + cliente.getId() + "', '" + cliente.getCorreo() + "', '" + cliente.getClave() + "', '" + cliente.getNombre() + "')";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(Cliente cliente, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "DELETE FROM Clientes WHERE `Clientes`.`id` = '" + cliente.getId() + "'";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean update(Cliente cliente, String nombre, String correo, String clave, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "UPDATE `Clientes` SET `nombre` = '" + nombre + "', "
                    + "`correo` = '" + correo + "', "
                    + "`clave` = '" + clave + "' "
                    + "WHERE `Clientes`.`id` = '" + cliente.getId() + "'";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
