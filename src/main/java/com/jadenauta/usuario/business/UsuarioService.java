package com.jadenauta.usuario.business;


import com.jadenauta.usuario.business.converter.UsuarioConverter;
import com.jadenauta.usuario.business.dtos.UsuarioDTO;
import com.jadenauta.usuario.infra.entity.Usuario;
import com.jadenauta.usuario.infra.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;


    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


}
