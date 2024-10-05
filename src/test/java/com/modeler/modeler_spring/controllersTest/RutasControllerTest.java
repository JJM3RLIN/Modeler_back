package com.modeler.modeler_spring.controllersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.modeler.modeler_spring.DTO.AddUserDTO;
import com.modeler.modeler_spring.DTO.IdDTO;
import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.configuration.ModelerException;
import com.modeler.modeler_spring.controllers.RutasController;
import com.modeler.modeler_spring.models.Ruta;
import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.RutaRepository;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.responses.RutaResponse;
import com.modeler.modeler_spring.responses.UsuarioParticipanteResponse;
import com.modeler.modeler_spring.services.RutaService;

@SpringBootTest
public class RutasControllerTest {

    @Mock
    private RutaRepository rutaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RutaService rutaService;

    @InjectMocks
    private RutasController systemUnderTest;

    @Test
    public void When_CallCreate_And_RutaDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        //Arrange
        doThrow(new ModelerException(null)).when(this.rutaService).create(null);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.create(null));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallCreate_And_RutaDTOIsValid_Then_ShouldReturnARutaResponse() throws ModelerException {
        //Arrange
        RutaDTO rutaDTO = new RutaDTO("123", "Prueba");
        RutaResponse rutaResponse = new RutaResponse("123", "prueba", "nombreTest");
        //SetUp
        when(this.rutaService.create(rutaDTO)).thenReturn(rutaResponse);

        //Act
        ResponseEntity<?> response = this.systemUnderTest.create(rutaDTO);

        //Assert
        assertEquals(response.getBody(), rutaResponse);
    }

    @Test
    public void When_CallUpdate_And_RutaDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        //Arrange
        doThrow(new ModelerException(null)).when(this.rutaService).update(null);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.update(null));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallUpdate_And_RutaDTOIsValid_Then_ShouldReturnAString() throws ModelerException {
        //Arrange
        RutaDTO rutaDTO = new RutaDTO("123", "Prueba");
        //SetUp
        when(this.rutaService.update(rutaDTO)).thenReturn(anyString());

        //Act
        ResponseEntity<?> response = this.systemUnderTest.update(rutaDTO);

        //Assert
        assertNotNull(response.getBody());
    }

    
    @Test
    public void When_CallDelete_And_IdDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String id = "123";

        //Arrange
        when(this.rutaRepository.findById(id)).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.rutaService).delete(id);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.delete(new IdDTO(id)));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallDelete_And_IdDTOIsValid_Then_ShouldReturnAString() throws ModelerException {
        //Arrange
        IdDTO idDTO = new IdDTO( "1234");

        //SetUp
        when(this.rutaService.delete(idDTO.getId())).thenReturn(anyString());

        //Act
        ResponseEntity<?> response = this.systemUnderTest.delete(idDTO);

        //Assert
        assertNotNull(response);
    }

    @Test
    public void When_CallUsuariosParticipanEnProyecto_And_IdDTOIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String id = "123";

        //Arrange
        when(this.rutaRepository.findById(id)).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.rutaService).obtenerUsuariosParticipantesDeProyecto(id);

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.usuariosParticipanEnProyecto(new IdDTO(id)));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallUsuariosParticipanEnProyecto_And_IdDTOIsValid_Then_ShouldReturnAList() throws ModelerException {
        //Arrange
        IdDTO idDTO = new IdDTO( "1234");
        List<UserDTO> users = new ArrayList<>();
        users.add(new UserDTO(123, "Test"));
        //SetUp
        when(this.rutaService.obtenerUsuariosParticipantesDeProyecto(idDTO.getId())).thenReturn(users);

        //Act
        ResponseEntity<?> response = this.systemUnderTest.usuariosParticipanEnProyecto(idDTO);

        //Assert
        assertEquals(response.getBody(), users);
    }

    @Test
    public void When_CallAddUser_And_RutaIdIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String correo = "correo@test.com";
        AddUserDTO userDTO = new AddUserDTO( "1234", correo);

        //Arrange
        when(this.rutaRepository.findById(userDTO.getIdRuta())).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.rutaService).addUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario());

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.addUser(userDTO));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallAddUser_And_UserIdIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String correo = "correo@test.com";
        AddUserDTO userDTO = new AddUserDTO( "1234", correo);

        //Arrange
        when(this.userRepository.findByEmail(userDTO.getEmailUsuario())).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.rutaService).addUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario());

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.addUser(userDTO));

        //Assert
        assertNotNull(ex);
    }
    @Test
    public void When_CallAddUser_And_UserIsAlreadyInTheProject_Then_ShouldThrowAnException() throws ModelerException {
        //Arrange
        String correo = "correo@test.com";
        String idRuta = "1234";
        AddUserDTO userDTO = new AddUserDTO( idRuta, correo);
        User userCreador = new User("nombre", "test@gbm.com", "password", new ArrayList<Ruta>());
        User userParticipante = new User("nombre", correo, "password", new ArrayList<Ruta>());
        List<User> usersParticipantes = new ArrayList<>();
        usersParticipantes.add(userCreador);
        usersParticipantes.add(userParticipante);
        Ruta ruta = new Ruta(idRuta, "Ruta", userCreador, usersParticipantes);
        userCreador.getRutasCreadas().add(ruta);
        //SetUp
        when(this.rutaRepository.findById(idRuta)).thenReturn(Optional.of(ruta));
        when(this.userRepository.findByEmail(userDTO.getEmailUsuario())).thenReturn(Optional.of(userParticipante));
        doThrow(new ModelerException(null)).when(this.rutaService).removeUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario());


        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.removeUser(userDTO));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallAddUser_And_AddUserDTOIsValid_Then_ShouldReturnAUsuarioParticipanteResponse() throws ModelerException {
        //Arrange
        String correo = "correo@test.com";
        AddUserDTO userDTO = new AddUserDTO( "1234", correo);
        UsuarioParticipanteResponse user = new UsuarioParticipanteResponse("Test", correo, "1234");

        //SetUp
        when(this.rutaService.addUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario())).thenReturn(user);

        //Act
        ResponseEntity<?> response = this.systemUnderTest.addUser(userDTO);

        //Assert
        assertEquals(response.getBody(), user);
    }

    @Test
    public void When_CallRemoveUser_And_RutaIdIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String correo = "correo@test.com";
        AddUserDTO userDTO = new AddUserDTO( "1234", correo);

        //Arrange
        when(this.rutaRepository.findById(userDTO.getIdRuta())).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.rutaService).removeUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario());

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.removeUser(userDTO));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallRemoveUser_And_UserIdIsNotValid_Then_ShouldThrowAnException() throws ModelerException {
        
        String correo = "correo@test.com";
        AddUserDTO userDTO = new AddUserDTO( "1234", correo);

        //Arrange
        when(this.userRepository.findByEmail(userDTO.getEmailUsuario())).thenReturn(Optional.empty());
        doThrow(new ModelerException(null)).when(this.rutaService).removeUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario());

        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.removeUser(userDTO));

        //Assert
        assertNotNull(ex);
    }

    @Test
    public void When_CallRemoveUser_And_UserIsNotInTheProject_Then_ShouldThrowAnException() throws ModelerException {
        //Arrange
        String correo = "correo@test.com";
        String idRuta = "1234";
        AddUserDTO userDTO = new AddUserDTO( idRuta, correo);
        User userCreador = new User("nombre", "test@gbm.com", "password", new ArrayList<Ruta>());
        User userParticipante = new User("nombre", correo, "password", new ArrayList<Ruta>());
        List<User> usersParticipantes = new ArrayList<>();
        usersParticipantes.add(userCreador);
        Ruta ruta = new Ruta(idRuta, "Ruta", userCreador, usersParticipantes);
        userCreador.getRutasCreadas().add(ruta);
        //SetUp
        when(this.rutaRepository.findById(idRuta)).thenReturn(Optional.of(ruta));
        when(this.userRepository.findByEmail(userDTO.getEmailUsuario())).thenReturn(Optional.of(userParticipante));
        doThrow(new ModelerException(null)).when(this.rutaService).removeUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario());


        //Act
        Exception ex = assertThrows(ModelerException.class, () -> this.systemUnderTest.removeUser(userDTO));

        //Assert
        assertNotNull(ex);
    }
    @Test
    public void When_CallRemoveUser_And_AddUserDTOIsValid_Then_ShouldReturnAString() throws ModelerException {
        //Arrange
        String correo = "correo@test.com";
        String idRuta = "1234";
        AddUserDTO userDTO = new AddUserDTO( idRuta, correo);
        User userCreador = new User("nombre", "test@gbm.com", "password", new ArrayList<Ruta>());
        User userParticipante = new User("nombre", correo, "password", new ArrayList<Ruta>());
        List<User> usersParticipantes = new ArrayList<>();
        usersParticipantes.add(userParticipante);
        Ruta ruta = new Ruta(idRuta, "Ruta", userCreador, usersParticipantes);
        userCreador.getRutasCreadas().add(ruta);
        //SetUp
        when(this.rutaRepository.findById(idRuta)).thenReturn(Optional.of(ruta));
        when(this.userRepository.findByEmail(userDTO.getEmailUsuario())).thenReturn(Optional.of(userParticipante));
        when(this.rutaService.removeUsuarioParticipante(userDTO.getIdRuta(), userDTO.getEmailUsuario())).thenReturn("Usuario eliminado de manera correcta");

        //Act
        ResponseEntity<?> response = this.systemUnderTest.removeUser(userDTO);

        //Assert
        assertNotNull(response);
    }
}
