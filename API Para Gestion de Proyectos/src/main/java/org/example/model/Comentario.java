package org.example.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "comentarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();

    }

}
