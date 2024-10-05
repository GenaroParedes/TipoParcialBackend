package ar.edu.utnfrc.backend.repositories;

import ar.edu.utnfrc.backend.dbContext.DbContext;
import ar.edu.utnfrc.backend.entities.Inasistencia;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EstudianteRepository {
    private final EntityManager em = DbContext.getInstance().getEntityManager();

    public void saveAll(List<Inasistencia> inasistencias) {
        try{
            em.getTransaction().begin();
            inasistencias.forEach(em::persist);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
