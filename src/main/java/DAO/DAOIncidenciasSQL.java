package DAO;

import models.Cliente;
import models.Incidencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOIncidenciasSQL implements DAOIncidencia{
    @Override
    public ArrayList<Incidencia> readAll(DAOManager dao) {
        ArrayList<Incidencia> lista = new ArrayList();
        String sentencia = "select * from Incidencias";
        LocalDate fechaCreacion = null;
        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Incidencia incidencia = new Incidencia(rs.getInt("id"),
                            rs.getString("contenido"),
                            rs.getInt("estado"),
                            rs.getDate("fechaCreacion").toLocalDate(),
                            rs.getString("id_cliente"),
                            rs.getString("id_tecnico"),
                            rs.getString("descripcionResolucion"));
                    lista.add(incidencia);
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

    @Override
    public boolean updateTecnico(Incidencia incidencia, int id_tecnico, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "UPDATE `Incidencias` SET `id_tecnico` = '" + id_tecnico + "' "
                    + "WHERE `Incidencias`.`id` = " + incidencia.getId();
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean insert(Incidencia incidencia, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "INSERT INTO `Incidencias` (`id`, `contenido`, `estado`, `id_cliente`, " +
                    "`fechaCreacion`) VALUES (" +
                    "'" + incidencia.getId() + "', '" + incidencia.getContenido() + "', '"
                    + incidencia.getEstado() + "', '" + incidencia.getCliente().getId() +
                    "', '" + incidencia.getFechaCreacion() + "')";
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateEstadoResuelta(Incidencia incidencia, int estado, String descripcionResolucion, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "UPDATE `Incidencias` SET `estado` = '" + estado + "', "
                    + "`descripcionResolucion` = '" + descripcionResolucion + "', "
                    + "`id_tecnico` = " + "NULL" + " "
                    + "WHERE `Incidencias`.`id` = " + incidencia.getId();
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean updateEstado(Incidencia incidencia, int estado, DAOManager dao) {
        try{
            dao.open();
            String sentencia = "UPDATE `Incidencias` SET `estado` = '" + estado + "' "
                    + "WHERE `Incidencias`.`id` = " + incidencia.getId();
            Statement stmt = dao.getConn().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
