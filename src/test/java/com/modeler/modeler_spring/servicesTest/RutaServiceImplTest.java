package com.modeler.modeler_spring.servicesTest;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.RutaRepository;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.services.EmailService;
import com.modeler.modeler_spring.servicesImpl.RutaServiceImpl;
import com.modeler.modeler_spring.DTO.RutaDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.configuration.ModelerException;
import com.modeler.modeler_spring.models.Ruta;

@SpringBootTest
public class RutaServiceImplTest {

@Mock
private UserRepository userRepository;

@Mock
private RutaRepository rutaRepository;

@Mock
private EmailService emailService;

@InjectMocks
private RutaServiceImpl systemUnderTest;

@Test
public void When_CallCreate_And_UserIsNotRegistered_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        int id = 1;
        RutaDTO rutaDTO = new RutaDTO("Test", id);

        //setUp
        when(this.userRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.create(rutaDTO));

        //Assert
        assertEquals("No se encontro el usuario creador", exception.getMessage());
}


@Test
public void When_CallCreate_And_UserIsRegistered_ShouldExecute() throws ModelerException 
{
        //Arrange
        List<Ruta> rutasCreadas = new ArrayList<Ruta>();
        User userMock = new User("Test","email@test.com", "abcd", rutasCreadas, null);
        userMock.setId(1);
        Ruta rutaMock = new Ruta("1", "Test", null, userMock, null);
        userMock.getRutasCreadas().add(rutaMock);
        RutaDTO rutaDTO = new RutaDTO("Test", 1);

        //setUp
        when(this.userRepository.findById(1)).thenReturn(Optional.of(userMock));
        when(this.rutaRepository.save(any(Ruta.class))).thenReturn(rutaMock);
        when(this.userRepository.save(userMock)).thenReturn(userMock);

        //Act
        this.systemUnderTest.create(rutaDTO);

        //Assert
        verify(this.rutaRepository, times(1)).save(any(Ruta.class));
        verify(this.userRepository, times(1)).save(userMock);
}

@Test
public void When_CallUpdate_And_RutaDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        RutaDTO rutaDTO = new RutaDTO("Test", 1);

        //setUp
        when(this.rutaRepository.findById(anyString())).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.update(rutaDTO));

        //Assert
        assertEquals("No se encontro la ruta", exception.getMessage());
        verify(this.rutaRepository, times(0)).save(any(Ruta.class));
}

@Test
public void When_CallUpdate_And_RutaExists_ShouldExecute() throws ModelerException 
{
        //Arrange
        String rutaId = "1";
        User userMock = new User("Test","email@test.com", "abcd", null, null);
        Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, null);
        RutaDTO rutaDTO = new RutaDTO("Test", 1);
        rutaDTO.setId(rutaId);
    
        //setUp
        when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
        when(this.rutaRepository.save(any(Ruta.class))).thenReturn(any(Ruta.class));

        //Act
        this.systemUnderTest.update(rutaDTO);

        //Assert
        verify(this.rutaRepository, times(1)).findById(anyString());
        verify(this.rutaRepository, times(1)).save(any(Ruta.class));
}

@Test
public void When_CallDelete_And_RutaDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String rutaId = "1";

        //setUp
        when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.empty());
        when(this.rutaRepository.save(any(Ruta.class))).thenReturn(any(Ruta.class));

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.delete(rutaId));

        //Assert
        assertEquals("La ruta no existe", exception.getMessage());
        verify(this.rutaRepository, times(0)).save(any(Ruta.class));
}

@Test
public void When_CallDelete_And_UserExists_ShouldExecute() throws ModelerException 
{
        //Arrange
        Ruta rutaMock = new Ruta("1", "Test", null, null, null);
    
        //setUp
        when(this.rutaRepository.findById(anyString())).thenReturn(Optional.of(rutaMock));

        //Act
        this.systemUnderTest.delete(anyString());

        //Assert
        verify(this.rutaRepository, times(1)).findById(anyString());
        verify(this.rutaRepository, times(1)).deleteById(anyString());
}

@Test
public void When_CalladdUsuarioParticipante_And_UserDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
    String rutaId = "1";
    String email = "email@test.com";
    User userMock = new User("Test",email, "abcd", null, null);
    Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, null);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
    when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

    //Act
    Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.addUsuarioParticipante(rutaId, email));

    //Assert
    assertEquals("No se encontro el usuario", exception.getMessage());
}

@Test
public void When_CalladdUsuarioParticipante_And_RutaDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
    //Arrange
    String rutaId = "1";
    String email = "email@test.com";
    User userMock = new User("Test", email, "abcd", null, null);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.empty());
    when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(userMock));

    //Act
    Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.addUsuarioParticipante(rutaId, email));

    //Assert
    assertEquals("No se encontro la ruta", exception.getMessage());
}

@Test
public void When_CalladdUsuarioParticipante_And_UserIsInRoute_ShouldThrowAnException() throws ModelerException 
{
    //Arrange
    List<Ruta> rutasCreadas = new ArrayList<Ruta>();
    List<User> usuariosParticipantes = new ArrayList<User>();
    String rutaId = "1";
    String email = "email@test2.com";
    User userMock = new User("Test", "email@test.com", "abcd", rutasCreadas, null);
    User user2Mock = new User("Test", email, "abcd", null, null);
    userMock.setId(1);
    Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, usuariosParticipantes);
    rutaMock.getUsuariosParticipantes().add(user2Mock);
    userMock.getRutasCreadas().add(rutaMock);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
    when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(userMock));

    //Act
    Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.addUsuarioParticipante(rutaId, email));

    //Assert
    assertEquals("El usuario ya esta en la ruta", exception.getMessage());
}

