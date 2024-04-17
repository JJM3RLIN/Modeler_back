package com.modeler.modeler_spring.DTO;

public class EmailSenderDTO {
    private String emailDestinatario;
    private String ruta;
     public EmailSenderDTO() {}
    public EmailSenderDTO(String emailDestinatario, String ruta) {
        this.emailDestinatario = emailDestinatario;
        this.ruta = ruta;
    }
    public String getEmailDestinatario() {
        return emailDestinatario;
    }
    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    
}
