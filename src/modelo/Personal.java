/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author lajot
 */
public class Personal {
    
        public String Colegiado;                   
        public String Nombre;                
        public String Apellidos;             
        public int Telefono;
        public String Usuario;
        public String Contraseña;
        public String Tipo;

    public Personal(String Colegiado, String Nombre, String Apellidos, int Telefono, String Usuario, String Contraseña, String Tipo) {
        this.Colegiado = Colegiado;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Telefono = Telefono;
        this.Usuario = Usuario;
        this.Contraseña = Contraseña;
        this.Tipo = Tipo;
    }

    public String getColegiado() {
        return Colegiado;
    }

    public void setColegiado(String Colegiado) {
        this.Colegiado = Colegiado;
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

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    
        
}
