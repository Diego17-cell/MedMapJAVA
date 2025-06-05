package org.example.modelo;

public class LugarTuristico {
    private String nombre;
    private String ubicacion;
    private double calificacion;

    public LugarTuristico(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.calificacion = 0.0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public void calificar(double puntuacion) {
        setCalificacion(puntuacion);
        System.out.println(nombre + " calificado con " + puntuacion + " estrellas");
    }
}
