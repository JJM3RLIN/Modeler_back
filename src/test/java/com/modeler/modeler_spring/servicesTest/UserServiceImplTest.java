package com.modeler.modeler_spring.servicesTest;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.modeler.modeler_spring.models.User;
import com.modeler.modeler_spring.repositories.RutaRepository;
import com.modeler.modeler_spring.repositories.UserRepository;
import com.modeler.modeler_spring.services.EmailService;
import com.modeler.modeler_spring.servicesImpl.UserServiceImpl;
import com.modeler.modeler_spring.DTO.RutasUsuarioDTO;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.configuration.ModelerException;

@SpringBootTest
public class UserServiceImplTest {
@Mock
private UserRepository userRepository;

@Mock
private RutaRepository rutaRepository;

@Mock
private EmailService emailService;

@Mock
private PasswordEncoder passwordEncoder;

@InjectMocks
private UserServiceImpl systemUnderTest;

@Test
public void When_CallCreate_And_UserIsRegisteredWithTheSameEmail_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
    
        //setUp
        UserDTO userDTO = new UserDTO(name, email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userMock));
        when(userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.create(userDTO));

        //Assert
        assertEquals("El email ya esta en uso", exception.getMessage());
}

@Test
public void When_CallCreate_And_CouldNotSendEmail_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
    
        //setUp
        UserDTO userDTO = new UserDTO(name, email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userMock);
        doThrow(new ModelerException("Ocurrio un error el enviar el email")).when(emailService).sendEmailAccount(anyString(), anyString(), anyString());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.create(userDTO));

        //Assert
        assertEquals("Ocurrio un error el enviar el email", exception.getMessage());
    
}

@Test
public void When_CallCreate_And_IsNotRegistered_ShouldExecute() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
    
        //setUp
        UserDTO userDTO = new UserDTO(name, email, password);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        systemUnderTest.create(userDTO);

        //Assert
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(any(User.class));
}

@Test
public void When_CallUpdate_And_UserDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null);
        UserDTO userDTO = new UserDTO(name, email, password);
        userDTO.setId(1);
    
        //setUp
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.update(userDTO));

        //Assert
        assertEquals("El usuario no existe", exception.getMessage());
        verify(userRepository, times(0)).save(userMock);
}

@Test
public void When_CallUpdate_And_UserExists_ShouldExecute() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
        UserDTO userDTO = new UserDTO(name, email, password);
        userDTO.setId(1);
    
        //setUp
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userMock));
        when(userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        systemUnderTest.create(userDTO);

        //Assert
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(any(User.class));
}

@Test
public void When_CallDelete_And_UserDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        UserDTO userDTO = new UserDTO(name, email, password);
        userDTO.setId(1);
    
        //setUp
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.delete(userDTO.getId()));

        //Assert
        assertEquals("El usuario no existe", exception.getMessage());
        verify(userRepository, times(0)).deleteById(userDTO.getId());
}

@Test
public void When_CallDelete_And_UserExists_ShouldExecute() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
        UserDTO userDTO = new UserDTO(name, email, password);
        userDTO.setId(1);
    
        //setUp
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userMock));

        //Act
        systemUnderTest.delete(userDTO.getId());

        //Assert
        verify(userRepository, times(1)).findById(userDTO.getId());
        verify(userRepository, times(1)).deleteById(userDTO.getId());
}

@Test
public void When_CallVerifyToken_And_TokenDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
    
        //setUp
        when(userRepository.findByToken(token)).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.verifyToken(token));

        //Assert
        assertEquals("El token no es valido", exception.getMessage());
        verify(userRepository, times(0)).save(userMock);
}

@Test
public void When_CallVerifyToken_And_TokenExists_ShouldExecute() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
    
        //setUp
        when(userRepository.findByToken(token)).thenReturn(Optional.of(userMock));

        //Act
        systemUnderTest.verifyToken(token);

        //Assert
        assertNull(userMock.getToken());
        verify(userRepository, times(1)).findByToken(token);
        verify(userRepository, times(1)).save(userMock);
}

@Test
public void When_CallRutasParticipante_And_UserDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        int id = 1;
    
        //setUp
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.rutasParticipante(id));

        //Assert
        assertEquals("El usuario no existe", exception.getMessage());
        verify(rutaRepository, times(0)).findByUsuariosParticipante(id);
}

@Test
public void When_CallRutasParticipante_And_RouteExists_ShouldExecute() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        User userMock = new User(name, email, passwordEncoded, null, token);
        int id = 1;
    
        //setUp
        when(userRepository.findById(id)).thenReturn(Optional.of(userMock));

        //Act
        List<RutasUsuarioDTO> rutas = systemUnderTest.rutasParticipante(id);

        //Assert
        assertNotNull(rutas);
        verify(userRepository, times(1)).findById(id);
        verify(rutaRepository, times(1)).findByUsuariosParticipante(id);
}

}