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
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import utilidades.Encriptado;

/**
 *
 * @author lajot
 */
    public class Conexion {
    // ── Array estático accesible desde cualquier clase ──────────────
    // [0] = nombre completo, [1] = numero_colegiado, [2] = tipo

    public static String[] datosPersonal;

    // ── Parámetros de conexión ──────────────────────────────────────
    private static final String URL
            = "jdbc:mysql://127.0.0.1:3307/consultorio"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=Europe/Madrid";
    private static final String USER = "root";
    private static final String PASS = "";

    // ── CONECTAR ────────────────────────────────────────────────────
    public static Connection Conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    // ── ACCEDER (Login) ─────────────────────────────────────────────
    // Encripta la contraseña antes de compararla con la BD
    public static boolean acceder(String user, String pass) {
        String passEnc = Encriptado.Encriptar(pass);
        String SQL = "SELECT * FROM personal WHERE usuario=? AND contrasenya=?";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, user);
            ps.setString(2, passEnc);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Error acceder: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── RECUPERA DATOS USER LOGADO ──────────────────────────────────
    // Devuelve String[]: [0]=nombre+apellidos, [1]=colegiado, [2]=tipo
    public static String[] recuperaDatosUserLogado(String user) {
        String SQL = "SELECT nombre, apellidos, numero_colegiado, tipo "
                + "FROM personal WHERE usuario=?";
        Connection c = Conectar();
        if (c == null) {
            return null;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("nombre") + " " + rs.getString("apellidos"),
                    String.valueOf(rs.getInt("numero_colegiado")),
                    rs.getString("tipo")
                };
            }
        } catch (SQLException e) {
            System.out.println("Error recuperaDatos: " + e.getMessage());
        } finally {
            cerrar(c);
        }
        return null;
    }

    // ── CITAS MÉDICAS (hoy) ─────────────────────────────────────────
    public static void recuperaCitasMedicas(DefaultTableModel modelo) {
        String SQL = "SELECT nombre, dia, hora FROM citas WHERE dia = CURDATE()";
        Connection c = Conectar();
        if (c == null) {
            return;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    Encriptado.Desencriptar(rs.getString("nombre")),
                    rs.getString("dia"),
                    rs.getDouble("hora")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error citas médicas: " + e.getMessage());
        } finally {
            cerrar(c);
        }
    }

    // ── CITAS ENFERMERÍA (hoy) ──────────────────────────────────────
    public static void recuperaCitasEnfermeria(DefaultTableModel modelo) {
        String SQL = "SELECT nombre, dia, hora FROM citasEnfermeria WHERE dia = CURDATE()";
        Connection c = Conectar();
        if (c == null) {
            return;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    Encriptado.Desencriptar(rs.getString("nombre")),
                    rs.getString("dia"),
                    rs.getInt("hora")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error citas enfermería: " + e.getMessage());
        } finally {
            cerrar(c);
        }
    }

    // ── REGISTRAR CITA MÉDICA ───────────────────────────────────────
    public static boolean registrarCitaMedica(Cita cita) {
        String SQL = "INSERT INTO citas (dniPaciente, nombre, dia, hora) VALUES (?,?,?,?)";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(cita.getDniPaciente()));
            ps.setString(2, Encriptado.Encriptar(cita.getNombre()));
            ps.setDate(3, new java.sql.Date(cita.getDia().getTime()));
            ps.setDouble(4, cita.getHora());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registrar cita médica: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── REGISTRAR CITA ENFERMERÍA ───────────────────────────────────
    public static boolean registrarCitaEnfermeria(Cita cita) {
        String SQL = "INSERT INTO citasEnfermeria (dniPaciente, nombre, dia, hora) VALUES (?,?,?,?)";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(cita.getDniPaciente()));
            ps.setString(2, Encriptado.Encriptar(cita.getNombre()));
            ps.setDate(3, new java.sql.Date(cita.getDia().getTime()));
            ps.setInt(4, (int) cita.getHora());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registrar cita enfermería: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── COMPROBAR DNI ───────────────────────────────────────────────
    public static boolean compruebaDni(String dni) {
        String SQL = "SELECT dni FROM paciente WHERE dni=?";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(dni));
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Error compruebaDni: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── RECUPERAR DATOS PACIENTE ────────────────────────────────────
    public static Paciente recuperaDatosPaciente(String dni) {
        String SQL = "SELECT * FROM paciente WHERE dni=?";
        Connection c = Conectar();
        if (c == null) {
            return null;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(dni));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Paciente(
                        Encriptado.Desencriptar(rs.getString("dni")),
                        Encriptado.Desencriptar(rs.getString("nombre")),
                        Encriptado.Desencriptar(rs.getString("apellidos")),
                        rs.getDate("fechaNacimiento"),
                        rs.getInt("telefono"),
                        rs.getString("email"),
                        rs.getInt("cp"),
                        rs.getString("sexo"),
                        rs.getString("tabaquismo"),
                        rs.getString("consumoalcohol"),
                        rs.getString("antecedentesSalud"),
                        rs.getString("datosSaludGeneral"),
                        rs.getDate("fechaRegistro")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error recuperaDatosPaciente: " + e.getMessage());
        } finally {
            cerrar(c);
        }
        return null;
    }

    // ── REGISTRAR PACIENTE ──────────────────────────────────────────
    public static boolean registrarPaciente(Paciente p) {
        String SQL = "INSERT INTO paciente (dni,nombre,apellidos,fechaNacimiento,"
                + "telefono,email,cp,sexo,tabaquismo,consumoalcohol,"
                + "antecedentesSalud,datosSaludGeneral,fechaRegistro) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(p.getDni()));
            ps.setString(2, Encriptado.Encriptar(p.getNombre()));
            ps.setString(3, Encriptado.Encriptar(p.getApellidos()));
            ps.setDate(4, new java.sql.Date(p.getFechaNacimiento().getTime()));
            ps.setInt(5, p.getTelefono());
            ps.setString(6, p.getEmail());
            ps.setInt(7, p.getCp());
            ps.setString(8, p.getSexo());
            ps.setString(9, p.getTabaquismo());
            ps.setString(10, p.getConsumoalcohol());
            ps.setString(11, p.getAntecedentesSalud());
            ps.setString(12, p.getDatosSaludGeneral());
            ps.setDate(13, new java.sql.Date(new java.util.Date().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registrarPaciente: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── CARGA TABLA PACIENTES ───────────────────────────────────────
    public static void cargaTablaPacientes(DefaultTableModel modelo) {
        String SQL = "SELECT dni, nombre, apellidos, telefono, cp FROM paciente";
        Connection c = Conectar();
        if (c == null) {
            return;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    Encriptado.Desencriptar(rs.getString("dni")),
                    Encriptado.Desencriptar(rs.getString("nombre")),
                    Encriptado.Desencriptar(rs.getString("apellidos")),
                    rs.getInt("telefono"),
                    rs.getInt("cp")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error cargaTablaPacientes: " + e.getMessage());
        } finally {
            cerrar(c);
        }
    }

    // ── ACTUALIZAR PACIENTE ─────────────────────────────────────────
    // datos: [0]=dni, [1]=nombre, [2]=apellidos, [3]=telefono, [4]=cp
    public static boolean actualizarPaciente(ArrayList<String> datos) {
        String SQL = "UPDATE paciente SET nombre=?, apellidos=?, telefono=?, cp=? WHERE dni=?";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(datos.get(1)));
            ps.setString(2, Encriptado.Encriptar(datos.get(2)));
            ps.setInt(3, Integer.parseInt(datos.get(3)));
            ps.setInt(4, Integer.parseInt(datos.get(4)));
            ps.setString(5, Encriptado.Encriptar(datos.get(0)));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizarPaciente: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── HISTORIAL CONSULTAS MÉDICAS ─────────────────────────────────
    public static void cargaTablaConsultasMedicas(DefaultTableModel modelo, String dni) {
        String SQL = "SELECT fechaConsulta, diagnostico, tratamiento, observaciones "
                + "FROM consultas WHERE dniPaciente=?";
        Connection c = Conectar();
        if (c == null) {
            return;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(dni));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getDate("fechaConsulta"),
                    rs.getString("diagnostico"),
                    rs.getString("tratamiento"),
                    rs.getString("observaciones")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error historial médico: " + e.getMessage());
        } finally {
            cerrar(c);
        }
    }

    // ── HISTORIAL CONSULTAS ENFERMERÍA ──────────────────────────────
    public static void cargaTablaConsultasEnfermeria(DefaultTableModel modelo, String dni) {
        String SQL = "SELECT fechaConsulta, tensionMax, tensionMin, glucosa, peso "
                + "FROM enfermeria WHERE dniPaciente=?";
        Connection c = Conectar();
        if (c == null) {
            return;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(dni));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getDate("fechaConsulta"),
                    rs.getDouble("tensionMax"),
                    rs.getDouble("tensionMin"),
                    rs.getInt("glucosa"),
                    rs.getDouble("peso")
                });
            }
        } catch (SQLException e) {
            System.out.println("Error historial enfermería: " + e.getMessage());
        } finally {
            cerrar(c);
        }
    }

    // ── REGISTRAR CONSULTA MÉDICA ───────────────────────────────────
    public static boolean registrarConsultaMedica(Consulta consulta) {
        String SQL = "INSERT INTO consultas (dniPaciente, fechaConsulta, diagnostico, "
                + "tratamiento, observaciones, codigoFacultativo) VALUES (?,?,?,?,?,?)";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(consulta.getDniPaciente()));
            ps.setDate(2, new java.sql.Date(consulta.getFechaConsulta().getTime()));
            ps.setString(3, consulta.getDiagnostico());
            ps.setString(4, consulta.getTratamiento());
            ps.setString(5, consulta.getObservaciones());
            ps.setInt(6, consulta.getCodigoFacultativo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registrarConsulta: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── REGISTRAR CONSULTA ENFERMERÍA ───────────────────────────────
    public static boolean registrarConsultaEnfermeria(ConsultaEnfermeria ce) {
        String SQL = "INSERT INTO enfermeria (dniPaciente, fechaConsulta, tensionMax, "
                + "tensionMin, glucosa, peso, codigoFacultativo) VALUES (?,?,?,?,?,?,?)";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, Encriptado.Encriptar(ce.getDniPaciente()));
            ps.setDate(2, new java.sql.Date(ce.getFechaConsulta().getTime()));
            ps.setDouble(3, ce.getMaxima());
            ps.setDouble(4, ce.getMinima());
            ps.setInt(5, ce.getGlucosa());
            ps.setDouble(6, ce.getPeso());
            ps.setInt(7, ce.getCodigoFacultativo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registrarEnfermeria: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── COMPROBAR USUARIO ───────────────────────────────────────────
    public static boolean compruebaUser(String user) {
        String SQL = "SELECT usuario FROM personal WHERE usuario=?";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setString(1, user);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Error compruebaUser: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── COMPROBAR NÚMERO COLEGIADO ──────────────────────────────────
    public static boolean compruebaNumColegiado(long numero) {
        String SQL = "SELECT numero_colegiado FROM personal WHERE numero_colegiado=?";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setLong(1, numero);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Error compruebaColegiado: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── REGISTRAR PERSONAL ──────────────────────────────────────────
    public static boolean registrarPersonal(Personal p) {
        String SQL = "INSERT INTO personal (numero_colegiado, nombre, apellidos, "
                + "telefono, usuario, contrasenya, tipo) VALUES (?,?,?,?,?,?,?)";
        Connection c = Conectar();
        if (c == null) {
            return false;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ps.setInt(1, p.getNumeroColegiado());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellidos());
            ps.setInt(4, p.getTelefono());
            ps.setString(5, p.getUsuario());
            ps.setString(6, Encriptado.Encriptar(p.getContrasenya()));
            ps.setString(7, p.getTipo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error registrarPersonal: " + e.getMessage());
            return false;
        } finally {
            cerrar(c);
        }
    }

    // ── CARGAR COMBO CÓDIGOS POSTALES ───────────────────────────────
    public static void cargasComboCp(JComboBox combo) {
        String SQL = "SELECT codigopostal FROM codigospostales ORDER BY codigopostal";
        combo.removeAllItems();
        combo.addItem("Seleccione");
        Connection c = Conectar();
        if (c == null) {
            return;
        }
        try {
            PreparedStatement ps = c.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getInt("codigopostal"));
            }
        } catch (SQLException e) {
            System.out.println("Error cargasComboCp: " + e.getMessage());
        } finally {
            cerrar(c);
        }
    }

    // ── CERRAR CONEXIÓN ─────────────────────────────────────────────
    private static void cerrar(Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar: " + e.getMessage());
        }
    }
}
