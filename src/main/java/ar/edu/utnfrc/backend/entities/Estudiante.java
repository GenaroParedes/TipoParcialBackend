package ar.edu.utnfrc.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @OneToMany(mappedBy="estudiante")
    private List<Inasistencia> inasistencias;

    public Estudiante() {
    }

    public Estudiante(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Inasistencia> getInasistencias() {
        return inasistencias;
    }

    public double getCantidadInasistencias(){
       double cant = this.inasistencias.stream().mapToDouble(Inasistencia::getCantidad).sum();
       System.out.println(cant);
       return cant;
    }

    public void setInasistencias(List<Inasistencia> inasistencias) {
        this.inasistencias = inasistencias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estudiante that = (Estudiante) o;
        return id == that.id && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                '}';
    }

    public void addInasistencia(Inasistencia inasistencia) {
        if( this.inasistencias == null){
            this.inasistencias = new ArrayList<>();
        }
        this.inasistencias.add(inasistencia);
    }
}
