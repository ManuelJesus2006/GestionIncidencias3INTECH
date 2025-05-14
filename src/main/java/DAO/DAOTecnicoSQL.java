package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    Tecnico tecnico = new Tecnico(rs.getInt("id"), rs.getString("correo"), rs.getString("clave"), rs.getString("nombre"));
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

    }

    public boolean delete(Tecnico tecnico, DAOManager dao) {
        return false;
    }

    public boolean update(Tecnico tecnico, DAOManager dao) {
        return false;
    }
}
