package ar.edu.utnfrc.backend.services;

import ar.edu.utnfrc.backend.entities.Estudiante;
import ar.edu.utnfrc.backend.entities.Inasistencia;
import ar.edu.utnfrc.backend.entities.Tipo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArchivoCSVService {

    public List<Inasistencia> cargarDatosDesdeCSV(URL location) throws URISyntaxException, IOException {
        List<Estudiante> estudiantes = new ArrayList<>();
        List<Inasistencia> inasistencias = new ArrayList<>();
        List<Tipo> tipos = new ArrayList<>();

        Files.lines(Paths.get(location.toURI()))
                .skip(1)
                .forEach(line -> {
                        Inasistencia inasistencia = crearInasistencia(line, estudiantes, tipos);
                        inasistencias.add(inasistencia);
                });
        return inasistencias;
    }

    private Inasistencia crearInasistencia(String line, List<Estudiante> estudiantes, List<Tipo> tipos) {
        String[] valores = line.split(",");
        String nombreEstudiante = valores[0];
        String nombreTipo = valores[1];
        int justificacion = Integer.parseInt(valores[2]);
        double cantidad = Double.parseDouble(valores[3]);

        //Crear Estudiante
        Estudiante estudiante = null;
        for (Estudiante e : estudiantes) {
            if(e.getNombre().equals(nombreEstudiante)) {
                estudiante = e;
                break;
            }
        }
        if(estudiante == null){
            estudiante = new Estudiante(nombreEstudiante);
            estudiantes.add(estudiante);
        }

        //Crear Tipo
        Tipo tipo = null;
        for ( Tipo t : tipos ) {
            if(t.getNombre().equals(nombreTipo)) {
                tipo = t;
                break;
            }
        }
        if(tipo == null){
            tipo = new Tipo(nombreTipo);
            tipos.add(tipo);
        }

        Inasistencia inasistencia = new Inasistencia(justificacion, cantidad, estudiante, tipo);
        return inasistencia;
    }
}
