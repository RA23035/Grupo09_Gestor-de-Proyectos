package org.example.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    @Column(name = "fecha_limite", nullable = false)
    private LocalDate fechaLimite;
    @Column(nullable = false, length = 20)
    private String prioridad;
    @Column(nullable = false, length = 30)
    private String estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_asignado_id")
    private Usuario usuarioAsignado;
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Comentario> comentarios = new ArrayList<>();
}
