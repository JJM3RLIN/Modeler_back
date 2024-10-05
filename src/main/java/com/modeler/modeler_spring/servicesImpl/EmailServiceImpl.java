package com.modeler.modeler_spring.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.modeler.modeler_spring.configuration.ModelerException;
import com.modeler.modeler_spring.services.EmailService;
import com.modeler.modeler_spring.services.Errors;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmailRecovery(String emailDestinatario, String ruta, String nombre, String id) throws ModelerException {

        try {

            MimeMessage message =  GetMimmeHelperConfig(
                emailSender.createMimeMessage(), 
                emailDestinatario, 
                genereHeaderHtml("Recuperar Contraseña", nombre) + recoverHTMLString(ruta, id) + generateFooterHmtl(),
                "Recuperación de contraseña"
                );
            emailSender.send(message);

        } catch (Exception e) {
            throw new ModelerException(Errors.EMAIL_NOT_SENT);
        }
    }

    public void sendEmailAddProject(String emailDestinatario, String ruta, String nombre) throws ModelerException{

        try {

            MimeMessage message =  GetMimmeHelperConfig(
                emailSender.createMimeMessage(), 
                emailDestinatario, 
                genereHeaderHtml("Te añadieron a un proyecto", nombre)+ addProjectHTMLString(ruta) + generateFooterHmtl(),
                "Has sido añadido a un proyecto de Modeler"
                );
            emailSender.send(message);

        } catch (Exception e) {
            throw new ModelerException(Errors.EMAIL_NOT_SENT);
        }
    }

    public void sendEmailAccount(String emailDestinatario, String ruta, String nombre) throws ModelerException {

        try {
            
            MimeMessage message =  GetMimmeHelperConfig(
                emailSender.createMimeMessage(), 
                emailDestinatario, 
                genereHeaderHtml("Verifica tu cuenta", nombre)+ accountHTMLString(ruta) + generateFooterHmtl(),
                "Verificación de cuenta"
                );
            emailSender.send(message);                  
            
        } catch (Exception e) {
            throw new ModelerException(Errors.EMAIL_NOT_SENT);                 
        }
    }

    private static MimeMessage GetMimmeHelperConfig(MimeMessage message, String emailDestinatario, String text, String subject) throws ModelerException {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(emailDestinatario);
            helper.setSubject(subject);
            helper.setText(text, true);
            return message;
        } catch (MessagingException e) {
            throw new ModelerException(Errors.EMAIL_NOT_SENT);
        }    
    }
    
    private static String genereHeaderHtml(String titulo, String usuario){ 
        return 
        "<!DOCTYPE html>"
        +"<html lang='es'>"
        +"<head>"
        +"<meta charset='UTF-8'>"
        +" <title>" + titulo + "</title>"
        +"<style>"
        +"body {font-family: Arial, sans-serif;margin: 0;padding: 0;background-color: #f0f0f0;}"
        +".container {width: 600px;margin: 10px auto;background-color: #fff;padding: 60px;border-radius: 10px;box-shadow: 0 4px 8px rgba(0,0,0,0.1);}"
        +".containerima {margin: 10px auto;display: flex;margin: auto;}"
        +"h1 {text-align: center;font-size: 40px;margin-bottom: 25px;  letter-spacing: 1.3px; color: #0369a1; font-weight: bold;}"
        +"h2 {text-align: left;font-size: 24px;margin-bottom: 20px;}"
        +"p {font-size: 16px;line-height: 1.5;}"
        +"a {display: block; margin: auto; text-align: center;text-decoration: none; color: white !important;background-color: #024079;padding: 15px 20px;border-radius: 8px;margin-top: 30px;margin-bottom: 30px;cursor: pointer;outline: none; font-weight: bold; }"        
        +"</style>"
        +"</head>"
        +"<body>"
        +"<div class='container'>"
        +"<h1>Modeler</h1>"
        +"<h2>Hola," + " " + usuario + ".</h2>";
           
    }
    private static String generateFooterHmtl(){
        return 
        "<p>Saludos, <br> El equipo de Modeler</p>"
        +"</div>"
        +"</body>"
        +"</html>";
    }
    private static String recoverHTMLString(String ruta, String id){
        return 
        "<p>No te preocupes, puedes recuperar tu cuenta dando click en el botón para generar tu nueva contraseña. Si no solicitaste restablecer la contraseña, puedes borrar este email y seguir diseñando con Modeler.</p>"
        +"<a href='" + ruta + "/" + id + "'>Recuperar contraseña</a>"
        +"<p>Si no has solicitado recuperar tu contraseña, por favor ignora este mensaje.</p>";
    }
    private static String addProjectHTMLString(String ruta){
        return 
        "<p>Has sido añadido a un proyecto, inicia sesion para empezar a diseñar con Modeler.</p>"
        +"<a href='" + ruta + "'>Ir a Modeler</a";
    }
    private static String accountHTMLString(String ruta){
        return 
        "<p>Para verificar tu cuenta y comenzar a diseñar con Modeler, da click en el siguiente botón.</p>"
        +"<a href='" + ruta + "'>Verificar cuenta</a>";
    }
    
}
