package com.modeler.modeler_spring.services;

public interface EmailService {
    public void sendEmailRecovery(String emailDestinatario, String ruta, String nombre);
    public void sendEmailAddProject(String emailDestinatario, String ruta, String nombre);
    public void sendEmailAccount(String emailDestinatario, String ruta, String nombre);
}
