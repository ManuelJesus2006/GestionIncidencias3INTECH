package DAO;

import models.Admin;

public interface DAOAdmin {
    public Admin readAdmin(DAOManager dao);
    public boolean insertAdmin(DAOManager dao, Admin admin);
}
