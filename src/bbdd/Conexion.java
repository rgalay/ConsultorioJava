/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lajot
 */
public class Conexion {

    public static Connection conn;

   
    
    public static void Conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/consultorio",
                    "root","");
        } catch (ClassNotFoundException | SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static void cerrar() {

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static boolean acceder(String usuario, String contrasenya) {

        try {
            String consulta
                    = "SELECT usuario, contrasenya "
                    + "FROM usuarios WHERE usuario=? "
                    + "AND contrasenya=?";

            PreparedStatement pst;
            ResultSet rs;              
            pst = conn.prepareStatement(consulta);      

            pst.setString(1, usuario);
            pst.setString(2, contrasenya);

            rs = pst.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return false;

    }

    public static void CompruebaDni(String dni, DefaultTableModel modelo) {

        try {
            Object[] misDatos = new Object[1];

            String consulta
                    = "SELECT dni FROM paciente WHERE dni = '" + dni + "'";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            while (rs.next()) {
                misDatos[0] = rs.getString("dni");
                modelo.addRow(misDatos);
            }

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrar();
        }
    }

    public static void RescatarDatosPaciente(String dni, DefaultTableModel modelo) {

        try {

            Object[] misDatos = new Object[8];

            String consulta
                    = "SELECT nombre, apellidos, fechaNacimiento, telefono, email, cp, sexo, consumoAlcohol "
                    + "FROM paciente WHERE dni = '" + dni + "'";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            while (rs.next()) {
                misDatos[0] = rs.getString("nombre");
                misDatos[1] = rs.getString("apellidos");
                misDatos[2] = rs.getDate("fechaNacimiento");
                misDatos[3] = rs.getInt("telefono");
                misDatos[4] = rs.getString("email");
                misDatos[5] = rs.getInt("cp");
                misDatos[6] = rs.getString("sexo");
                misDatos[7] = rs.getString("consumoAlcohol");

                modelo.addRow(misDatos);
            }

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrar();
        }
    }

    public static void VerHistorial(String dni, DefaultTableModel modelo) {

        try {

            Object[] misDatos = new Object[5];

            String consulta
                    = "SELECT fechaConsulta, diagnostico, tratamiento, observaciones, codigofacultativo "
                    + "FROM consultas "
                    + "WHERE dniPaciente = '" + dni + "' "
                    + "ORDER BY fechaConsulta DESC";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            while (rs.next()) {
                misDatos[0] = rs.getDate("fechaConsulta");
                misDatos[1] = rs.getString("diagnostico");
                misDatos[2] = rs.getString("tratamiento");
                misDatos[3] = rs.getString("observaciones");
                misDatos[4] = rs.getInt("codigofacultativo");

                modelo.addRow(misDatos);
            }

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrar();
        }
    }

    public static void VerHistorialEnfermeria(String dni, DefaultTableModel modelo) {

        try {

            Object[] misDatos = new Object[6];

            String consulta
                    = "SELECT fechaConsulta, tensionMax, tensionMin, glucosa, peso, codigoFacultativo "
                    + "FROM enfermeria "
                    + "WHERE dniPaciente = '" + dni + "' "
                    + "ORDER BY fechaConsulta DESC";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            while (rs.next()) {
                misDatos[0] = rs.getDate("fechaConsulta");
                misDatos[1] = rs.getDouble("tensionMax");
                misDatos[2] = rs.getDouble("tensionMin");
                misDatos[3] = rs.getDouble("glucosa");
                misDatos[4] = rs.getDouble("peso");
                misDatos[5] = rs.getInt("codigoFacultativo");

                modelo.addRow(misDatos);
            }

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            cerrar();
        }
    }

    public static String[] RescataDatosUserLogado(String usuario) {

        String[] datos = new String[4];

        try {

            String consulta = "SELECT nombre, apellidos, numero_colegiado, tipo "
                    + "FROM personal "
                    + "WHERE usuario = '" + usuario + "'";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            if (rs.next()) {
                datos[0] = rs.getString("nombre");
                datos[1] = rs.getString("apellidos");
                datos[2] = rs.getString("numero_colegiado");
                datos[3] = rs.getString("tipo");
            }

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return datos;
    }

    public static void CargarCitas(DefaultTableModel modelo) {

        try {

            Object[] misDatos = new Object[5];

            String consulta = "SELECT idCita, dniPaciente, nombre, dia, hora "
                    + "FROM citas "
                    + "ORDER BY dia, hora";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            while (rs.next()) {
                misDatos[0] = rs.getInt("idCita");
                misDatos[1] = rs.getString("dniPaciente");
                misDatos[2] = rs.getString("nombre");
                misDatos[3] = rs.getDate("dia");
                misDatos[4] = rs.getDouble("hora");

                modelo.addRow(misDatos);
            }

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public static void CargarCitasEnfermeria(DefaultTableModel modelo) {

        try {

            Object[] misDatos = new Object[5];

            String consulta = "SELECT id, dniPaciente, nombre, dia, hora "
                    + "FROM citasEnfermeria "
                    + "ORDER BY dia, hora";

            ResultSet rs = conn.createStatement().executeQuery(consulta);

            while (rs.next()) {
                misDatos[0] = rs.getInt("id");
                misDatos[1] = rs.getString("dniPaciente");
                misDatos[2] = rs.getString("nombre");
                misDatos[3] = rs.getDate("dia");
                misDatos[4] = rs.getInt("hora");

                modelo.addRow(misDatos);
            }

        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

}
