package com.renzo.mifirebaseapp;

public class Estudiante {

    private String id;
    private String nombre;
    private String carrera;
    private String curso;

    public Estudiante() {
    }

    public Estudiante(String id,
                      String nombre,
                      String carrera,
                      String curso) {

        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.curso = curso;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getCurso() {
        return curso;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}