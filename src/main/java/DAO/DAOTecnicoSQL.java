package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import models.Cliente;
import models.Tecnico;

public class DAOTecnicoSQL implements DAOTecnico {
    public DAOTecnicoSQL() {
    }

    public ArrayList<Tecnico> readAll(DAOManager dao) {
        ArrayList<Tecnico> lista = new ArrayList();
        String sentencia = "select * from Tecnico";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Tecnico tecnico = new Tecnico(rs.getString("id"),
                            rs.getString("correo"),
                            rs.getString("clave"),
                            rs.getString("nombre"));
                    lista.add(tecnico);
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

    public boolean insert(Tecnico tecnico, DAOManager dao) {
        try{
           dao.open();
           String sentencia = "INSERT INTO `Tecnico` (`id`, `correo`, `clave`, `nombre`) VALUES (" +
                   "'" + tecnico.getId() + "', '" + tecnico.getCorreo() + "', '" + tecnico.getClave() + "', '" + tecnico.getNombre() + "')";
           Statement stmt = dao.getConn().createStatement();
           stmt.executeUpdate(sentencia);
           dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(Tecnico tecnico, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "DELETE FROM Tecnico WHERE `Tecnico`.`id` = '" + tecnico.getId() + "'";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean update(Tecnico tecnico, String nombre, String correo, String clave, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "UPDATE `Tecnico` SET `nombre` = '" + nombre + "', "
                    + "`correo` = '" + correo + "', "
                    + "`clave` = '" + clave + "' "
                    + "WHERE `Tecnico`.`id` = '" + tecnico.getId() + "'";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
