package com.prueba.pruebau2;

public class Evento {

    private String key;
    private String nombre;
    private double latitud;
    private double longitud;
    private String fecha;
    private String hora;

    public Evento() {
        // Constructor vacío necesario para Firebase
    }

    public Evento(String nombre, double latitud, double longitud, String fecha, String hora) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getKey() {
        return key;
    }

    public String getNombre() {
        return nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}
