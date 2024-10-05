package ar.edu.utnfrc.backend.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Inasistencias")
public class Inasistencia {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int justificada;
    private double cantidad;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_estudiante", referencedColumnName="id")
    private Estudiante estudiante;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id_tipo", referencedColumnName="id")
    private Tipo tipo;

    public Inasistencia() {
    }

    public Inasistencia(int justificada, double cantidad, Estudiante estudiante, Tipo tipo) {
        this.justificada = justificada;
        this.cantidad = cantidad;
        this.estudiante = estudiante;
        this.tipo = tipo;
        estudiante.addInasistencia(this);
        tipo.addInasistencia(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJustificada() {
        return justificada;
    }

    public void setJustificada(int justificada) {
        this.justificada = justificada;
    }


    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inasistencia that = (Inasistencia) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Inasistencia{" +
                ", justificada=" + justificada +
                ", cantidad=" + cantidad +
                ", estudiante=" + estudiante.toString() +
                ", tipo=" + tipo.toString() +
                '}';
    }
}
