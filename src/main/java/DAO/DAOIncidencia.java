package DAO;

import models.Cliente;
import models.Incidencia;

import java.util.ArrayList;

public interface DAOIncidencia {
    public ArrayList<Incidencia> readAll(DAOManager dao);
    public boolean updateTecnico(Incidencia incidencia, int id_tecnico, DAOManager dao);

    public boolean insert(Incidencia incidencia, DAOManager dao);

    public boolean updateEstado(Incidencia incidencia, int estado, DAOManager dao);
    public boolean updateEstadoResuelta(Incidencia incidencia, int estado, String descripcionResolucion, DAOManager dao);
}
