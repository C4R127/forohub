package com.aluracursos.forohub.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autor;
    private String curso;

    // Constructor para inicializar la entidad desde el DTO
    public Topico(DatosRegistroTopico datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now(); // Asignamos la fecha exacta de hoy
        this.status = "ACTIVO"; // Estado por defecto
        this.autor = datos.autor();
        this.curso = datos.curso();
    }

    // Método para actualizar los datos desde el DTO
    public void actualizarInformacion(DatosActualizarTopico datosActualizar) {
        if (datosActualizar.titulo() != null) {
            this.titulo = datosActualizar.titulo();
        }
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje();
        }
        if (datosActualizar.autor() != null) {
            this.autor = datosActualizar.autor();
        }
        if (datosActualizar.curso() != null) {
            this.curso = datosActualizar.curso();
        }
    }
}