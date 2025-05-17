package models;

import DAO.DAOIncidenciasSQL;
import DAO.DAOManager;
import jakarta.persistence.criteria.CriteriaBuilder;
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
        ArrayList<Incidencia> todasIncidencias = getTodasIncidencias();
        ArrayList<Incidencia> incidenciasSinAsignar = new ArrayList<>();
        for (Incidencia i : todasIncidencias){
            if (i.getTecnico() == null && i.getEstado() != 2) incidenciasSinAsignar.add(i);
        }
        return incidenciasSinAsignar;
    }

    public boolean assignTecnicoToIncidencia(Integer incidenciaId, Integer tecnicoId) {
        return daoIncidencia.updateTecnico(getIncidenciaById(incidenciaId), tecnicoId, dao);
    }

    public ArrayList<Incidencia> getTodasIncidencias() {
        return daoIncidencia.readAll(dao);
    }

    public void nuevaIncidencia(String contenido, Cliente cliente) {
        daoIncidencia.insert(new Incidencia(contenido, cliente), dao);
    }

    public ArrayList<Incidencia> getIncidenciasCliente(Cliente cliente) {
        ArrayList<Incidencia> todasIncidencias = getTodasIncidencias();
        ArrayList<Incidencia> incidenciasCliente = new ArrayList<>();
        for (Incidencia i : todasIncidencias){
            if (i.getCliente().getId().equals(cliente.getId())) incidenciasCliente.add(i);
        }
        return incidenciasCliente;
    }

    public ArrayList<Incidencia> getIncidenciasAsignadas(Tecnico tecnico) {
        ArrayList<Incidencia> todasIncidencias = getTodasIncidencias();
        ArrayList<Incidencia> incidenciasAsignadasTecnico = new ArrayList<>();
        for (Incidencia i : todasIncidencias){
            if (i.getTecnico() != null){
                if (i.getTecnico().getId().equals(tecnico.getId())) incidenciasAsignadasTecnico.add(i);
            }
        }
        return incidenciasAsignadasTecnico;
    }

    public void setNuevoEstado(Incidencia incidencia, int estado, String descripcionResolucion) {
        if (descripcionResolucion != null) daoIncidencia.updateEstadoResuelta(incidencia, estado, descripcionResolucion, dao);
        else daoIncidencia.updateEstado(incidencia, estado, dao);
    }

    public Incidencia getIncidenciaByIdCliente(int idIncidencia, Cliente cliente) {
        ArrayList<Incidencia> todasIncidencias = daoIncidencia.readAll(dao);
        for (Incidencia i : todasIncidencias){
            if (i.getId() == idIncidencia && i.getCliente().getId().equals(cliente.getId())) return i;
        }
        return null;
    }
}
