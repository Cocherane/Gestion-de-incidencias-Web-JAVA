package Comunications;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;


public class Comunications {

    // puedes poner tu boot aqui para comprobar
    private String botTelegram ;
    private String emailSender ;
    private String emailPassword ;

    public Comunications(String botTelegram, String emailSender, String emailPassword) {
        this.emailSender = "######### Email HERE ########";  // emailSender;
        this.emailPassword= "######### PASSWORD HERE ########"; //emailPassword;
        this.botTelegram = botTelegram;
    }

    public boolean enviarMensajeTelegram(String mensajes, int idChat)throws IOException{
        String direccion; // url de la API de mi bot en mi conversa
        String fijo = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=", botTelegram, idChat);
        direccion = fijo + mensajes +"&parse_mode=HTML";
        URL url;
        boolean dev;
        dev = false;
        try {
            url = new URL(direccion); //creando un objeto URL con la direccion de la API del bot
            URLConnection con = url.openConnection(); // Realizando la peticion GET
            // con
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            dev =true;// ha tenido exito
        }catch ( IOException e){
            System.out.println(e.getMessage());
        }
        return dev; // Devuelvo si ha tenido exito o no


    }


    public boolean enviarMail(String destino, String mensaje, String asunto) throws RuntimeException{
        
        //if (!asunto.isBlank()) return false; // TODO: 26/03/2022 Quitar esto es solo para q no envie email en la parte de testeo
        
        boolean resultado;
        resultado = false;

        //Guardamos la direccipon que va a repetir el mensaje
        String emisor = "no-reply@fernantickets.es";
        String username = emailSender; // Usuario para el logueo en el server de correo
        String password = emailPassword; // Clave del usuario de correo

        System.out.println(username);
        System.out.println(password);

        //Host del servidor de correo
        String host = "smtp.gmail.com";

        // Creamos nuestra variables de propiedades con los datos de nuestro servidor de correo
        Properties props = new Properties();



        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Obtenemos la sesion en nuestro sevicio de correo

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username,password);
            }
        });

        try {
//


            //Creamos un mensaje de correo por defecto
            Message message = new MimeMessage(session);
            // En el mensaje, establecemos el emisor con los datos pasados a la funcion
            message.setFrom(new InternetAddress(emisor));
            // En el mensaje, establecer el emisor
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            // Esttablecer el asunto
            message.setSubject(asunto);
            // Anadimos el contenido del mensaje
            //message.setText(mensaje); // Si solo manadamos texto
            message.setContent(mensaje, "text/html; charset=utf-8");
            // Intentamos mandar el mensaje
            Transport.send(message);
            resultado = true;

        }catch (Exception e){ // Si entra aqui hemos tenido fallo
            throw new RuntimeException(e);
        }
        return resultado;
    }

    /* mensaje con adjunto */
    public boolean enviarMailAdjunto(String destino, String mensaje, String asunto, String pathAttach ){
        //Recipient's email ID:
        String toID = destino.trim();
        //Sender's email ID:
        String fromID = this.emailSender.trim();
        //Sender's Email Password:
        String password = this.emailPassword.trim();
        //Subject of the Email:
        String subject = asunto;
        //Body of the Email:
        String bodyText = mensaje;
        //Change the location of the required attachment file, the format can by anything like jpg, png, pdf etc:
        String attachmentLocation = pathAttach;
        // Host is Gmail's SMTP
        //Host del servidor de correo
        String host = "smtp.gmail.com";
        // Creamos nuestra variables de propiedades con los datos de nuestro servidor de correo
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");


        //pass the Email and password to the session object
        Session ss = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromID, password);
            }
        });
        try {
            MimeMessage mm = new MimeMessage(ss);
            //Set the 'From:' header field
            mm.setFrom(new InternetAddress(fromID));
            //Set the 'To:' header field
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(toID));
            //Set the 'Subject:' header field
            mm.setSubject(subject);
            MimeBodyPart attachment = new MimeBodyPart();
            MimeBodyPart text = new MimeBodyPart();

            //Change the location of the attachment file:
            File file = new File(attachmentLocation);
            attachment.attachFile(file);

            //Set the actual message of the Email:
            text.setText(bodyText);
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(text);
            mp.addBodyPart(attachment);

            mm.setContent(mp);

            System.out.println("sending the Email...");
            //send the mail:
            Transport.send(mm);
            System.out.println("\nEmail sent successfully!!\n");

        }
        catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return true;

    }



}

