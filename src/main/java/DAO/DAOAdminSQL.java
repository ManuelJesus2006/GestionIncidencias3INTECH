package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Admin;

public class DAOAdminSQL implements DAOAdmin {
    public DAOAdminSQL() {
    }

    public Admin readAdmin(DAOManager dao) {
        Admin admin = null;
        String sentencia = "select * from Admin";

        try {
            dao.open();
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    admin = new Admin(rs.getString("id"),
                            rs.getString("correo"),
                            rs.getString("clave"),
                            rs.getString("nombre"));
                }

                dao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return admin;
    }
}