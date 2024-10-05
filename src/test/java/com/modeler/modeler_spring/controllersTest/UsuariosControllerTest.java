package com.modeler.modeler_spring.controllersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.modeler.modeler_spring.DTO.EmailDTO;
import com.modeler.modeler_spring.DTO.IdDTO;
import com.modeler.modeler_spring.DTO.RutasUsuarioDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.configuration.ModelerException;
import com.modeler.modeler_spring.controllers.UsuariosController;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.services.UserService;


@SpringBootTest
public class UsuariosControllerTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private UserService userService;

    @InjectMocks
    private UsuariosController systemUnderTest;

    @Test
    public void When_CallFindByEmail_And_EmailIsNotRegistered_Then_ShouldThrowAnException() throws ModelerException {
        
        EmailDTO emailDTO = new EmailDTO("emailTest@correo.com");

        //Arrange
        when(this.userRepo.findByEmail(emailDTO.getEmail())).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.userService).findByEmail(emailDTO.getEmail());

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.findByEmail(emailDTO));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallFindByEmail_And_EmailIsValid_Then_ShouldReturnUser() throws ModelerException {
        //Arrange
        EmailDTO emailDTO = new EmailDTO("emailTest@correo.com");
        User user = new User("nameTest", emailDTO.getEmail(), "passwordTest", null, null);
        //Arrange
        when(this.userRepo.findByEmail(emailDTO.getEmail())).thenReturn(Optional.of(user));
        when(this.userService.findByEmail(emailDTO.getEmail())).thenReturn(Optional.of(user));

        //Act
        ResponseEntity<?> response = this.systemUnderTest.findByEmail(emailDTO);

        //Assert
        assertNotNull(response);
    }

    @Test
    public void When_CallCreate_And_UserDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        //Arrange
        doThrow(new ModelerException(null)).when(this.userService).create(null);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.create(null));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallCreate_And_UserDTOIsValid_Then_ShouldReturnAString() throws ModelerException {
        //Arrange
        UserDTO userDTO = new UserDTO("nameTest", "emailTest", "passwordTest");

        //SetUp
        when(this.userService.create(userDTO)).thenReturn(anyString());

        //Act
        ResponseEntity<?> response = this.systemUnderTest.create(userDTO);

        //Assert
        assertNotNull(response);
    }

    @Test
    public void When_CallDelete_And_IdDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        Integer id = 123;

        //Arrange
        when(this.userRepo.findById(id)).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.userService).delete(id);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.delete(new IdDTO(id.toString())));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallDelete_And_IdDTOIsValid_Then_ShouldReturnAString() throws ModelerException {
        //Arrange
        IdDTO idDTO = new IdDTO( "1234");

        //SetUp
        when(this.userService.delete(Integer.parseInt(idDTO.getId()))).thenReturn(anyString());

        //Act
        ResponseEntity<?> response = this.systemUnderTest.delete(idDTO);

        //Assert
        assertNotNull(response);
    }

    @Test
    public void When_CallUpdate_And_UserDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        //Arrange
        doThrow(new ModelerException(null)).when(this.userService).update(null);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.update(null));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallUpdate_And_UserDTOIsValid_Then_ShouldReturnAString() throws ModelerException {
        //Arrange
        UserDTO userDTO = new UserDTO("nameTest", "emailTest", "passwordTest");
        UserDTO userDTOUpdated = new UserDTO("nameTestUpdated", "emailTest", "passwordTest");
        User userUpdated = new User(userDTOUpdated.getNombre(), userDTOUpdated.getEmail(), userDTOUpdated.getPassword(), null, null);
        //SetUp
        when(this.userRepo.save(userUpdated)).thenReturn(userUpdated);
        when(this.userService.update(userDTO)).thenReturn(anyString());

        //Act
        ResponseEntity<?> response = this.systemUnderTest.update(userDTO);

        //Assert
        assertNotNull(response);
    }


    @Test
    public void When_CallVerificarToken_And_TokenDoesntExistOrIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String token = "123";

        //Arrange
        when(this.userRepo.findByToken(token)).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.userService).verifyToken(token);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.verificarToken(token));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallVerificarToken_And_IdDTOIsValid_Then_ShouldReturnABool() throws ModelerException {
        //Arrange
        String token = "123";
        User user = new User("nameTest", "emailTest", "passwordTest", null, null);
        //SetUp
        when(this.userRepo.findByToken(token)).thenReturn(Optional.of(user));
        when(this.userService.verifyToken(token)).thenReturn(anyBoolean());

        //Act
        ResponseEntity<?> response = this.systemUnderTest.verificarToken(token);

        //Assert
        assertNotNull(response);
    }

    @Test
    public void When_CallRutasUsuario_And_IdIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String id = "123";

        //Arrange
        when(this.userRepo.findById(Integer.parseInt(id))).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.userService).rutasParticipante(Integer.parseInt(id));

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.rutasUsuario(id));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallRutasUsuario_And_IdsValid_Then_ShouldReturnAList() throws ModelerException {
        //Arrange
        String id = "123";
        User user = new User("nameTest", "emailTest", "passwordTest", null, null);
        List<RutasUsuarioDTO> rutaUsuarioList = new ArrayList<>();
        rutaUsuarioList.add(new RutasUsuarioDTO(id, "ruta", 123));
        //SetUp
        when(this.userRepo.findById(Integer.parseInt(id))).thenReturn(Optional.of(user));
        when(this.userService.rutasParticipante(Integer.parseInt(id))).thenReturn(rutaUsuarioList);

        //Act
        ResponseEntity<?> response = this.systemUnderTest.rutasUsuario(id);

        //Assert
        assertEquals(rutaUsuarioList, response.getBody());
    }

    @Test
    public void When_CallRecoverPassword_And_EmailIsNotRegistered_Then_ShouldThrowAnException() throws ModelerException {
        
        EmailDTO emailDTO = new EmailDTO("emailTest@correo.com");

        //Arrange
        when(this.userRepo.findByEmail(emailDTO.getEmail())).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.userService).sendEmailRecovery(emailDTO.getEmail());

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.recoverPassword(emailDTO));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallRecoverPassword_And_EmailIsValid_Then_ShouldReturnOk() throws ModelerException {
        //Arrange
        EmailDTO emailDTO = new EmailDTO("emailTest@correo.com");
        String nombre = "nombre";
        User user = new User(nombre, emailDTO.getEmail(), "passwordTest", null, null);

        //Arrange
        when(this.userRepo.findByEmail(emailDTO.getEmail())).thenReturn(Optional.of(user));

        //Act
        ResponseEntity<?> response = this.systemUnderTest.recoverPassword(emailDTO);

        //Assert
        verify(this.userService, times(1)).sendEmailRecovery(emailDTO.getEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void When_CallResetPassword_And_UserDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        //Arrange
        doThrow(new ModelerException(null)).when(this.userService).recoverPassword(null);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.resetPassword(null));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallResetPassword_And_UserDTOIsValid_Then_ShouldReturnAString() throws ModelerException {
        //Arrange
        UserDTO userDTO = new UserDTO("nameTest", "emailTest", "passwordTest");
        //SetUp
        when(this.userService.recoverPassword(userDTO)).thenReturn(anyString());

        //Act
        ResponseEntity<?> response = this.systemUnderTest.resetPassword(userDTO);

        //Assert
        assertNotNull(response);
    }
}
