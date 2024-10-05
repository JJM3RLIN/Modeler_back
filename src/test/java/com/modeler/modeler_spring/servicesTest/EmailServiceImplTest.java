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
import com.modeler.modeler_spring.services.Errors;

@SpringBootTest
public class EmailServiceImplTest {
    @Mock
     private JavaMailSender emailSender;

    @InjectMocks
     private EmailServiceImpl systemUnderTest;

     private String messageError = Errors.EMAIL_NOT_SENT;

    @Test
    public void When_CallSendEmailRecovery_And_CouldNotSendEmail_ShouldThrowAnException() throws ModelerException 
    {
            //Arrange
            String emailDestinatario = "emailDestinatario@email.com";
            String ruta = "ruta";
            String nombre = "nombre";
            String id = "12";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(this.emailSender.createMimeMessage()).thenReturn(mimeMessage);
            doThrow(new RuntimeException(messageError)).when(this.emailSender).send(any(MimeMessage.class));

            //Act
            Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.sendEmailRecovery(emailDestinatario, ruta, nombre, id));

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
            String id = "12";
            MimeMessage mimeMessage = mock(MimeMessage.class);
            
            //SetUp
            when(this.emailSender.createMimeMessage()).thenReturn(mimeMessage);
            //Act
            this.systemUnderTest.sendEmailRecovery(emailDestinatario, ruta, nombre, id);

            //Assert
            verify(this.emailSender, times(1)).send(any(MimeMessage.class));
        
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
            when(this.emailSender.createMimeMessage()).thenReturn(mimeMessage);
            doThrow(new RuntimeException(messageError)).when(this.emailSender).send(any(MimeMessage.class));

            //Act
            Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.sendEmailAddProject(emailDestinatario, ruta, nombre));

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
            when(this.emailSender.createMimeMessage()).thenReturn(mimeMessage);
            //Act
            this.systemUnderTest.sendEmailAddProject(emailDestinatario, ruta, nombre);

            //Assert
            verify(this.emailSender, times(1)).send(any(MimeMessage.class));
        
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
            when(this.emailSender.createMimeMessage()).thenReturn(mimeMessage);
            doThrow(new RuntimeException(messageError)).when(emailSender).send(any(MimeMessage.class));

            //Act
            Exception exception = assertThrows(ModelerException.class, ()-> this.systemUnderTest.sendEmailAccount(emailDestinatario, ruta, nombre));

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
            when(this.emailSender.createMimeMessage()).thenReturn(mimeMessage);
            //Act
            this.systemUnderTest.sendEmailAccount(emailDestinatario, ruta, nombre);

            //Assert
            verify(this.emailSender, times(1)).send(any(MimeMessage.class));
        
    }
    
}