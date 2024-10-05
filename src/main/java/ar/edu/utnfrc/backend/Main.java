package ar.edu.utnfrc.backend;


import ar.edu.utnfrc.backend.entities.Estudiante;
import ar.edu.utnfrc.backend.entities.Inasistencia;
import ar.edu.utnfrc.backend.entities.Tipo;
import ar.edu.utnfrc.backend.repositories.EstudianteRepository;
import ar.edu.utnfrc.backend.services.ArchivoCSVService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        URL location = Main.class.getResource("/inasistencias.csv");
        List<Inasistencia> inasistencias = new ArrayList<>();
        ArchivoCSVService service = new ArchivoCSVService();
        EstudianteRepository estudianteRepository = new EstudianteRepository();

        try{
            inasistencias = service.cargarDatosDesdeCSV(location);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Determinar e informar la suma de todas las cantidades de días  |  Req 2
        double cantTotalDias = inasistencias.stream().mapToDouble(Inasistencia::getCantidad).sum();
        System.out.println("La suma de todas las cantidades de dias es: "+ cantTotalDias);

        //Requerimiento 3
        List<Tipo> tipoProcesados = new ArrayList<>();
        File file = new File("Reporte_Inasistencias.txt");
        try(PrintWriter printWriter = new PrintWriter(file)){
            printWriter.println("Nombre tipo | Cantidad inasistencias por tipo");
            for ( Inasistencia in : inasistencias) {
                if(!tipoProcesados.contains(in.getTipo())){
                    printWriter.printf("%s, %d \n",
                            in.getTipo().getNombre(),
                            in.getTipo().getInasistencias().size());
                    tipoProcesados.add(in.getTipo());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Requerimiento 4
        System.out.println("----------------Estudiantes con menos de cinco inasistencias justificadas---------------");
        List<Inasistencia> inasistenciasJustificadas = inasistencias.stream().filter(in -> in.getJustificada() == 1).toList();
        //inasistenciasJustificadas.forEach(System.out::println);
        List<Estudiante> estudiantes = inasistenciasJustificadas.stream()
                                .map(Inasistencia::getEstudiante)
                                .distinct()
                                .toList();
        //estudiantes.forEach(System.out::println);
        //estudiantes.stream().map(e -> e.getInasistencias().size()).forEach(System.out::println);
        List<Estudiante> estMenosCincoInJustificadas = estudiantes.stream()
                .filter(e -> e.getCantidadInasistencias() < 5)
                .toList();

        List<Estudiante> estOrdenados = estMenosCincoInJustificadas.stream()
                .sorted(Comparator.comparing(Estudiante::getNombre)).toList();

        estOrdenados.forEach(System.out::println);

        //Requerimiento 5
        estudianteRepository.saveAll(inasistencias);


    }
}