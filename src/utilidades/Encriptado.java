/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author lajot
 */
public class Encriptado {
    
     private static final String millave = "vJMnURwFuojTiaJT";

    public static String Encriptar(String textoEncriptar) {
        String cadenaEncriptada = null;

        try {

            //Convierte la clave en bytes y asociamos que en el algotitamo se utiliza.
            Key millaveEnBytes = new SecretKeySpec(millave.getBytes(), "AES");

            Cipher encriptador = Cipher.getInstance("AES");
            encriptador.init(Cipher.ENCRYPT_MODE, millaveEnBytes);

            byte[] bytesEncriptados = encriptador.doFinal(textoEncriptar.getBytes());

            cadenaEncriptada = Base64.encodeBase64String(bytesEncriptados);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException ex) {
            System.getLogger(Encriptado.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return cadenaEncriptada;
    }

    public static String Desencriptar(String textoDesencriptar) {
        String cadenadesencriptada = null;

        try {
            byte[] bytesEncriptados = Base64.decodeBase64(textoDesencriptar);

            //Convertimos nuestra llave privada a bytes, indicamos que el algoritmo se va a asociar
            Key millaveEnBytes = new SecretKeySpec(millave.getBytes(), "AES");

            Cipher desencriptador = Cipher.getInstance("AES");
            desencriptador.init(Cipher.DECRYPT_MODE, millaveEnBytes);

            cadenadesencriptada = new String(desencriptador.doFinal(bytesEncriptados));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.getLogger(Encriptado.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return cadenadesencriptada;

    }
}
