package com.jadenauta.usuario.business;


import com.jadenauta.usuario.business.converter.UsuarioConverter;
import com.jadenauta.usuario.business.dtos.UsuarioDTO;
import com.jadenauta.usuario.infra.entity.Usuario;
import com.jadenauta.usuario.infra.exceptions.ConflictException;
import com.jadenauta.usuario.infra.exceptions.ResourceNotFoundException;
import com.jadenauta.usuario.infra.repository.UsuarioRepository;
import com.jadenauta.usuario.infra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
    public void emailExiste(String email) {

        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já Cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Esse email já existe", e.getCause());
        }
    }


    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)
        );
    }

    public void deletarEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuari(String token, UsuarioDTO dto) {
        // Aqui busca o email do usuario através do token ( Tirar a obrigatoriedade do email)
        String email = jwtUtil.extraiEmailDoToken(token.substring(7));

        dto.setSenha(dto.getSenha() != null? passwordEncoder.encode(dto.getSenha()) : null);

        // busca os dados do usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não Localizado!!"));


        //mesclou os dados que recebemos na requisição DTO com os dados do banco de dados.
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        // critografica a senha
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        // salva os dados convertidos do usuario.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


}

