package com.modeler.modeler_spring.DTO;

public class AddUserDTO {
    private String idRuta;
    private String emailUsuario;
    public AddUserDTO() {
    }
    public AddUserDTO(String idRuta, String emailUsuario) {
        this.idRuta = idRuta;
        this.emailUsuario = emailUsuario;
    }
    public AddUserDTO(String idRuta) {
        this.idRuta = idRuta;
    }
    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }
    public String getEmailUsuario() {
        return emailUsuario;
    }
    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }
}
