/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import org.apache.commons.mail.DefaultAuthentificator;
import com.sun.jdi.connect.Transport;
import java.net.Authenticator;
import java.util.Properties;
import java.util.Properties;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.SimipleEmail;


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

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public boolean enviarMailSimple() {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.hostinger.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("noreply@reynaldomd.com", "2023-Online"));
        email.setSSLonConnect(true);
        email.setCharset("UTF-8");
        email.setFrom("noreply@reynaldomd.com");
        email.setSubject(this.asunto);
        
        email.setMsg(this.mensaje); 
        email.addTo(this.destinatario);
        email.send();
        return true;
                
    }
}

