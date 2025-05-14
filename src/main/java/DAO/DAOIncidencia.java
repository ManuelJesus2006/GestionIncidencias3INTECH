package DAO;

import models.Incidencia;

import java.util.ArrayList;

public interface DAOIncidencia {
    public ArrayList<Incidencia> readAll(DAOManager dao);
    public boolean update(Incidencia incidencia, int id_tecnico, DAOManager dao);
}
