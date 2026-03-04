package com.aluracursos.forohub.security;

import com.aluracursos.forohub.Usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Spring inyecta aquí el valor que pusimos en application.properties
    @Value("${jwt.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("forohub") // Quien emite el token
                    .withSubject(usuario.getLogin()) // A quien le pertenece (el email)
                    .withClaim("id", usuario.getId()) // Guardamos el ID del usuario
                    .withExpiresAt(generarFechaExpiracion()) // Cuándo caduca
                    .sign(algorithm); // Lo firmamos
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    // Método para que el token expire en 2 horas
    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token no proporcionado");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.require(algorithm)
                    .withIssuer("forohub")
                    .build()
                    .verify(token)
                    .getSubject(); // Extrae el correo del usuario
        } catch (Exception exception) {
            throw new RuntimeException("Token JWT inválido o expirado!");
        }
    }
}