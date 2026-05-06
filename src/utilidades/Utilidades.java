/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author lajot
 */
public class Utilidades {
    

    // Centra un JFrame en pantalla
    public static void centrarVentana(JFrame v) {
        Dimension p = Toolkit.getDefaultToolkit().getScreenSize();
        v.setLocation(
                (p.width - v.getWidth()) / 2,
                (p.height - v.getHeight()) / 2
        );
    }

    // Centra un JDialog en pantalla
    public static void centrarVentana(JDialog v) {
        Dimension p = Toolkit.getDefaultToolkit().getScreenSize();
        v.setLocation(
                (p.width - v.getWidth()) / 2,
                (p.height - v.getHeight()) / 2
        );
    }

    // Valida formato DNI español (8 dígitos + letra correcta)
    public static boolean validarDNI(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Za-z]")) {
            return false;
        }
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);
        return Character.toUpperCase(dni.charAt(8)) == letraCorrecta;
    }

    // Valida formato email
    public static boolean validarEmail(String email) {
        return email != null
                && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    // Valida número de colegiado: 9 dígitos, no empieza por 0
    public static boolean validarColegiado(String num) {
        return num != null && num.matches("[1-9]\\d{8}");
    }

    // Comprueba si un String está vacío
    public static boolean vacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}


