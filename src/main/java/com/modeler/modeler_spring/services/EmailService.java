package com.modeler.modeler_spring.services;

import com.modeler.modeler_spring.configuration.ModelerException;

public interface EmailService {
    public void sendEmailRecovery(String emailDestinatario, String ruta, String nombre, String id) throws ModelerException;
    public void sendEmailAddProject(String emailDestinatario, String ruta, String nombre) throws ModelerException;
    public void sendEmailAccount(String emailDestinatario, String ruta, String nombre)throws ModelerException;
}
