/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author lajot
 */
public class Paciente {
    
    public String  Dni;               
    public String  Nombre;            
    public String  Apellidos;         
    public String  Email;             
    public int     Cp;                
    public char    Sexo;              
    public LocalDate FechaNacimiento; 
    public long    Telefono;          
    public boolean Alcohol;           
    public boolean Tabaco;            
    public String  Familiares;        
    public String  Personal;

    public Paciente(String Dni, String Nombre, String Apellidos, String Email, int Cp, char Sexo, LocalDate FechaNacimiento, long Telefono, boolean Alcohol, boolean Tabaco, String Familiares, String Personal) {
        this.Dni = Dni;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Email = Email;
        this.Cp = Cp;
        this.Sexo = Sexo;
        this.FechaNacimiento = FechaNacimiento;
        this.Telefono = Telefono;
        this.Alcohol = Alcohol;
        this.Tabaco = Tabaco;
        this.Familiares = Familiares;
        this.Personal = Personal;
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

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getCp() {
        return Cp;
    }

    public void setCp(int Cp) {
        this.Cp = Cp;
    }

    public char getSexo() {
        return Sexo;
    }

    public void setSexo(char Sexo) {
        this.Sexo = Sexo;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public long getTelefono() {
        return Telefono;
    }

    public void setTelefono(long Telefono) {
        this.Telefono = Telefono;
    }

    public boolean isAlcohol() {
        return Alcohol;
    }

    public void setAlcohol(boolean Alcohol) {
        this.Alcohol = Alcohol;
    }

    public boolean isTabaco() {
        return Tabaco;
    }

    public void setTabaco(boolean Tabaco) {
        this.Tabaco = Tabaco;
    }

    public String getFamiliares() {
        return Familiares;
    }

    public void setFamiliares(String Familiares) {
        this.Familiares = Familiares;
    }

    public String getPersonal() {
        return Personal;
    }

    public void setPersonal(String Personal) {
        this.Personal = Personal;
    }

    
        
}
