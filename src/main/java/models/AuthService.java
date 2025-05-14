package models;

import DAO.DAOAdminSQL;
import DAO.DAOClienteSQL;
import DAO.DAOManager;
import DAO.DAOTecnicoSQL;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private DAOClienteSQL daoCliente = new DAOClienteSQL();
    private DAOTecnicoSQL daoTecnico = new DAOTecnicoSQL();
    private DAOAdminSQL daoAdmin = new DAOAdminSQL();
    private DAOManager dao = DAOManager.getSingletonInstance();

    public AuthService() {
    }

    public Object autenticar(String correoIntroducido, String claveIntroducida) {
        for(Cliente c : this.daoCliente.readAll(this.dao)) {
            if (c.getCorreo().equals(correoIntroducido) && c.getClave().equals(claveIntroducida)) {
                return c;
            }
        }

        for(Tecnico tec : this.daoTecnico.readAll(this.dao)) {
            if (tec.getCorreo().equals(correoIntroducido) && tec.getClave().equals(claveIntroducida)) {
                return tec;
            }
        }

        Admin admin = this.daoAdmin.readAdmin(this.dao);
        if (admin != null && admin.getCorreo().equals(correoIntroducido) && admin.getClave().equals(claveIntroducida)) {
            return admin;
        } else {
            return null;
        }
    }
}
