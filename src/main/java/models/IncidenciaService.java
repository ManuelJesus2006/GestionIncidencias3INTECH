package models;

import DAO.DAOIncidenciasSQL;
import DAO.DAOManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IncidenciaService {
    private DAOIncidenciasSQL daoIncidencia = new DAOIncidenciasSQL();
    private DAOManager dao = DAOManager.getSingletonInstance();
    public Incidencia getIncidenciaById(Integer incidenciaId) {
        ArrayList<Incidencia> todasIncidencias = daoIncidencia.readAll(dao);
        for (Incidencia i : todasIncidencias){
            if (i.getId() == incidenciaId) return i;
        }
        return null;
    }

    public ArrayList<Incidencia> getIncidenciasPendientesAsignar() {
        ArrayList<Incidencia> todasIncidencias = daoIncidencia.readAll(dao);
        ArrayList<Incidencia> incidenciasSinAsignar = new ArrayList<>();
        for (Incidencia i : todasIncidencias){
            if (i.getTecnico() == null) incidenciasSinAsignar.add(i);
        }
        return incidenciasSinAsignar;
    }

    public boolean assignTecnicoToIncidencia(Integer incidenciaId, Integer tecnicoId) {
        return daoIncidencia.update(getIncidenciaById(incidenciaId), tecnicoId, dao);
    }
}
