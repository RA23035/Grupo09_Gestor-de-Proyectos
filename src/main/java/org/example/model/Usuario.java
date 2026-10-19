package org.example.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false, unique = true, length = 150)
    private String correo;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false, length = 50)
    private String rol;
    @OneToMany(mappedBy = "usuarioAsignado")
    @ToString.Exclude
    private List<Tarea> tareasAsignadas = new ArrayList<>();
}
