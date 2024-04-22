package com.modeler.modeler_spring.security;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeler.modeler_spring.DTO.UserDTO;
import com.modeler.modeler_spring.enums.JWTEnums;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    
    private AuthenticationManager authenticationManager;
    private UserDTO userDTO = null;
    
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        //Obtener el email y password, y lo mapeamos a la clase DTO
               
            try {
                userDTO = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
            } catch (IOException e) {
                System.out.println("Fallo al autenticar...");
                e.printStackTrace();
            }
            //Autenticamos al usuario y generamos su token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        
       
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        // Generamos un JWT para la informacion del usuario autenticado
         Claims claims = Jwts.claims()
         .add("email", user.getUsername())
        .build();
        String token = Jwts.builder()
                        .subject(user.getUsername())
                        .claims(claims)
                        .expiration(new Date(System.currentTimeMillis() + 864000000))
                        .issuedAt(new Date())
                        .signWith(JWTKey.SECRET_KEY)
                        .compact();
        //Agregamos el token al header de la respuesta
        response.addHeader(JWTEnums.CREATE_AUTHORIZATION.getValue(), JWTEnums.PREFIX_TOKEN.getValue() + token);
        //Creamos el body de la respuesta a partir de un Map
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("email", user.getUsername());
        //Accediendo a la sesion para obtener el nombre del usuario mediante el request
        HttpSession session = request.getSession();
        body.put("nombre", (String) session.getAttribute("nombre"));
        body.put("id", String.valueOf(session.getAttribute("id")));
        body.put("verificado", String.valueOf(session.getAttribute("verificado")));
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(JWTEnums.CONTENT_TYPE.getValue());
        response.setStatus(200);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("error", "Error de autenticación");
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");

    }
    
    
}