@Test
public void When_CalladdUsuarioParticipante_And_UserIsNotInRoute_And_RutaExists_ShouldExecute() throws ModelerException 
{
    //Arrange
    List<Ruta> rutasCreadas = new ArrayList<Ruta>();
    List<User> usuariosParticipantes = new ArrayList<User>();
    String rutaId = "1";
    String email = "email@test2.com";
    User userMock = new User("Test", "email@test.com", "abcd", rutasCreadas, null);
    User user2Mock = new User("Test", email, "abcd", null, null);
    userMock.setId(1);
    Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, usuariosParticipantes);
    userMock.getRutasCreadas().add(rutaMock);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
    when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user2Mock));
    when(this.rutaRepository.save(rutaMock)).thenReturn(any(Ruta.class));

    //Act
    this.systemUnderTest.addUsuarioParticipante(rutaId, email);

    //Assert
    verify(this.rutaRepository, times(1)).save(any(Ruta.class));
    verify(this.emailService, times(1)).sendEmailAddProject(email, "http://localhost:5173/login", user2Mock.getNombre());
}

@Test
public void When_CallobtenerUsuarioParticipantesDeProyecto_And_RutaDoesNotExist_ShouldThrowAnException() throws ModelerException 
{

        //setUp
        when(this.rutaRepository.findById(anyString())).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.obtenerUsuariosParticipantesDeProyecto(anyString()));

        //Assert
        assertEquals("No se encontro la ruta", exception.getMessage());
}

@Test
public void When_CallobtenerUsuarioParticipantesDeProyecto_And_RutaExists_ShouldExecute() throws ModelerException 
{
    //Arrange
    UserDTO userDTO = new UserDTO(1, "Test", "test@correo");
    List<Ruta> rutasCreadas = new ArrayList<Ruta>();
    List<UserDTO> usuariosParticipantes = List.of(userDTO);
    String rutaId = "1";
    User userMock = new User("Test", "email@test.com", "abcd", rutasCreadas, null);
    userMock.setId(1);
    Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, new ArrayList<User>());
    userMock.getRutasCreadas().add(rutaMock);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
    when(this.rutaRepository.findUsuariosEnProyecto(rutaId)).thenReturn(usuariosParticipantes);

    //Act
    List<UserDTO> usuarios = this.systemUnderTest.obtenerUsuariosParticipantesDeProyecto(rutaId);

    //Assert
    verify(this.rutaRepository, times(1)).findUsuariosEnProyecto(rutaId);
    assertEquals(usuariosParticipantes, usuarios);
}

@Test
public void When_CallremoveUsuarioParticipante_And_UserDoesNotExit_ShouldThrowAnException() throws ModelerException 
{
    //Arrange
    List<Ruta> rutasCreadas = new ArrayList<Ruta>();
    List<User> usuariosParticipantes = new ArrayList<User>();
    String rutaId = "1";
    String email = "email@test2.com";
    User userMock = new User("Test", "email@test.com", "abcd", rutasCreadas, null);
    Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, usuariosParticipantes);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
    when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

    //Act
    Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.removeUsuarioParticipante(rutaId, email));

    //Assert
    assertEquals("No se encontro el usuario", exception.getMessage());
}

@Test
public void When_CallremoveUsuarioParticipante_And_UserIsNotInRoute_ShouldThrowAnException() throws ModelerException 
{
    //Arrange
    List<Ruta> rutasCreadas = new ArrayList<Ruta>();
    List<User> usuariosParticipantes = new ArrayList<User>();
    String rutaId = "1";
    String email = "email@test2.com";
    User userMock = new User("Test", "email@test.com", "abcd", rutasCreadas, null);
    User user2Mock = new User("Test", email, "abcd", null, null);
    userMock.setId(1);
    Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, usuariosParticipantes);
    userMock.getRutasCreadas().add(rutaMock);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
    when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user2Mock));

    //Act
    Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.removeUsuarioParticipante(rutaId, email));

    //Assert
    assertEquals("El usuario no esta en la ruta", exception.getMessage());
}

@Test
public void When_CallremoveUsuarioParticipante_And_UserIsInRoute_ShouldThrowAnException() throws ModelerException 
{
    //Arrange
    List<Ruta> rutasCreadas = new ArrayList<Ruta>();
    List<User> usuariosParticipantes = new ArrayList<User>();
    String rutaId = "1";
    String email = "email@test2.com";
    User userMock = new User("Test", "email@test.com", "abcd", rutasCreadas, null);
    User user2Mock = new User("Test", email, "abcd", null, null);
    userMock.setId(1);
    Ruta rutaMock = new Ruta(rutaId, "Test", null, userMock, usuariosParticipantes);
    rutaMock.getUsuariosParticipantes().add(user2Mock);
    userMock.getRutasCreadas().add(rutaMock);

    //setUp
    when(this.rutaRepository.findById(rutaId)).thenReturn(Optional.of(rutaMock));
    when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(user2Mock));
    when(this.rutaRepository.save(rutaMock)).thenReturn(rutaMock);

    //Act
    this.systemUnderTest.removeUsuarioParticipante(rutaId, email);

    //Assert
    verify(this.rutaRepository, times(1)).save(any(Ruta.class));
    assertEquals(0, rutaMock.getUsuariosParticipantes().size());
}

}