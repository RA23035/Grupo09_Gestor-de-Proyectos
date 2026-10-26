package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UsuarioDTO;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertirADTO(usuario);
    }

    public UsuarioDTO crear(UsuarioDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado: " + dto.getCorreo());
        }
        Usuario usuario = convertirAEntidad(dto);
        usuario.setId(null); // Asegura que se genere un nuevo ID
        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());
        usuario.setRol(dto.getRol());

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO convertirADTO(Usuario entidad) {
        if (entidad == null) return null;
        return UsuarioDTO.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .correo(entidad.getCorreo())
                .contrasena(entidad.getContrasena())
                .rol(entidad.getRol())
                .build();
    }

    public Usuario convertirAEntidad(UsuarioDTO dto) {
        if (dto == null) return null;
        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .contrasena(dto.getContrasena())
                .rol(dto.getRol())
                .build();
    }
}