/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;


import com.sun.jdi.connect.Transport;
import java.net.Authenticator;
import java.util.Properties;
import java.util.Properties;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;



/**
 *
 * @author lajot
 */


public class UtilidadesEmail {
    
    String asunto;
    String mensaje;
    String destinatario;
    String ruta;

    public UtilidadesEmail(String asunto, String mensaje, String destinatario, String ruta) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.ruta = ruta;
    }

    public UtilidadesEmail(String asunto, String mensaje, String destinatario) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
    }

    public boolean enviarMailSimple() {
        try {
            SimpleEmail email = new SimpleEmail();

            // CONFIGURACIÓN SMTP (Hostinger)
            email.setHostName("smtp.hostinger.com");
            email.setSmtpPort(465);
            email.setAuthenticator(
                new DefaultAuthenticator("consultorio@reynaldomd.com", "2024-Consultorio")
            );

            email.setSSLOnConnect(true);
            email.setCharset("UTF-8");

            // DATOS DEL CORREO
            email.setFrom("consultorio@reynaldomd.com");
            email.setSubject(this.asunto);
            email.setMsg(this.mensaje);
            email.addTo(this.destinatario);

            // ENVÍO
            email.send();

            return true;

        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }
}