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
    
     private static final String MILLAVE = "consultorio";

    
    public static String encriptar(String textoEncriptar) {
        
        String cadenaEncriptada = null;
        
        try {
                  
            Key millaveEnBytes = new SecretKeySpec(MILLAVE.getBytes(), "AES");
            
            Cipher encriptador = Cipher.getInstance("AES");
            encriptador.init(Cipher.ENCRYPT_MODE, millaveEnBytes);
            
            byte[] bytesEncriptados = encriptador.doFinal(textoEncriptar.getBytes());
            
            cadenaEncriptada = Base64.encodeBase64String(bytesEncriptados);
            
            return cadenaEncriptada;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.getLogger(Encriptado.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
        
        
    }

    public static String desencriptar(String textoDesencriptar) {

        try {
            String cadenadesencriptada = null;
            
            byte[] bytesEncriptados = Base64.decodeBase64(textoDesencriptar);
            
            Key millaveEnBytes = new SecretKeySpec(MILLAVE.getBytes(), "AES");
            
            Cipher encriptador = Cipher.getInstance("AES");
            try {
                encriptador.init(Cipher.DECRYPT_MODE, millaveEnBytes);
            } catch (InvalidKeyException ex) {
                System.getLogger(Encriptado.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
            
            cadenadesencriptada = new String(encriptador.doFinal(bytesEncriptados));
            
            return cadenadesencriptada;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            System.getLogger(Encriptado.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    
}
