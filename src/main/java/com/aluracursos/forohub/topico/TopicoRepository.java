package com.aluracursos.forohub.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Método derivado que Spring Data JPA construye automáticamente
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}