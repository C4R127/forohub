package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.security.DatosJWTToken;
import com.aluracursos.forohub.security.TokenService;
import com.aluracursos.forohub.Usuario.DatosAutenticacionUsuario;
import com.aluracursos.forohub.Usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService; // Inyectamos el nuevo servicio

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacion) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacion.login(), datosAutenticacion.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        // ¡Generamos el token JWT!
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        // Devolvemos el DTO con el token adentro
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}