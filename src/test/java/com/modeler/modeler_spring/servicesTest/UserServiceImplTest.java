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
import com.modeler.modeler_spring.services.Errors;
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
        when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(userMock));
        when(this.userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.create(userDTO));

        //Assert
        assertEquals(Errors.EMAIL_ALREADY_IN_USE, exception.getMessage());
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
        when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(this.userRepository.save(any(User.class))).thenReturn(userMock);
        doThrow(new ModelerException(Errors.EMAIL_NOT_SENT)).when(this.emailService).sendEmailAccount(anyString(), anyString(), anyString());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.create(userDTO));

        //Assert
        assertEquals(Errors.EMAIL_NOT_SENT, exception.getMessage());
    
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
        when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(this.userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        this.systemUnderTest.create(userDTO);

        //Assert
        verify(this.userRepository, times(1)).findByEmail(email);
        verify(this.userRepository, times(1)).save(any(User.class));
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
        when(this.userRepository.findById(userDTO.getId())).thenReturn(Optional.empty());
        when(this.userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.update(userDTO));

        //Assert
        assertEquals(Errors.USER_NOT_FOUND, exception.getMessage());
        verify(this.userRepository, times(0)).save(userMock);
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
        when(this.userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userMock));
        when(this.userRepository.save(any(User.class))).thenReturn(userMock);

        //Act
        this.systemUnderTest.create(userDTO);

        //Assert
        verify(this.userRepository, times(1)).findByEmail(email);
        verify(this.userRepository, times(1)).save(any(User.class));
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
        when(this.userRepository.findById(userDTO.getId())).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.delete(userDTO.getId()));

        //Assert
        assertEquals(Errors.USER_NOT_FOUND, exception.getMessage());
        verify(this.userRepository, times(0)).deleteById(userDTO.getId());
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
        when(this.userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userMock));

        //Act
       this.systemUnderTest.delete(userDTO.getId());

        //Assert
        verify(this.userRepository, times(1)).findById(userDTO.getId());
        verify(this.userRepository, times(1)).deleteById(userDTO.getId());
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
        when(this.userRepository.findByToken(token)).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.verifyToken(token));

        //Assert
        assertEquals(Errors.INVALID_TOKEN, exception.getMessage());
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
        when(this.userRepository.findByToken(token)).thenReturn(Optional.of(userMock));

        //Act
        this.systemUnderTest.verifyToken(token);

        //Assert
        assertNull(userMock.getToken());
        verify(this.userRepository, times(1)).findByToken(token);
        verify(this.userRepository, times(1)).save(userMock);
}

@Test
public void When_CallRutasParticipante_And_UserDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        int id = 1;
    
        //setUp
        when(this.userRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.rutasParticipante(id));

        //Assert
        assertEquals(Errors.USER_NOT_FOUND, exception.getMessage());
        verify(this.rutaRepository, times(0)).findByUsuariosParticipante(id);
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
        when(this.userRepository.findById(id)).thenReturn(Optional.of(userMock));

        //Act
        List<RutasUsuarioDTO> rutas = this.systemUnderTest.rutasParticipante(id);

        //Assert
        assertNotNull(rutas);
        verify(this.userRepository, times(1)).findById(id);
        verify(this.rutaRepository, times(1)).findByUsuariosParticipante(id);
}

@Test
public void When_CallSendEmailRecovery_And_UserDosentExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String email = "correo@test.com";
        //setUp
        when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.sendEmailRecovery(email));

        //Assert
        assertEquals(Errors.EMAIL_NOT_SENT, exception.getMessage());
    
}

@Test
public void When_CallRecoverPassword_And_UserDoesNotExist_ShouldThrowAnException() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        UserDTO userDTO = new UserDTO(name, email, password);
        User userMock = new User(name, email, passwordEncoded, null, token);
        //setUp
        when(this.userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //Act
        Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.recoverPassword(userDTO));

        //Assert
       assertNotNull(exception);
       verify(this.userRepository, times(0)).save(userMock);
}

@Test
public void When_CallRecoverPassword_And_UserDoesExist_ShouldReturnAString() throws ModelerException 
{
        //Arrange
        String name = "Test";
        String email = "correo@test.com";
        String password = "password";
        String token = UUID.randomUUID().toString();
        String passwordEncoded = passwordEncoder.encode(password);
        UserDTO userDTO = new UserDTO(123, name, email, password);
        User userMock = new User(name, email, passwordEncoded, null, token);
        //setUp
        when(this.userRepository.findById(userDTO.getId())).thenReturn(Optional.of(userMock));

        //Act
        String response = this.systemUnderTest.recoverPassword(userDTO);

        //Assert
       assertNotNull(response);
       verify(this.userRepository, times(1)).save(userMock);
}

}