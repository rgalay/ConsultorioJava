/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author lajot
 */
public class Cita {
    
    String    Dni;    
    String    Nombre; 
    LocalDate Fecha;  
    LocalTime Hora;  

    public Cita(String Dni, String Nombre, LocalDate Fecha, LocalTime Hora) {
        this.Dni = Dni;
        this.Nombre = Nombre;
        this.Fecha = Fecha;
        this.Hora = Hora;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String Dni) {
        this.Dni = Dni;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate Fecha) {
        this.Fecha = Fecha;
    }

    public LocalTime getHora() {
        return Hora;
    }

    public void setHora(LocalTime Hora) {
        this.Hora = Hora;
    }

   
    
    
}
