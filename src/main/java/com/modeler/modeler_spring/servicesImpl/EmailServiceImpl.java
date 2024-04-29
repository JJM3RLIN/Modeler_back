package com.modeler.modeler_spring.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.modeler.modeler_spring.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender emailSender;
    public void sendEmailRecovery(String emailDestinatario, String ruta, String nombre) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(emailDestinatario);
            helper.setSubject("Recuperación de contraseña");
            helper.setText(genereHeaderHtml("Recuperar Contraseña", nombre) + recoverHTMLString(ruta) + generateFooterHmtl(), true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Email enviado a: " + emailDestinatario + " con la ruta: " + ruta);
    }
    public void sendEmailAddProject(String emailDestinatario, String ruta, String nombre) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(emailDestinatario);
            helper.setSubject("Has sido añadido a un proyecto");
            helper.setText(genereHeaderHtml("Te añadieron a un proyecto", nombre)+ addProjectHTMLString(ruta) + generateFooterHmtl(), true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Email enviado a: " + emailDestinatario + " con la ruta: " + ruta);
    }
    public void sendEmailAccount(String emailDestinatario, String ruta, String nombre) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(emailDestinatario);
            helper.setSubject("Verifica tu cuenta");
            helper.setText(genereHeaderHtml("Verifica tu cuenta", nombre)+ accountHTMLString(ruta) + generateFooterHmtl(), true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Email enviado a: " + emailDestinatario + " con la ruta: " + ruta);
    }
    
    private String genereHeaderHtml(String titulo, String usuario){ 
        return 
        "<!DOCTYPE html>"
        +"<html lang='es'>"
        +"<head>"
        +"<meta charset='UTF-8'>"
        +" <title>" + titulo + "</title>"
        +"<style>"
        +"body {font-family: Arial, sans-serif;margin: 0;padding: 0;background-image: url('https://192.168.0.11:8080/imgCorreo/Fondo_correo.png');background-repeat: no-repeat;background-size: contain;background-color: #f0f0f0;}"
        +".container {width: 600px;margin: 10px auto;background-color: #fff;padding: 60px;border-radius: 10px;box-shadow: 0 4px 8px rgba(0,0,0,0.1);}"
        +".containerima {margin: 10px auto;display: flex;margin: auto;}"
        +"h1 {text-align: left;font-size: 24px;margin-bottom: 20px;}"
        + "a {text-decoration: none; color: white; font-weight: bold;}"
        +" p {font-size: 16px;line-height: 1.5;}"
        +" button {display: flex; margin: auto; text-align: center;background-color: #024079; color: white;padding: 15px 30px;border-radius: 8px;margin-top: 30px;margin-bottom: 30px;cursor: pointer;transition: background-color 0.3s ease-in-out;box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);outline: none; font-weight: bold; }"        
        +".button:hover {background-color: #03539f; transition: background-color 0.3s ease-in-out;}"
        +"</style>"
        +"</head>"
        +"<body>"
        +"<div class='containerima'>"
        +"<img src='https://192.168.0.11:8080/imgCorreo/Modeler.png' height='20px;' style='display: flex; padding-left:410px; margin-bottom: 20px; margin-top: 20px;' />"
        +"</div>"
        +"<div class='container'>"
        +"<h1>Hola," + usuario + ".</h1>";
           
    }
    private String generateFooterHmtl(){
        return 
        "<p>Saludos, <br> El equipo de Modeler</p>"
        +"</div>"
        +"</body>"
        +"</html>";
    }
    private String recoverHTMLString(String ruta){
        return 
        "<p>No te preocupes, puedes recuperar tu cuenta dando click en el botón para generar tu nueva contraseña. Si no solicitaste restablecer la contraseña, puedes borrar este email y seguir diseñando con Modeler.</p>"
        +"<button class='button'><a href='" + ruta + "'>Recuperar contraseña</a></button>"
        +"<p>Si no has solicitado recuperar tu contraseña, por favor ignora este mensaje.</p>";
    }
    private String addProjectHTMLString(String ruta){
        return 
        "<p>Has sido añadido a un proyecto, inicia sesion para empezar a diseñar con Modeler.</p>"
        +"<button class='button'><a href='" + ruta + "'>Ir a Modeler</a></button>";
    }
    private String accountHTMLString(String ruta){
        return 
        "<p>Para verificar tu cuenta y comenzar a diseñar con Modeler, da click en el siguiente botón.</p>"
        +"<button class='button'><a href='" + ruta + "'>Verificar cuenta</a></button>";
    }
    
}
