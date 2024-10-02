package com.modeler.modeler_spring.servicesTest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import jakarta.mail.internet.MimeMessage;

import com.modeler.modeler_spring.servicesImpl.EmailServiceImpl;
import com.modeler.modeler_spring.configuration.ModelerException;

@SpringBootTest
public class EmailServiceImplTest {
    @Mock
     private JavaMailSender emailSender;

    @InjectMocks
     private EmailServiceImpl systemUnderTest;

     private String messageError = "Ocurrio un error el enviar el email";

    @Test
    public void When_CallSendEmailRecovery_And_CouldNotSendEmail_ShouldThrowAnException() throws ModelerException 
    {
            //Arrange
            String emailDestinatario = "emailDestinatario@email.com";
            String ruta = "ruta";
            String nombre = "nombre";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
            doThrow(new RuntimeException(messageError)).when(emailSender).send(any(MimeMessage.class));

            //Act
            Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.sendEmailRecovery(emailDestinatario, ruta, nombre));

            //Assert
            assertEquals(messageError, exception.getMessage());
        
    }

    @Test
    public void When_CallSendEmailRecovery_And_CouldSendEmail_ShouldExecute() throws ModelerException 
    {
            //Arrange
            String emailDestinatario = "emailDestinatario@email.com";
            String ruta = "ruta";
            String nombre = "nombre";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
            //Act
            systemUnderTest.sendEmailRecovery(emailDestinatario, ruta, nombre);

            //Assert
            verify(emailSender, times(1)).send(any(MimeMessage.class));
        
    }

    @Test
    public void When_CallSendEmailAddProject_And_CouldNotSendEmail_ShouldThrowAnException() throws ModelerException 
    {
            //Arrange
            String emailDestinatario = "emailDestinatario@email.com";
            String ruta = "ruta";
            String nombre = "nombre";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
            doThrow(new RuntimeException(messageError)).when(emailSender).send(any(MimeMessage.class));

            //Act
            Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.sendEmailAddProject(emailDestinatario, ruta, nombre));

            //Assert
            assertEquals(messageError, exception.getMessage());
        
    }

    @Test
    public void When_CallSendEmailAddProjectAnd_CouldSendEmail_ShouldExecute() throws ModelerException 
    {
            //Arrange
            String emailDestinatario = "emailDestinatario@email.com";
            String ruta = "ruta";
            String nombre = "nombre";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
            //Act
            systemUnderTest.sendEmailAddProject(emailDestinatario, ruta, nombre);

            //Assert
            verify(emailSender, times(1)).send(any(MimeMessage.class));
        
    }

    @Test
    public void When_CallSendEmailAccount_And_CouldNotSendEmail_ShouldThrowAnException() throws ModelerException 
    {
            //Arrange
            String emailDestinatario = "emailDestinatario@email.com";
            String ruta = "ruta";
            String nombre = "nombre";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
            doThrow(new RuntimeException(messageError)).when(emailSender).send(any(MimeMessage.class));

            //Act
            Exception exception = assertThrows(ModelerException.class, ()-> systemUnderTest.sendEmailAccount(emailDestinatario, ruta, nombre));

            //Assert
            assertEquals(messageError, exception.getMessage());
        
    }

    @Test
    public void When_CallSendEmailAccountAnd_CouldSendEmail_ShouldExecute() throws ModelerException 
    {
            //Arrange
            String emailDestinatario = "emailDestinatario@email.com";
            String ruta = "ruta";
            String nombre = "nombre";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
            //Act
            systemUnderTest.sendEmailAccount(emailDestinatario, ruta, nombre);

            //Assert
            verify(emailSender, times(1)).send(any(MimeMessage.class));
        
    }
    
}