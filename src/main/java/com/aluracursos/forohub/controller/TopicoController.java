package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico) {

        // Regla de negocio: no permitir duplicados
        if (topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje())) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        }

        // Persistencia en la base de datos
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));

        return ResponseEntity.ok(topico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {
        // Obtenemos todos los tópicos, los paginamos y los convertimos a nuestro DTO
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {
        // Buscamos el tópico por su ID en la base de datos
        var topicoOptional = topicoRepository.findById(id);

        // Regla de negocio: Verificamos si el ID ingresado es correcto/existe
        if (topicoOptional.isPresent()) {
            // Si existe, lo convertimos al DTO y devolvemos un 200 OK
            return ResponseEntity.ok(new DatosListadoTopico(topicoOptional.get()));
        }

        // Si no existe, devolvemos un error 404 Not Found
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizar) {
        // Buscamos el tópico por ID
        var topicoOptional = topicoRepository.findById(id);

        // Verificamos si existe usando isPresent() como piden las instrucciones
        if (topicoOptional.isPresent()) {
            var topico = topicoOptional.get();

            // Actualizamos los datos
            topico.actualizarInformacion(datosActualizar);

            // Devolvemos los datos actualizados usando el DTO de listado
            return ResponseEntity.ok(new DatosListadoTopico(topico));
        }

        // Si no existe el ID, devolvemos 404 Not Found
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        // Buscamos si el tópico existe antes de eliminarlo
        var topicoOptional = topicoRepository.findById(id);

        // Verificamos con isPresent() como indican las reglas
        if (topicoOptional.isPresent()) {
            // Si existe, lo eliminamos usando deleteById
            topicoRepository.deleteById(id);

            // Devolvemos un código 204 (No Content), que es la mejor práctica
            // en APIs REST para indicar que se borró con éxito y ya no hay contenido que mostrar.
            return ResponseEntity.noContent().build();
        }

        // Si no existe, devolvemos un 404 Not Found
        return ResponseEntity.notFound().build();
    }

}